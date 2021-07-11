package com.luiscampos

import org.apache.commons.lang3.StringUtils

object ProfessionNormalizer {

  private val professionCategories = Map(
    "professor" -> "Teaching",
    "advogad" -> "Law",
    "advocacia" -> "Law",
    "jurista" -> "Law",
    "juiz" -> "Law",
    "solicitador" -> "Law",
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
    "gerente" -> "Management",
    "director" -> "Management",
    "consultor" -> "Consulting",
    "psico" -> "Psychology",
    "tecnico superior" -> "Public Servant",
    "tecnica superior" -> "Public Servant",
    "tec. superior" -> "Public Servant",
    "funcionario publico" -> "Public Servant",
    "funcionaria publica" -> "Public Servant",
    "funcionaria do estado" -> "Public Servant",
    "presidente de camara" -> "Public Servant",
    "presidente da camara" -> "Public Servant",
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
    "serralheiro" -> "Blue Collar",
    "metalurgico" -> "Blue Collar",
    "tecnico de maquinas" -> "Blue Collar",
    "mecanico naval" -> "Blue Collar",
    "tecnico de manutencao mecanica" -> "Blue Collar",
    "agricultor" -> "Blue Collar",
    "vitivinicultor" -> "Blue Collar",
    "tributario" -> "Accounting",
    "contabilista" -> "Accounting",
    "tecnico oficial de contas" -> "Accounting",
    "tecnico de contas" -> "Accounting",
    "antropol" -> "Anthropology",
    "historiad" -> "History",
    "museologo" -> "Museology",
    "quimica" -> "Chemistry",
    "seguros" -> "Insurance",
    "call center" -> "Call Center",
    "arquitect" -> "Architecture",
    "design" -> "Design",
    "assistente social" -> "Social Worker",
    "servico social" -> "Social Worker",
    "turismo" -> "Tourism",
    "estudante" -> "Student",
    "enfermeir" -> "Nursing",
    "secretari" -> "Office Work",
    "empregado de escritorio" -> "Office Work",
    "funcionario do pcp" -> "Party Worker",
    "funcionario do partido politico" -> "Party Worker",
    "farmaceutic" -> "Pharmacy",
    "escrit" -> "Writing",
    "militar" -> "Military",
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
