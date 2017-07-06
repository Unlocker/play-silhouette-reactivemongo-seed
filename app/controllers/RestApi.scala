package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.Silhouette
import com.mohiva.play.silhouette.api.actions.SecuredErrorHandler
import play.api.i18n.{ Messages, MessagesApi }
import play.api.libs.json._
import play.api.mvc._
import utils.auth.DefaultEnv

import scala.concurrent.Future

/**
 * Sample REST controller.
 */
class RestApi @Inject() (val messagesApi: MessagesApi, val env: DefaultEnv, val silhouette: Silhouette[DefaultEnv])
  extends Controller with SecuredErrorHandler {

  /**
   *
   * @return
   */
  def profile: Action[AnyContent] = silhouette.SecuredAction.async { implicit request =>
    val json: JsValue = Json.toJson(request.identity)
    val transformer: Reads[JsObject] = (__ \ 'loginInfo).json.prune
    json.transform(transformer).fold(
      _ => Future.successful(InternalServerError(Json.obj("error" -> Messages("error.profileError")))),
      js => Future.successful(Ok(js))
    )
  }

  override def onNotAuthenticated(request: RequestHeader): Some[Future[Result]] = {
    Some(Future.successful(Unauthorized(Json.obj("error" -> Messages("error.profileUnauth")))))
  }

  override def onNotAuthorized(implicit request: RequestHeader): Future[Result] = ???
}
