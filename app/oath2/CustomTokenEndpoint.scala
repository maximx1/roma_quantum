package oath2

import scalaoauth2.provider.{Password, TokenEndpoint}


object CustomTokenEndpoint extends TokenEndpoint {
  val passwordNoCred = new Password() {
    override def clientCredentialRequired = false
  }

  override val handlers = Map(
    "password" -> passwordNoCred
  )
}