package models.daos

import java.util.UUID
import javax.inject.Inject

import models.Firm
import play.api.libs.json._
import play.modules.reactivemongo._
import play.modules.reactivemongo.json._
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
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
   * Saves a given firm
   *
   * @param firm firm
   * @return saved firm
   */
  override def save(firm: Firm): Future[Firm] = {
    collection.flatMap(_.update(Json.obj("firmID" -> firm.firmID), firm, upsert = true))
    Future.successful(firm)
  }

  /**
   * @return collection to deal with
   */
  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("firms"))
}
