package com.luiscampos

import com.luiscampos.model.parser.RawDeputadoParser

object GenerateProcessedFiles extends App {

  val filePath = "../raw-data/RegistoBiograficoXIV.json"

  RawDeputadoParser.getRawDeputados(filePath) match {
    case Right(deputados) => deputados.map(_.cadProfissao).foreach(d => println(s"${d.getOrElse("NULL")}"))
    case Left(err) => println(err)
  }
}
