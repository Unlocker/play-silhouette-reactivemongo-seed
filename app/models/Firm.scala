package models

import java.util.UUID

import play.api.libs.json.{ Json, OFormat }

/**
 * Describes a customer's firm.
 *
 * @param firmID firm ID
 * @param name   human readable name
 */
case class Firm(firmID: UUID, name: Option[String])

object Firm {
  implicit val jsonFormat: OFormat[Firm] = Json.format[Firm]
}
