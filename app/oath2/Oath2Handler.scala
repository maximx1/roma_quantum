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
      case Failure(x) => throw new Exception(x)
    }
  }
  
  def getStoredAccessToken(authInfo: AuthInfo[User]): Future[Option[AccessToken]] = Future {
    AccessTokens.findByIds(authInfo.user.id, authInfo.clientId).headOption map { token =>
      AccessToken(token.accessToken, token.refreshToken, token.scope, token.expiresIn, token.createdAt.toDate)
    }
  }
  
  def refreshAccessToken(authInfo: AuthInfo[User], refreshToken: String): Future[AccessToken] = createAccessToken(authInfo)
  
  def findAuthInfoByCode(code: String): Future[Option[AuthInfo[User]]] = Future {
    AuthCodes.byCode(code) flatMap { authCode =>
      Users.byId(authCode.userId).headOption map { user =>
        AuthInfo(user, authCode.clientId, authCode.scope, authCode.redirectUri)
      }
    }
  }
  
  def findAuthInfoByRefreshToken(refreshToken: String): Future[Option[AuthInfo[User]]] = Future {
    AccessTokens.findByRefreshToken(refreshToken) flatMap { x =>
      Users.byId(x.userId).headOption map { user =>
        AuthInfo(user, x.clientId, x.scope, Some(""))
      }
    }
  }
  
  def findClientUser(clientCredential: ClientCredential, scope: Option[String]): Future[Option[User]] = Future.successful(None)
  
  def findAccessToken(token: String): Future[Option[AccessToken]] = Future {
    AccessTokens.findByToken(token) map { token => 
      AccessToken(token.accessToken, token.refreshToken, token.scope, token.expiresIn, token.createdAt.toDate)
    }
  }
  
  def findAuthInfoByAccessToken(accessToken: AccessToken): Future[Option[AuthInfo[User]]] = Future {
    AccessTokens.findByToken(accessToken.token) flatMap { token => 
      Users.byId(token.userId).headOption map { user =>
        AuthInfo(user, token.clientId, token.scope, Some(""))
      }
    }
  }
  
  def generateToken = new BASE64Encoder().encode(UUID.randomUUID.toString.getBytes)

}