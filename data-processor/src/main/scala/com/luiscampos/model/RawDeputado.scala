package com.luiscampos.model

import io.circe.Decoder
import io.circe.generic.semiauto._

final case class RawDeputado(
    cadProfissao: Option[String]
)

object RawDeputado {
  implicit val fooDecoder: Decoder[RawDeputado] = deriveDecoder[RawDeputado]
}
