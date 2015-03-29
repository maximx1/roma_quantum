package models

import play.api.Play.current
import play.api.db.slick.DB
import play.api.db.slick.Config.driver.simple._
import scala.util.Try

case class User(
  id: Option[Int],
  firstName: String,
  lastName: String,
  nickname: String,
  email: String
) extends Model

class Users(tag: Tag) extends Table[User](tag, "USERS") {
  def id = column[Option[Int]]("ID", O.PrimaryKey, O.AutoInc)
  def firstName = column[String]("FIRST_NAME", O.NotNull)
  def lastName = column[String]("LAST_NAME", O.NotNull)
  def nickname = column[String]("NICKNAME", O.NotNull)
  def email = column[String]("EMAIL")
  def * =  (id, firstName, lastName, nickname, email) <> (User.tupled, User.unapply)
}

object Users extends BaseSlickTrait[User] {
  override protected def model = TableQueries.users
  
  def byId(id: Int) = DB withSession { implicit session => model.filter{_.id === id}.list}
}