dear ${toaddr},

<p>This is the data controller at <a href="${config.baseURL}">The open local information directory</a>. Recently, a user who gave the name "${tracker.givenName}" and email address ${tracker.givenEmail} has requested access to edit <a href="${config.baseURL}/entry/${id}">${entry.title}</a>. Your email address is receiving this message because it is listed as the owner of the information, and we would like you to confirm if this user should be allowed to edit this information. </p>

<p>
<h3>Information supplied by the requester:</h3>
<ul>
   <li>Name:${tracker.givenName}</li>
   <li>Email Address:${tracker.givenEmail}</li>
   <li>Reason:${tracker.message}</li>
</ul>
</p>


There are two ways ways to respond to this request

<ol>
  <li>click <a href="${config.baseURL}/entry/${id}/perms?id=1&token=${tracker.guid}">Here</a>, To log/register in and manage this request</li>
  <li>Or Select one of the following options:
  <ul>
    <li><a href="${config.baseURL}/entry/${id}/perms?id=1&token=${tracker.guid}&action=report">I don't want to approve this request AND I wish to report the requester for sending abusive or offensive requests</a></li>
    <li><a href="${config.baseURL}/entry/${id}/perms?id=1&token=${tracker.guid}&action=reject">I don't want to approve this request (Click here to also to ask not to be contacted in the future)</a></li>
    <li><a href="${config.baseURL}/entry/perms?id=1&token=${tracker.guid}&action=approve">I want to approve this request</a></li>
  </ul>
</ol>
