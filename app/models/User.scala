package models

import play.api.Play.current
import play.api.db.slick.DB
import play.api.db.slick.Config.driver.simple._
import org.joda.time.DateTime
import com.github.tototoshi.slick.HsqldbJodaSupport._
import scala.util.Try
import java.util.UUID

case class User(
  id: UUID,
  firstName: String,
  lastName: String,
  email: String,
  password: String,
  createdAt: DateTime,
  createdByUuid: UUID,
  updatedAt: DateTime,
  updatedByUuid: UUID,
  deletedAt: Option[DateTime],
  deletedByUuid: Option[UUID]
) extends Model

class Users(tag: Tag) extends Table[User](tag, "USERS") {
  def id = column[UUID]("ID", O.PrimaryKey)
  def firstName = column[String]("FIRST_NAME", O.NotNull)
  def lastName = column[String]("LAST_NAME", O.NotNull)
  def email = column[String]("EMAIL", O.NotNull)
  def password = column[String]("PASSKEY", O.NotNull)
  def createdAt = column[DateTime]("CREATED_AT", O.NotNull)
  def createdByUuid = column[UUID]("CREATED_BY_UUID", O.NotNull)
  def updatedAt = column[DateTime]("UPDATED_AT", O.NotNull)
  def updatedByUuid = column[UUID]("UPDATED_BY_UUID", O.NotNull)
  def deletedAt = column[Option[DateTime]]("DELETED_AT", O.NotNull)
  def deletedByUuid = column[Option[UUID]]("DELETED_BY_UUID", O.NotNull)
  def * = (id, firstName, lastName, email, password, createdAt, createdByUuid, updatedAt, updatedByUuid, deletedAt, deletedByUuid) <> (User.tupled, User.unapply)
}

object Users extends BaseSlickTrait[User] {
  override protected def model = TableQueries.users
  def byId(id: UUID) = DB withSession { implicit session => model.filter{_.id === id}.list}
}