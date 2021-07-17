package com.luiscampos.model

import com.luiscampos.util.FileUtil
import io.circe
import io.circe.Decoder
import io.circe.DecodingFailure
import io.circe.Json
import io.circe.generic.semiauto._

final case class RawRepresentativeFromInformacaoBase(
  depCadId: RepresentativeId,
  depSituacao: RawDepSituacaoFromInformacaoBase
)

case class RawDepSituacaoFromInformacaoBase(
  pt_ar_wsgode_objectos_DadosSituacaoDeputado: Seq[DadoSituacaoDeputadoFromInformacaoBase]
)

object RawDepSituacaoFromInformacaoBase {
  implicit val sitDecoder: Decoder[RawDepSituacaoFromInformacaoBase] = new Decoder[RawDepSituacaoFromInformacaoBase] {
    def apply(c: circe.HCursor): Decoder.Result[RawDepSituacaoFromInformacaoBase] = {
      val situacaoFieldCursor = c.downField("pt_ar_wsgode_objectos_DadosSituacaoDeputado")
      situacaoFieldCursor.as[Seq[DadoSituacaoDeputadoFromInformacaoBase]] match {
        case Right(v) => Right(RawDepSituacaoFromInformacaoBase(v))
        case Left(_) =>
          situacaoFieldCursor.as[DadoSituacaoDeputadoFromInformacaoBase] match {
            case Right(v)  => Right(RawDepSituacaoFromInformacaoBase(Seq(v)))
            case Left(err) => Left(err)
          }
      }
    }
  }
}

case class DadoSituacaoDeputadoFromInformacaoBase(
  sioDes: String,
  sioDtFim: String,
  sioDtInicio: String
)

object DadoSituacaoDeputadoFromInformacaoBase {
  implicit val sitDecoder: Decoder[DadoSituacaoDeputadoFromInformacaoBase] =
    deriveDecoder[DadoSituacaoDeputadoFromInformacaoBase]
}

object RawRepresentativeFromInformacaoBase {

  implicit val repDecoder: Decoder[RawRepresentativeFromInformacaoBase] =
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
