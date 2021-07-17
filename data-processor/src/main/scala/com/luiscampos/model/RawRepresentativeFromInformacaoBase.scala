package com.luiscampos.model

import com.luiscampos.util.FileUtil
import io.circe
import io.circe.Decoder
import io.circe.DecodingFailure
import io.circe.Json
import io.circe.generic.semiauto._

final case class RawRepresentativeFromInformacaoBase(
  depCadId: RepresentativeId
)

object RawRepresentativeFromInformacaoBase {

  implicit val fooDecoder: Decoder[RawRepresentativeFromInformacaoBase] =
    deriveDecoder[RawRepresentativeFromInformacaoBase]

  def fromInformacaoBaseJson(json: Json): Either[DecodingFailure, Seq[RawRepresentativeFromInformacaoBase]] =
    json.hcursor
      .downField("Legislatura")
      .downField("Deputados")
      .downField("pt_ar_wsgode_objectos_DadosDeputadoSearch")
      .focus
      .get
      .as[Seq[RawRepresentativeFromInformacaoBase]]

  def fromInformacaoBaseFile(filePath: String): Either[circe.Error, Seq[RawRepresentativeFromInformacaoBase]] =
    FileUtil
      .fileToJson(filePath)
      .flatMap(json => {
        RawRepresentativeFromInformacaoBase.fromInformacaoBaseJson(json)
      })
}
