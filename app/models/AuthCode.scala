package models

import org.joda.time.DateTime
import com.github.tototoshi.slick.HsqldbJodaSupport._
import java.util.UUID
import play.api.Play.current
import play.api.db.slick.DB
import play.api.db.slick.Config.driver.simple._

case class AuthCode(
    authorizationCode: String,
    userId: UUID,
    redirectUri: Option[String],
    createdAt: DateTime,
    scope: Option[String],
    clientId: Option[String],
    expiresIn: Int
) extends Model

class AuthCodes(tag: Tag) extends Table[AuthCode](tag, "AUTH_CODES") {
  def authorizationCode = column[String]("AUTHORIZATION_CODE", O.PrimaryKey)
  def userId = column[UUID]("USER_ID")
  def redirectUri = column[Option[String]]("REDIRECT_URI")
  def createdAt = column[DateTime]("CREATED_AT")
  def scope = column[Option[String]]("SCOPE")
  def clientId = column[Option[String]]("CLIENT_ID")
  def expiresIn = column[Int]("EXPIRES_IN")
  def * = (authorizationCode, userId, redirectUri, createdAt, scope, clientId, expiresIn) <> (AuthCode.tupled, AuthCode.unapply)
}

object AuthCodes extends BaseSlickTrait[AuthCode] {
  override protected def model = TableQueries.authCodes
  
  def byCode(code: String) = DB withSession { implicit session => model.filter{_.authorizationCode === code}.list.headOption }
}