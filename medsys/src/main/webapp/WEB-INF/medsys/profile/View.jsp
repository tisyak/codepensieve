
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form:form method="POST" action="#" modelAttribute="adminuser"
	autocomplete="off">
	<div class="form-group">
		<label for="inputCustomerId">Name</label>
		<c:out value="${adminuser.name}" />
		
	</div>
	
	<div class="form-group">
		<label for="inputCustomerId">Username</label>
		<c:out value="${adminuser.username}" />
		<form:hidden path="username" />
	</div>
	
	
	<div class="form-group">
		<label for="inputCustomerId">Email</label>
		<c:out value="${adminuser.email}" />
		
	</div>
	
	
	
	<div class="form-group">
		<label for="inputCustomerId">Mobile No</label>
		<c:out value="${adminuser.mobileNo}" />
		
	</div>
	
	
	
	<div class="form-group">
		<label for="inputCustomerId">Company/LLP Name</label>
		<c:out value="${adminuser.companyName}" />
		
	</div>
	



	<c:url
		value="<%=UIActions.FORWARD_SLASH + UIActions.EDIT_PROFILE%>"
		var="loadProfileEdit" />

	<button type="submit" formmethod="get" class="btn btn-default"
		formaction="${loadProfileEdit}">Edit Profile</button>
</form:form>