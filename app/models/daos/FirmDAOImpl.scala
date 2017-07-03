package models.daos

import java.util.UUID
import javax.inject.Inject

import models.Firm
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.Future

/**
 * Gives access to the firm object.
 *
 * @param reactiveMongoApi MongoDB API
 */
class FirmDAOImpl @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends FirmDAO {

  /**
   * Retrieves a firm with a given identifier
   *
   * @param firmID ID
   * @return firm
   */
  override def find(firmID: UUID): Future[Option[Firm]] = {
    val query = Json.obj("firmID" -> firmID)
    collection.flatMap(_.find(query).one[Firm])
  }

  /**
   * @return collection to deal with
   */
  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("firms"))

  /**
   * Saves a given firm
   *
   * @param firm firm
   * @return saved firm
   */
  override def save(firm: Firm): Future[Firm] = {

  }
}
