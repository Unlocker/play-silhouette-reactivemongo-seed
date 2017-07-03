package models.daos

import java.util.UUID

import models.Firm

import scala.concurrent.Future

/**
 * Gives access to the firm object.
 */
trait FirmDAO {

  /**
   * Retrieves a firm with a given identifier
   *
   * @param firmID ID
   * @return firm
   */
  def find(firmID: UUID): Future[Option[Firm]]

  /**
   * Saves a given firm
   *
   * @param firm firm
   * @return saved firm
   */
  def save(firm: Firm): Future[Firm]

}
