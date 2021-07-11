package com.luiscampos

import com.luiscampos.model.parser.RawDeputadoParser

object GenerateProcessedFiles extends App {

  val filePath = "../raw-data/RegistoBiograficoXIV.json"

  RawDeputadoParser.getRawDeputados(filePath) match {
    case Right(deputados) =>
      deputados
        .map(_.cadProfissao)
        .map(_.getOrElse("NULL"))
        .map(ProfessionNormalizer.normalize)
        .groupBy(identity).view.mapValues(_.size)
        .foreach(d => println(s"${d}"))
    case Left(err) => println(err)
  }
}
