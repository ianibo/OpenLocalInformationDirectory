dear ${owner.email},

This is the data controller at The open local information directory. Recently, a user with Name ${who?.displayName} and email address ${who?.email} has requested access to edit <a href="http://olid.localdomain:8080/search/requestAccess/requestAccess/${shortcode}">${entry.title}</a>. This email address is receiving this message because it is listed as the owner of the information, and we would like you to confirm if this user should be allowed to edit this information. There are two ways ways to manage this process:

<ol>
<li>click <g:link controller="user" action="managePermissionRequests" params="${[id:1,token:'xxssdd']}">Here</a>, which will ask you to log in to the system and manage this request</li>
<li>Select one of the following options:
<ul>
<li><a href="">I don't want to approve this request, and please don't bother me again</a> - (An administrator may subseqently authorize a user to edit this data
if they can demonstrate that they have a legitimate reason to do so).</li>
<li><a href="">I want to approve this request, and I'm happy to approve future requests</a></li>
<li><a href="">I want to approve this request, please make this user responsible for all future requests</a></li>
</li>
