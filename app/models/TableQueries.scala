package models

import scala.slick.lifted.TableQuery

/**
 * Container filled with TableQuery objects.
 */
object TableQueries {
  val users = TableQuery[Users]
}