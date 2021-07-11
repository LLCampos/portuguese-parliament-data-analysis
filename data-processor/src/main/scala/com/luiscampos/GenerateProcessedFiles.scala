package com.luiscampos

import com.luiscampos.model.parser.RawDeputadoParser
import com.luiscampos.model.RawDeputado
import com.luiscampos.model.Legislature

object GenerateProcessedFiles extends App {

  def filePath(legislature: String) =
    s"../raw-data/RegistoBiografico$legislature.json"

  val legislatureNumbers = Seq("II", "III", "IV", "V", "VI", "VII", "VIII", "X", "XI", "XII", "XIII")

  val legislatures = legislatureNumbers.map { legislature =>
    RawDeputadoParser.getRawDeputados(filePath(legislature)) match {
      case Right(deputados) =>
        Some(
          Legislature(
            legislature,
            groupProfessionsByCategory(getProfessions(deputados))
          )
        )
      case Left(err) =>
        println(err)
        None
    }
  }.flatten

  legislatures.foreach { l =>
    println(l.legislatureNumber)
    println()
    l.professions.toSeq.sortBy(_._2).reverse.foreach(println)
    println("--------------------")
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
