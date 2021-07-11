package com.luiscampos

import com.luiscampos.model.parser.RawDeputadoParser
import com.luiscampos.model.RawDeputado

object GenerateProcessedFiles extends App {

  val filePath = "../raw-data/RegistoBiograficoXIV.json"

  def groupProfessionsByCategory(professions: Seq[String]): Map[String, Int] = {
    val (main, others) = professions
      .map(ProfessionNormalizer.normalize)
      .groupBy(identity)
      .view
      .mapValues(_.size)
      .partition(_._2 > 1)
    main.toMap + ("Others" -> others.values.sum)
  }

  def getProfessions(deputados: Seq[RawDeputado]): Seq[String] =
    deputados
      .map(_.cadProfissao)
      .map(_.getOrElse("Unknown"))

  RawDeputadoParser.getRawDeputados(filePath) match {
    case Right(deputados) =>
      groupProfessionsByCategory(getProfessions(deputados)).toSeq
        .sortBy(_._2)
        .reverse
        .foreach(d => println(s"${d}"))
    case Left(err) => println(err)
  }
}
