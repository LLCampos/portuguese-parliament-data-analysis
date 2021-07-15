package com.luiscampos.model

import com.luiscampos.ProfessionNormalizer
import io.circe.Decoder
import io.circe.generic.semiauto._

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
  implicit val fooDecoder: Decoder[RawRepresentative] = deriveDecoder[RawRepresentative]
}
