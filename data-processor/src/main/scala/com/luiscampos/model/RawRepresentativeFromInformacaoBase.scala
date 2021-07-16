package com.luiscampos.model

import io.circe


final case class RawRepresentativeFromInformacaoBase(
  depCadId: RepresentativeId
)

object RawRepresentativeFromInformacaoBase {
    def fromInformacaoBaseFile(filePath: String): Either[circe.Error, Seq[RawRepresentativeFromInformacaoBase]] = ???
}