package models

import play.api.db.slick.Config.driver.simple._
import org.joda.time.DateTime
import com.github.tototoshi.slick.HsqldbJodaSupport._

case class AuthCode(
    authorizationCode: String,
    userId: Int,
    redirectUri: Option[String],
    createdAt: DateTime,
    scope: Option[String],
    clientId: Option[String],
    expiresIn: Int
)

class AuthCodes(tag: Tag) extends Table[AuthCode](tag, "AUTH_CODES") {
  def authorizationCode = column[String]("AUTHORIZATION_CODE", O.PrimaryKey)
  def userId = column[Int]("USER_ID")
  def redirectUri = column[Option[String]]("REDIRECT_URI")
  def createdAt = column[DateTime]("CREATED_AT")
  def scope = column[Option[String]]("SCOPE")
  def clientId = column[Option[String]]("CLIENT_ID")
  def expiresIn = column[Int]("EXPIRES_IN")
  def * = (authorizationCode, userId, redirectUri, createdAt, scope, clientId, expiresIn) <> (AuthCode.tupled, AuthCode.unapply)
}