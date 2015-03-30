package models

import java.util.UUID

import play.api.db.slick.Config.driver.simple._
import org.joda.time.DateTime
import com.github.tototoshi.slick.HsqldbJodaSupport._

case class GrantType(
    id: Option[Int],
    grantType: String
) extends Model

class GrantTypes(tag: Tag) extends Table[GrantType](tag, "GRANT_TYPES") {
  def id = column[Option[Int]]("ID", O.PrimaryKey)
  def grantType = column[String]("GRANT_TYPE")
  def * = (id, grantType) <> (GrantType.tupled, GrantType.unapply)
}

object GrantTypes extends BaseSlickTrait[GrantType] {
  override protected def model = TableQueries.grantTypes
}