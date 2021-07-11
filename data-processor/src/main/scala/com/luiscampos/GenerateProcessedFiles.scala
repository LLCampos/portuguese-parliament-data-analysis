package com.luiscampos

import com.luiscampos.model.parser.RawDeputadoParser

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

  RawDeputadoParser.getRawDeputados(filePath) match {
    case Right(deputados) =>
      val professions = deputados
        .map(_.cadProfissao)
        .map(_.getOrElse("NULL"))
      groupProfessionsByCategory(professions).toSeq
        .sortBy(_._2)
        .reverse
        .foreach(d => println(s"${d}"))
    case Left(err) => println(err)
  }
}
