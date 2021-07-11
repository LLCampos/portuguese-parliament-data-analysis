package com.luiscampos

import org.apache.commons.lang3.StringUtils

object ProfessionNormalizer {

  private val professionCategories = Map(
    "professor" -> "teaching"
  )

  def normalize(profession: String): String = {
    normalizeToCategory(StringUtils.stripAccents(profession.toLowerCase().trim()))
  }

  private def normalizeToCategory(profession: String): String =
    professionCategories
      .find {
        case (keyWord, _) => {
          profession.contains(keyWord)
        }
      }
      .map(_._2)
      .getOrElse(profession)

}
