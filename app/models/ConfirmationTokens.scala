package models

import java.util.UUID

import play.api.db.slick.Config.driver.simple._
import org.joda.time.DateTime
import com.github.tototoshi.slick.HsqldbJodaSupport._

case class ConfirmationToken(
    id: Option[Int],
    uuid: UUID,
    email: String,
    creationTime: DateTime,
    expirationTime: DateTime,
    isSignUp: Boolean
) extends Model

class ConfirmationTokens(tag: Tag) extends Table[ConfirmationToken](tag, "CONFIRMATION_TOKENS") {
  def id = column[Option[Int]]("ID", O.PrimaryKey, O.AutoInc)
  def uuid = column[UUID]("UUID")
  def email = column[String]("EMAIL")
  def creationTime = column[DateTime]("CREATION_TIME")
  def expirationTime = column[DateTime]("EXPIRATION_TIME")
  def isSignUp = column[Boolean]("IS_SIGN_UP")
  def * = (id, uuid, email, creationTime, expirationTime, isSignUp) <> (ConfirmationToken.tupled, ConfirmationToken.unapply)
}

object ConfirmationTokens extends BaseSlickTrait[ConfirmationToken] {
  override protected def model = TableQueries.confirmationTokens
}