package controllers

import scalaoauth2.provider._
import play.api.mvc.{Controller, Action}
import oath2.Oath2Handler
import scala.concurrent.ExecutionContext.Implicits.global

object OAuth2Controller extends Controller with OAuth2Provider {
  def accessToken = Action.async { implicit request =>
    issueAccessToken(new Oath2Handler())
  }
}