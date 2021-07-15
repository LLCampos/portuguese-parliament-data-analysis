package com.luiscampos.model.parser

import com.luiscampos.model.RawRepresentative
import com.luiscampos.util.FileUtil
import io.circe._

object RawRepresentativeParser {
  def fromRegistoBiograficoJson(json: Json): Either[DecodingFailure, Seq[RawRepresentative]] =
    json.hcursor
      .downField("RegistoBiografico")
      .downField("RegistoBiograficoList")
      .downField("pt_ar_wsgode_objectos_DadosRegistoBiograficoWeb")
      .focus
      .get
      .as[Seq[RawRepresentative]]

  def fromRegistoBiograficoFile(filePath: String): Either[Error, Seq[RawRepresentative]] =
    FileUtil
      .fileToJson(filePath)
      .flatMap(json => {
        RawRepresentativeParser.fromRegistoBiograficoJson(json)
      })

}
