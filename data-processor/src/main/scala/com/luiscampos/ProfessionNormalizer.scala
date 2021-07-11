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
    "engº" -> "Engineering",
    "eng." -> "Engineering",
    "eng " -> "Engineering",
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
    "funcionaria publica" -> "Public Servant",
    "aposentad" -> "Retired",
    "reformad" -> "Retired",
    "empresari" -> "Business",
    "matematico" -> "Maths",
    "actriz" -> "Acting",
    "atriz" -> "Acting",
    "jornalis" -> "Journalism",
    "universitari" -> "Academy",
    "investigador" -> "Academy",
    "ensino superior" -> "Academy",
    "arqueolog" -> "Archeology",
    "electricista" -> "Blue Collar",
    "operari" -> "Blue Collar",
    "afinador" -> "Blue Collar",
    "trabalhador agricola" -> "Blue Collar",
    "tributario" -> "Accounting",
    "contabilista" -> "Accounting",
    "tecnico oficial de contas" -> "Accounting",
    "antropol" -> "Anthropology",
    "historiad" -> "History",
    "museologo" -> "Museology",
    "quimica" -> "Chemistry",
    "seguros" -> "Insurance",
    "call center" -> "Call Center",
    "arquitect" -> "Architecture",
    "design" -> "Design",
    "assistente social" -> "Social Worker",
    "turismo" -> "Tourism",
    "estudante" -> "Student",
    "enfermeir" -> "Nursing",
    "secretari" -> "Secretary",
    "funcionario do pcp" -> "Party Worker",
    "unknown" -> "Unknown",
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
