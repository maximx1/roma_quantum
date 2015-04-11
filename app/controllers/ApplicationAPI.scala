package controllers

import org.json4s._
import org.json4s.jackson.JsonMethods._
import com.github.tototoshi.play2.json4s.jackson._
import com.github.tototoshi.play2.json4s.jackson.Json4s
import play.api.mvc._
import models.responses._
import controllers.helpers._
import org.joda.time.DateTime

case class NewUser(firstName: String, lastName: String, email: String, password: String)

object ApplicationAPI extends Controller with Json4s {
  private implicit val format = DefaultFormats
  
  def createUser = Action(json) { implicit request =>
    val newUser = request.body.extract[NewUser]
    val uuid = java.util.UUID.randomUUID()
    models.Users.+=(models.User(uuid, newUser.firstName, newUser.lastName, newUser.email, newUser.password, DateTime.now(), uuid, None, None, None, None))
    
    Ok(Json(BaseResponse("ok", None)))
  }
}