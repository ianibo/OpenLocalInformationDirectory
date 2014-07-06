<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <title>User Profile</title>
  </head>
  <body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h3>User Preferences</h3>
			<dl class="dl-horizontal">
				<dt>Show Quick View :</dt>
				<dd>
					<g:xEditableRefData owner="${user}" field="showQuickView"
						config="YN" />
				</dd>
				<dt>Default Page Size :</dt>
				<dd>
					<g:xEditable owner="${user}" field="defaultPageSize" />
				</dd>
		  </dl>
		</div>
	</div>
</body>
</html>
