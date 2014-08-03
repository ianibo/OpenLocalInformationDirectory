dear ${requester.email},

This is the data controller at The open local information directory. Recently, a user with the name "${requester?.displayName?:'not supplied'}" and email address ${requester.email?:'not supplied'} has requested access to edit <a href="${config.baseURL}/entry/${id}">${entry.title}</a>. This email address is receiving this message because it is listed as the owner of the information, and we would like you to confirm if this user should be allowed to edit this information. There are two ways ways to manage this process:

<ol>
<li>click <a href="${config.baseURL}/entry/${id}/perms?id=1&token=tok">Here</a>, which will ask you to log in to the system and manage this request</li>
<li>Select one of the following options:
<ul>
<li><a href="${config.baseURL}/entry/${id}/perms?id=1&token=tok&action=reject">I don't want to approve this request but please continue to ask me in future</a></li>
<li><a href="${config.baseURL}/entry/${id}/perms?id=1&token=tok&action=rejectPlus">I don't want to approve this request and please don't ask me about future requests</a> - (An administrator may subseqently authorize a user to edit this data if they can demonstrate that they have a legitimate reason to do so).</li>
<li><a href="${config.baseURL}/entry/perms?id=1&token=tok&action=approve">I want to approve this request (and I'm happy to continue to approve future requests)</a></li>
<li><a href="${config.baseURL}/entry/perms?id=1&token=tok&action=approvePlus">I want to approve this request, please make this user responsible for all future requests</a></li>
</li>
