package oath2

import models._
import scala.concurrent.Future
import scalaoauth2.provider.{DataHandler, ClientCredential, AuthInfo, AccessToken}
import org.mindrot.jbcrypt.BCrypt

class Oath2Handler extends DataHandler[User] {
  import scala.concurrent.ExecutionContext.Implicits.global
  
  def validateClient(clientCredential: ClientCredential, grantType: String): Future[Boolean] = Future {
    Clients.validate(clientCredential.clientId, clientCredential.clientSecret, grantType)
  }
  
  def findUser(username: String, password: String): Future[Option[User]] = Future {
    Users.byEmail(username).filter{x => BCrypt.checkpw(password, x.password)}.headOption
  }
  
  def createAccessToken(authInfo: AuthInfo[User]): Future[AccessToken] = ???
  
  def getStoredAccessToken(authInfo: AuthInfo[User]): Future[Option[AccessToken]] = ???
  
  def refreshAccessToken(authInfo: AuthInfo[User], refreshToken: String): Future[AccessToken] = ???
  
  def findAuthInfoByCode(code: String): Future[Option[AuthInfo[User]]] = ???
  
  def findAuthInfoByRefreshToken(refreshToken: String): Future[Option[AuthInfo[User]]] = ???
  
  def findClientUser(clientCredential: ClientCredential, scope: Option[String]): Future[Option[User]] = ???
  
  def findAccessToken(token: String): Future[Option[AccessToken]] = ???
  
  def findAuthInfoByAccessToken(accessToken: AccessToken): Future[Option[AuthInfo[User]]] = ???

}