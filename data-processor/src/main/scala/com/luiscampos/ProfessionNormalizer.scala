package com.luiscampos

import org.apache.commons.lang3.StringUtils

object ProfessionNormalizer {

  private val professionCategories = Map(
    "professor" -> "Teaching",
    "advogad" -> "Law",
    "advocacia" -> "Law",
    "jurista" -> "Law",
    "juiz" -> "Law",
    "medic" -> "Medicine",
    "bancari" -> "Banking",
    "economista" -> "Economy",
    "programador" -> "IT",
    "computador" -> "IT",
    "engenheir" -> "Engineering",
    "eng." -> "Engineering",
    "biolog" -> "Biology",
    "sociolog" -> "Sociology",
    "gestor" -> "Management",
    "administrador" -> "Management",
    "consultor" -> "Consulting",
    "psico" -> "Psychology",
    "tecnico superior" -> "Public Servant",
    "tecnica superior" -> "Public Servant",
    "tec. superior" -> "Public Servant",
    "funcionario publico" -> "Public Servant",
    "aposentad" -> "Retired",
    "empresari" -> "Business",
    "matematico" -> "Maths",
    "actriz" -> "Acting",
    "atriz" -> "Acting",
    "jornalis" -> "Journalism",
    "universitari" -> "Academy",
    "investigador" -> "Academy",
    "arqueolog" -> "Archeology",
    "electricista" -> "Blue Collar",
    "operari" -> "Blue Collar",
    "afinador" -> "Blue Collar",
    "tributario" -> "Accounting",
    "contabilista" -> "Accounting",
    "antropol" -> "Anthropology",
    "historiad" -> "History",
    "museologo" -> "Museology",
    "quimica" -> "Chemistry",
    "seguros" -> "Insurance",
    "call center" -> "Call Center",
    "arquitect" -> "Architecture",
    "design" -> "Design",
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
