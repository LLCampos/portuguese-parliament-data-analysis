package com.luiscampos.model.parser

import com.luiscampos.model.RawDeputado
import com.luiscampos.util.FileUtil
import io.circe._

object RawDeputadoParser {
  def fromRawJson(json: Json): Either[DecodingFailure, Seq[RawDeputado]] =
    json.hcursor
      .downField("RegistoBiografico")
      .downField("RegistoBiograficoList")
      .downField("pt_ar_wsgode_objectos_DadosRegistoBiograficoWeb")
      .focus
      .get
      .as[Seq[RawDeputado]]

  def getRawDeputados(filePath: String): Either[Error, Seq[RawDeputado]] =
    FileUtil
      .fileToJson(filePath)
      .flatMap(json => {
        RawDeputadoParser.fromRawJson(json)
      })

}
