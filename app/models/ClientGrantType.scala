package models

import java.util.UUID

import play.api.db.slick.Config.driver.simple._
import org.joda.time.DateTime
import com.github.tototoshi.slick.HsqldbJodaSupport._

case class ClientGrantType(
    clientId: Int,
    grantTypeId: Int
)

class ClientGrantTypes(tag: Tag) extends Table[ClientGrantType](tag, "CLIENT_GRANT_TYPES") {
  def clientId = column[Int]("CLIENT_ID")
  def grantTypeId = column[Int]("GRANT_TYPE_ID")
  def * = (clientId, grantTypeId) <> (ClientGrantType.tupled, ClientGrantType.unapply)
  val pk = primaryKey("PK_CLIENT_GRANT_TYPE", (clientId, grantTypeId))
}

object ClientGrantTypes extends BaseSlickTrait[ClientGrantType] {
  override protected def model = TableQueries.clientGrantTypes
}