package controllers

import scalaoauth2.provider._
import play.api.mvc.{Controller, Action}
import oath2.{Oath2Handler, CustomTokenEndpoint}
import scala.concurrent.ExecutionContext.Implicits.global

trait CustomOAuth extends OAuth2Provider {
  override val tokenEndpoint: TokenEndpoint = CustomTokenEndpoint
}

object OAuth2Controller extends Controller with OAuth2Provider {
  def accessToken = Action.async { implicit request =>
    issueAccessToken(new Oath2Handler())
  }
}