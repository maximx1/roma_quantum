package controllers

import org.json4s._
import org.json4s.jackson.JsonMethods._
import com.github.tototoshi.play2.json4s.jackson._
import com.github.tototoshi.play2.json4s.jackson.Json4s
import play.api.mvc._
import models.responses._
import controllers.helpers._

object ApplicationAPI extends Controller with Json4s {
  def createUser = Action(json) { implicit request =>
    Ok(Json(BaseResponse("ok", None)))
  }
}