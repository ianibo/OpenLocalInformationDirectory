<html>

<head>
<title><g:message code='spring.security.ui.login.title'/></title>
<meta name='layout' content='main'/>
</head>

<body>
<div class="container" 

<div class="login s2ui_center ui-corner-all" style='text-align:center;'>
  <div class="login-inner">
    <form action='${postUrl}' method='POST' id="loginForm" name="loginForm" autocomplete='off'>
      <div class="sign-in">
        <h1><g:message code='spring.security.ui.login.signin'/></h1>

        <div class="container" style="padding-top:30px;">
          <div style="text-align:center;" class="col-lg-5">
          <p>With your username and password</p><br/>
          <table style="display:inline-block;">
            <tr>
              <td><label for="username"><g:message code='spring.security.ui.login.username'/></label></td>
              <td><input name="j_username" id="username" size="20" /></td>
            </tr>
            <tr>
              <td><label for="password"><g:message code='spring.security.ui.login.password'/></label></td>
              <td><input type="password" name="j_password" id="password" size="20" /></td>
            </tr>
            <!--
            <tr>
              <td colspan="2">
                <input type="checkbox" class="checkbox" name="${rememberMeParameter}" id="remember_me" checked="checked" />
                <label for='remember_me'><g:message code='spring.security.ui.login.rememberme'/></label>
              </td>
            </tr>
            -->
            <tr>
              <td colspan="2">
                <button type="submit" class="btn btn-default btn-x-large">Login</button>
              </td>
            </tr>
            <tr>
              <td colspan='2'>
                <span class="forgot-link">
                  <g:link controller='register' action='forgotPassword'><g:message code='spring.security.ui.login.forgotPassword'/></g:link>
                </span>
              </td>
            </tr>

            <tr>
              <td colspan='2'>
                <s2ui:linkButton elementId='register' controller='register' messageCode='spring.security.ui.login.register'/>
              </td>
            </tr>
          </table>
          </div>
          <div class="col-lg-2">
            <br/>&nbsp;<br/>&nbsp;
            - OR -
          </div>
          <div class="col-lg-5">
            <p>Using Facebook or Google</p><br/>
            <g:set var="savedReq" value="${new org.springframework.security.web.savedrequest.HttpSessionRequestCache().getRequest(request, response)}"/>
            <div class="login-facebook">
              <oauth:connect provider="facebook" 
                             class="btn btn-default btn-facebook btn-x-large" 
                             redirectUrl="${savedReq?.getRedirectUrl()}"
                             id="facebook-connect-link"><i class="fa fa-facebook"></i> | Sign in with Facebook</oauth:connect>
            </div>
            &nbsp;<br/>
            <div class="login-google">
              <oauth:connect class="btn btn-default btn-google-plus btn-x-large" 
                             provider="google" 
                             redirectUrl="${savedReq?.getRedirectUrl()}"
                             id="google-connect-link"><i class="fa fa-google"></i> | Sign in with Google</oauth:connect>
            </div>
          </div>
        </div>
      </div>
    </form>
  </div>
</div>

<script>
$(document).ready(function() {
  $('#username').focus();
});

<s2ui:initCheckboxes/>

</script>

</div>
</body>
</html>
