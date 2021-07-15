package com.luiscampos.model

import com.luiscampos.ProfessionNormalizer
import io.circe.Decoder
import io.circe.generic.semiauto._
import com.luiscampos.util.FileUtil
import io.circe.DecodingFailure
import io.circe.Json
import io.circe

final case class RawRepresentative(
  cadProfissao: Option[String],
  cadId: RepresentativeId
) {

  def toRepresentative: Representative =
    Representative(
      cadId,
      cadProfissao.map(ProfessionNormalizer.normalize)
    )

}

object RawRepresentative {
  implicit val fooDecoder: Decoder[RawRepresentative] =
    deriveDecoder[RawRepresentative]

  def fromRegistoBiograficoJson(json: Json): Either[DecodingFailure, Seq[RawRepresentative]] =
    json.hcursor
      .downField("RegistoBiografico")
      .downField("RegistoBiograficoList")
      .downField("pt_ar_wsgode_objectos_DadosRegistoBiograficoWeb")
      .focus
      .get
      .as[Seq[RawRepresentative]]

  def fromRegistoBiograficoFile(filePath: String): Either[circe.Error, Seq[RawRepresentative]] =
    FileUtil
      .fileToJson(filePath)
      .flatMap(json => {
        RawRepresentative.fromRegistoBiograficoJson(json)
      })
}
