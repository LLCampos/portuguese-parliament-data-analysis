package com.luiscampos.model

import Legislature._

final case class Legislature(
  number: Int,
  professions: Professions
)

object Legislature {
  type Professions = Map[String, Int]
}