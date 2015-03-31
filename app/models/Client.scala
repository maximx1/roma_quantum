package models

import java.util.UUID
import play.api.db.slick.Config.driver.simple._
import org.joda.time.DateTime
import com.github.tototoshi.slick.HsqldbJodaSupport._
import scala.concurrent.{Future, ExecutionContext}
import play.api.Play.current
import play.api.db.slick.DB

case class Client(
    id: String,
    secret: Option[String],
    redirectUri: Option[String],
    scope: Option[String]
) extends Model

class Clients(tag: Tag) extends Table[Client](tag, "CLIENTS") {
  def id = column[String]("ID", O.PrimaryKey)
  def secret = column[Option[String]]("SECRET")
  def redirectUri = column[Option[String]]("REDIRECT_URI")
  def scope = column[Option[String]]("SCOPE")
  def * = (id, secret, redirectUri, scope) <> (Client.tupled, Client.unapply)
}

object Clients extends BaseSlickTrait[Client] {
  override protected def model = TableQueries.clients
  
  def validate(id: String, secret: Option[String], grantType: String)(implicit executionContext: ExecutionContext): Future[Boolean] = Future {
    DB.withSession { implicit session => 
      val result = for {
        ((clients, clientGrantTypes), grantTypes) <- TableQueries.clients innerJoin TableQueries.clientGrantTypes on (_.id === _.clientId) innerJoin TableQueries.grantTypes on (_._2.grantTypeId === _.id)
        if clients.id === id && clients.secret === secret && grantTypes.grantType === grantType
      } yield clients
      result.firstOption.isDefined
    }
  }
}