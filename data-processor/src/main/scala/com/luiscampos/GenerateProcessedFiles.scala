package com.luiscampos

import com.luiscampos.model.parser.RawDeputadoParser
import com.luiscampos.model.RawDeputado
import com.luiscampos.model.Legislature

import java.io._

object GenerateProcessedFiles extends App {

  def filePath(legislature: String) =
    s"../raw-data/RegistoBiografico$legislature.json"

  val legislatureNumbersRoman =
    Seq("II", "III", "IV", "V", "VI", "VII", "VIII", "X", "XI", "XII", "XIII")

  val legislatureNumbersTrans = Map(
    "II" -> 2,
    "III" -> 3,
    "IV" -> 4,
    "V" -> 5,
    "VI" -> 6,
    "VII" -> 7,
    "VIII" -> 8,
    "X" -> 9,
    "XI" -> 10,
    "XII" -> 11,
    "XIII" -> 12
  )

  val legislatures = legislatureNumbersRoman.map { legislature =>
    RawDeputadoParser.getRawDeputados(filePath(legislature)) match {
      case Right(deputados) =>
        Some(
          Legislature(
            legislatureNumbersTrans.get(legislature).get,
            groupProfessionsByCategory(getProfessions(deputados))
          )
        )
      case Left(err) =>
        println(err)
        None
    }
  }.flatten

  // legislatures.foreach { l =>
  //   println(l.legislatureNumber)
  //   println()
  //   l.professions.toSeq.sortBy(_._2).reverse.foreach(println)
  //   println("--------------------")
  // }

  writeToFile("../processed-data/professions.csv", toCsv(legislatures))

  private def writeToFile(filePath: String, content: String): Unit = {
    val pw = new PrintWriter(new File(filePath))
    pw.write(content)
    pw.close
  }

  private def toCsv(legislatures: Seq[Legislature]): String = {
    val columnNames = "legislatureNumber,profession_cat,number_of_deputados"
    val rows = legislatures
      .flatMap { legislature =>
        legislature.professions.map { case (profession_cat, n) =>
          s"${legislature.number},$profession_cat,$n"
        }
      }
      .mkString("\n")
    columnNames + "\n" + rows
  }

  private def groupProfessionsByCategory(
      professions: Seq[String]
  ): Map[String, Int] = {
    val (main, others) = professions
      .map(ProfessionNormalizer.normalize)
      .groupBy(identity)
      .view
      .mapValues(_.size)
      .partition(_._2 > 1)
    // others.toMap.keys.foreach(println)
    main.toMap + ("Others" -> others.values.sum)
  }

  private def getProfessions(deputados: Seq[RawDeputado]): Seq[String] =
    deputados
      .map(_.cadProfissao)
      .map(_.getOrElse("Unknown"))
}
