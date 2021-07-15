package com.luiscampos.model

final case class Representative(
  id: RepresentativeId,
  professionCategory: Option[String]
)