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
		<a class="navbar-brand" href="index.html"> <img
			alt="MEDSYS Logo" src="<c:url value="/resources/img/logo20.png"/>"
			class="hidden-xs" /> <span>DSC Inventory System</span></a>

		<!-- user dropdown starts -->
		<div class="btn-group pull-right">
			<button class="btn btn-default dropdown-toggle"
				data-toggle="dropdown">
				<i class="glyphicon glyphicon-user"></i><span
					class="hidden-sm hidden-xs"> <sec:authentication
						property="principal.username" /></span> <span class="caret"></span>
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

		<!-- theme selector starts -->
		<!-- <div class="btn-group pull-right theme-container">
                <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <i class="glyphicon glyphicon-tint"></i><span
                        class="hidden-sm hidden-xs"> Change Theme / Skin</span>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" id="themes">
                    <li><a data-value="classic" href="#"><i class="whitespace"></i> Classic</a></li>
                    <li><a data-value="cerulean" href="#"><i class="whitespace"></i> Cerulean</a></li>
					<li><a data-value="simplex" href="#"><i class="whitespace"></i> Simplex</a></li>
                    <li><a data-value="darkly" href="#"><i class="whitespace"></i> Darkly</a></li>
                    <li><a data-value="lumen" href="#"><i class="whitespace"></i> Lumen</a></li>
                    <li><a data-value="slate" href="#"><i class="whitespace"></i> Slate</a></li>
                    <li><a data-value="united" href="#"><i class="whitespace"></i> United</a></li>
                </ul>
            </div> -->
		<!-- theme selector ends -->

		

	</div>
</div>
<!-- topbar ends -->