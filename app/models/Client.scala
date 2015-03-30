package models

import java.util.UUID

import play.api.db.slick.Config.driver.simple._
import org.joda.time.DateTime
import com.github.tototoshi.slick.HsqldbJodaSupport._

case class Client(
    id: Option[Int],
    secret: Option[String],
    redirectUri: Option[String],
    scope: Option[String]
) extends Model

class Clients(tag: Tag) extends Table[Client](tag, "CLIENTS") {
  def id = column[Int]("ID", O.PrimaryKey)
  def secret = column[Option[String]]("SECRET")
  def redirectUri = column[Option[String]]("REDIRECT_URI")
  def scope = column[Option[String]]("SCOPE")
  def * = (id.?, secret, redirectUri, scope) <> (Client.tupled, Client.unapply)
}

object Clients extends BaseSlickTrait[Client] {
  override protected def model = TableQueries.clients
}