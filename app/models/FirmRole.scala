package models

import java.util.UUID

import play.api.libs.json.{ Json, OFormat }

/**
 * Describes an association between firm and user.
 *
 * @param firmID   firm ID
 * @param position user position
 * @param roles    roles
 */
case class FirmRole(firmID: UUID, position: Option[String], roles: Set[String]) {
  override def hashCode(): Int = firmID.hashCode()

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case fr: FirmRole => firmID.equals(fr.firmID)
      case _ => false
    }
  }
}

object FirmRole {
  implicit val jsonFormat: OFormat[FirmRole] = Json.format[FirmRole]
}
