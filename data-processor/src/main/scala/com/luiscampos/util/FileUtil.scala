package com.luiscampos.util

import io.circe._
import io.circe.parser._

import scala.io.Source

object FileUtil {
  def fileToString(filePath: String): String =
    Source.fromFile(filePath).mkString

  def fileToJson(filePath: String): Either[ParsingFailure, Json] =
    parse(fileToString(filePath))
}
