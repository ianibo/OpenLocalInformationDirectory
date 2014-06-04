

Setting up FB authentication

1. For development you will need to set up a olid.localdomain pointing at 127.0.0.1 in your hosts file in order to test
facebook login.

2. go to https://developers.facebook.com/ and go to apps -> set up a new app

3. On your new app you will need to go to settings (LHS) and add a new domain

4. Here is the ~/.grails/search-config.groovy

sysusers=[
  [ name:'titleBot', pass:'titleBotPass', display:'Title Augment Bot', roles:['ROLE_API'] ],
  [ name:'admin', pass:'admin', display:'Admin', roles:['ROLE_ADMIN', 'ROLE_USER' ] ]
]
// https://github.com/enr/grails-spring-security-oauth
def appName = grails.util.Metadata.current.'app.name'
def baseURL = grails.serverURL ?: "http://olid.localdomain:${System.getProperty('server.port', '8080')}/${appName}"
oauth {
  providers {
    facebook {
      callback = "http://olid.localdomain:8080/search/oauth/facebook/callback"
      api = org.scribe.builder.api.FacebookApi
      key = 'FBKEY'
      secret = 'FBSECRET'
      successUri = '/oauth/facebook/success'
      failureUri = '/oauth/facebook/failure'
      callback = "${baseURL}/oauth/facebook/callback"
    }
  }
  debug=true
}

println(oauth);

