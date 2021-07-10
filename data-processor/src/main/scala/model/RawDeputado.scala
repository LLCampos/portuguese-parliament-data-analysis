import io.circe.Json

import io.circe.generic.semiauto._
import io.circe.Decoder
import io.circe._

final case class RawDeputado(
    cadProfissao: Option[String]
)

object RawDeputado {
  implicit val fooDecoder: Decoder[RawDeputado] = deriveDecoder[RawDeputado]
}
