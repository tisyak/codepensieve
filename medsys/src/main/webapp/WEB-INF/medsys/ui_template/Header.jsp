<%@page import="com.medsys.ui.util.UIActions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
	history.forward();
</script>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- topbar starts -->
<div class="navbar navbar-default" role="navigation">

	<div class="navbar-inner">
		<button type="button" class="navbar-toggle pull-left animated flip">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="./"> <img
			alt="Divine Orthocare Logo" src="<c:url value="/resources/img/logo20.png"/>"
			class="hidden-xs" /> <span style="width=100px">Divine Orthocare Inventory System</span></a>

		<!-- user dropdown starts -->
		<div class="btn-group pull-right">
			<button class="btn btn-default dropdown-toggle"
				data-toggle="dropdown">
				<i class="glyphicon glyphicon-user"></i><span
					class="hidden-sm hidden-xs"> 
					<sec:authorize access="isAuthenticated()">  
					<sec:authentication
						property="principal.username" />
						</sec:authorize>  </span> <span class="caret"></span>
			</button>
			<ul class="dropdown-menu">
				<li><a href="#">Profile</a></li>
				<li class="divider"></li>
				<li><c:url var="logoutUrl" value="/logout" />
					<form action="${logoutUrl}" method="post">
						<input type="submit" value="Logout" class="link" /> <input type="hidden"
							name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form></li>
			</ul>
		</div>
		<!-- user dropdown ends -->

		

	</div>
</div>
<!-- topbar ends -->