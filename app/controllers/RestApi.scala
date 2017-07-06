package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.Silhouette
import play.api.i18n.{ I18nSupport, Messages, MessagesApi }
import play.api.libs.json._
import play.api.mvc._
import utils.auth.DefaultEnv

import scala.concurrent.Future

/**
 * Sample REST controller.
 */
class RestApi @Inject() (val messagesApi: MessagesApi, val env: DefaultEnv, val silhouette: Silhouette[DefaultEnv])
  extends Controller with I18nSupport {

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
}
