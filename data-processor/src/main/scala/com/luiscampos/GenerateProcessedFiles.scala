package com.luiscampos

import com.luiscampos.model._

import java.io.File
import java.io.PrintWriter

object GenerateProcessedFiles extends App {

  def registoBiograficoFilePath(legislature: String) =
    s"../raw-data/RegistoBiografico$legislature.json"

  def informacaoBaseFilePath(legislature: String) =
    s"../raw-data/InformacaoBase$legislature.json"

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

  // Doesn't handle representatives changing professions but that's probably ok?
  val representatives = legislatureNumbersRoman.map { legislatureNumber =>
    RawRepresentativeFromRegistoBiografico.fromRegistoBiograficoFile(registoBiograficoFilePath(legislatureNumber)) match {
      case Right(rawRep) =>
        rawRep.map(_.toRepresentative).map(r => r.id -> r).toMap
      case Left(err) =>
        println(err)
        Map.empty
    }
  }.flatten.toMap

  val legislatures = legislatureNumbersRoman.map { legislature =>
    RawRepresentativeFromInformacaoBase.fromInformacaoBaseFile(informacaoBaseFilePath(legislature)) match {
      case Right(rawReps: Seq[RawRepresentativeFromInformacaoBase]) =>
        val reps = rawReps.map(r => representatives.get(r.depCadId)).flatten
        Some(
          Legislature(
            legislatureNumbersTrans.get(legislature).get,
            groupProfessionsByCategory(getProfessions(reps)),
            reps
          )
        )
      case Left(err) =>
        println(err)
        None
    }
  }.flatten

  // legislatures.foreach { l =>
  //   println(l.number)
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
    val (main, others) =
      professions.groupBy(identity).view.mapValues(_.size).partition(_._2 > 1)
    // others.toMap.keys.foreach(println)
    main.toMap + ("Others" -> others.values.sum)
  }

  private def getProfessions(rep: Seq[Representative]): Seq[String] =
    rep.map(_.professionCategory.getOrElse("Unknown"))
}
