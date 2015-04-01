package models

import org.joda.time.DateTime
import com.github.tototoshi.slick.HsqldbJodaSupport._
import java.util.UUID
import play.api.Play.current
import play.api.db.slick.DB
import play.api.db.slick.Config.driver.simple._

case class AccessToken(
    accessToken: String,
    refreshToken: Option[String],
    userId: UUID,
    scope: Option[String],
    expiresIn: Option[Long],
    createdAt: DateTime,
    clientId: Option[String]
) extends Model

class AccessTokens(tag: Tag) extends Table[AccessToken](tag, "ACCESS_TOKENS") {
  def accessToken = column[String]("ACCESS_TOKEN", O.PrimaryKey)
  def refreshToken = column[Option[String]]("REFRESH_TOKEN")
  def userId = column[UUID]("USER_ID")
  def scope = column[Option[String]]("SCOPE")
  def expiresIn = column[Option[Long]]("EXPIRES_IN")
  def createdAt = column[DateTime]("CREATED_AT")
  def clientId = column[Option[String]]("CLIENT_ID")
  def * = (accessToken, refreshToken, userId, scope, expiresIn, createdAt, clientId) <> (AccessToken.tupled, AccessToken.unapply)
}

object AccessTokens extends BaseSlickTrait[AccessToken] {
  override protected def model = TableQueries.accessTokens
  def -=(userId: UUID, clientId: Option[String]) = DB withSession { implicit session =>
    model.filter(x => x.clientId === clientId && x.userId === userId).delete
  }
}