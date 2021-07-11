package com.luiscampos

import io.circe._
import io.circe.parser._

import scala.io.Source

import model.RawDeputado

object FileUtil {
  def fileToString(filePath: String): String =
    Source.fromFile(filePath).mkString

  def fileToJson(filePath: String): Either[ParsingFailure, Json] =
    parse(fileToString(filePath))
}

object RawDeputadoParser {
  def fromRawJson(json: Json): Either[DecodingFailure, Seq[RawDeputado]] =
    json.hcursor
      .downField("RegistoBiografico")
      .downField("RegistoBiograficoList")
      .downField("pt_ar_wsgode_objectos_DadosRegistoBiograficoWeb")
      .focus.get
      .as[Seq[RawDeputado]]

  def getRawDeputados(filePath: String): Either[Error, Seq[RawDeputado]] = 
    FileUtil.fileToJson("../raw-data/RegistoBiograficoXIV.json").flatMap(json => {
      RawDeputadoParser.fromRawJson(json)
    })

}

object GenerateProcessedFiles extends App {

  val filePath = "../raw-data/RegistoBiograficoXIV.json"

  RawDeputadoParser.getRawDeputados(filePath) match {
    case Right(deputados) => deputados.map(_.cadProfissao).foreach(d => println(s"${d.getOrElse("NULL")}"))
    case Left(err) => println(err)
  }
}
