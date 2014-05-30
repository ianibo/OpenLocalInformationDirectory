package search


import grails.converters.JSON
import org.scribe.model.Token
import org.springframework.security.web.savedrequest.*;

class OauthHandlerController {

  def oauthService

  // See https://github.com/pledbrook/talk-submissions/blob/master/grails-app/controllers/cacoethes/auth/SpringSecurityOAuthController.groovy

  def index() {
    log.debug("Index");
  }

  def onSuccess() {
    log.debug("onSuccess ${params}");

    // Validate the 'provider' URL. Any errors here are either misconfiguration
    // or web crawlers (or malicious users).
    if (!params.provider) {
      renderError 400, "The Spring Security OAuth callback URL must include the 'provider' URL parameter."
      return
    }


    def sessionKey = oauthService.findSessionKeyForAccessToken(params.provider)
    if (!session[sessionKey]) {
      renderError 500, "No OAuth token in the session for provider '${params.provider}'!"
      return
    }

    Token oAuthToken = (Token) session[oauthService.findSessionKeyForAccessToken(params.provider)]

    // if (oAuthToken.principal instanceof GrailsUser) {
    //   authenticateAndRedirect(oAuthToken, defaultTargetUrl)
    // } else {
      // This OAuth account hasn't been registered against an internal
      // account yet. Give the oAuthID the opportunity to create a new
      // internal account or link to an existing one.
    //   session[SPRING_SECURITY_OAUTH_TOKEN] = oAuthToken

    //   def redirectUrl = SpringSecurityUtils.securityConfig.oauth.registration.askToLinkOrCreateAccountUri
    //   assert redirectUrl, "grails.plugins.springsecurity.oauth.registration.askToLinkOrCreateAccountUri" +
    //                 " configuration option must be set!"
    //   log.debug "Redirecting to askToLinkOrCreateAccountUri: ${redirectUrl}"
    //   redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl])
    // }

  }

  def onFailure() {
    log.debug("onFailure");
  }

  def facebookSuccess() {
    def result = [:]
    try {
      log.debug("FB Success");
      Token facebookAccessToken = (Token) session[oauthService.findSessionKeyForAccessToken('facebook')]
      def facebookResource = oauthService.getFacebookResource(facebookAccessToken, "https://graph.facebook.com/me")
      def facebookResponse = JSON.parse(facebookResource?.getBody())

      SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);

      log.debug("Email = ${facebookResponse.email}, saved req:${savedRequest}")
    }
    catch ( Exception e ) {
      log.error("Problem",e)
    }
    finally {
      log.debug("Complete");
    }

    render result as JSON
  }
}
