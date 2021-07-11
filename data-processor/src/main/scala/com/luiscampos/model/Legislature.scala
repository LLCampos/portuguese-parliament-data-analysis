package com.luiscampos.model

import Legislature._

final case class Legislature(
  legislatureNumber: String, // Roman numeral
  professions: Professions
)

object Legislature {
  type Professions = Map[String, Int]
}