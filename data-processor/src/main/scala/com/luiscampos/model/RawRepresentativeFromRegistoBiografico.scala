package com.luiscampos.model

import com.luiscampos.ProfessionNormalizer
import io.circe.Decoder
import io.circe.generic.semiauto._
import com.luiscampos.util.FileUtil
import io.circe.DecodingFailure
import io.circe.Json
import io.circe

final case class RawRepresentativeFromRegistoBiografico(
  cadProfissao: Option[String],
  cadId: RepresentativeId
) {

  def toRepresentative: Representative =
    Representative(
      cadId,
      cadProfissao.map(ProfessionNormalizer.normalize)
    )

}

object RawRepresentativeFromRegistoBiografico {
  implicit val fooDecoder: Decoder[RawRepresentativeFromRegistoBiografico] =
    deriveDecoder[RawRepresentativeFromRegistoBiografico]

  def fromRegistoBiograficoJson(json: Json): Either[DecodingFailure, Seq[RawRepresentativeFromRegistoBiografico]] =
    json.hcursor
      .downField("RegistoBiografico")
      .downField("RegistoBiograficoList")
      .downField("pt_ar_wsgode_objectos_DadosRegistoBiograficoWeb")
      .focus
      .get
      .as[Seq[RawRepresentativeFromRegistoBiografico]]

  def fromRegistoBiograficoFile(filePath: String): Either[circe.Error, Seq[RawRepresentativeFromRegistoBiografico]] =
    FileUtil
      .fileToJson(filePath)
      .flatMap(json => {
        RawRepresentativeFromRegistoBiografico.fromRegistoBiograficoJson(json)
      })
}
