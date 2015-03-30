package models

import scala.slick.lifted.TableQuery

/**
 * Container filled with TableQuery objects.
 */
object TableQueries {
	  val accessTokens = TableQuery[AccessTokens]
    val authCodes = TableQuery[AuthCodes]
    val clients = TableQuery[Clients]
    val clientGrantTypes = TableQuery[ClientGrantTypes]
		val confirmationTokens = TableQuery[ConfirmationTokens]
    val grantTypes = TableQuery[GrantTypes]
    val users = TableQuery[Users]
}