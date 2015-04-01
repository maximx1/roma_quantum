package oath2

import models.{AccessToken => AccessTokenE, _}
import scala.concurrent.Future
import scalaoauth2.provider.{DataHandler, ClientCredential, AuthInfo, AccessToken}
import org.mindrot.jbcrypt.BCrypt
import sun.misc.BASE64Encoder
import java.util.UUID
import scala.util.{Success, Failure}
import org.joda.time.DateTime

class Oath2Handler extends DataHandler[User] {
  import scala.concurrent.ExecutionContext.Implicits.global
  
  def validateClient(clientCredential: ClientCredential, grantType: String): Future[Boolean] = Future {
    Clients.validate(clientCredential.clientId, clientCredential.clientSecret, grantType)
  }
  
  def findUser(username: String, password: String): Future[Option[User]] = Future {
    Users.byEmail(username).filter{x => BCrypt.checkpw(password, x.password)}.headOption
  }
  
  def createAccessToken(authInfo: AuthInfo[User]): Future[AccessToken] = Future {
    val expiration = Some(3600L)
    val refreshToken = Some(generateToken)
    val accessToken = generateToken
    val timestamp = DateTime.now
    val newToken = AccessTokenE(accessToken, refreshToken, authInfo.user.id, authInfo.scope, expiration, timestamp, authInfo.clientId)
    AccessTokens -= (authInfo.user.id, authInfo.clientId) 
    (AccessTokens += newToken) match {
      case Success(x) => AccessToken(accessToken, refreshToken, authInfo.scope, expiration, timestamp.toDate)
    }
  }
  
  def getStoredAccessToken(authInfo: AuthInfo[User]): Future[Option[AccessToken]] = Future {
    AccessTokens.findByIds(authInfo.user.id, authInfo.clientId).headOption match {
      case Some(token) => Some(AccessToken(token.accessToken, token.refreshToken, token.scope, token.expiresIn, token.createdAt.toDate))
      case _ => None
    }
  }
  
  def refreshAccessToken(authInfo: AuthInfo[User], refreshToken: String): Future[AccessToken] = createAccessToken(authInfo)
  
  def findAuthInfoByCode(code: String): Future[Option[AuthInfo[User]]] = Future {
    AuthCodes.byCode(code) match {
      case Some(authCode) => {
        Users.byId(authCode.userId).headOption match {
          case Some(user) => Some(AuthInfo(user, authCode.clientId, authCode.scope, authCode.redirectUri))
          case _ => None
        }  
      }
      case _ => None
    }
  }
  
  def findAuthInfoByRefreshToken(refreshToken: String): Future[Option[AuthInfo[User]]] = ???
  
  def findClientUser(clientCredential: ClientCredential, scope: Option[String]): Future[Option[User]] = ???
  
  def findAccessToken(token: String): Future[Option[AccessToken]] = ???
  
  def findAuthInfoByAccessToken(accessToken: AccessToken): Future[Option[AuthInfo[User]]] = ???
  
  def generateToken = new BASE64Encoder().encode(UUID.randomUUID.toString.getBytes)

}