
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	response.addHeader("X-FRAME-OPTIONS", "DENY");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>

<head>
<title><spring:message code="medsys.service" /></title>
<link rel="icon" href="<c:url value="/resources/img/favicon.ico" />"
	type="image/x-icon" />
<link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" type="image/x-icon" />
<meta name="description" content="DSC Inventory System" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

    <meta charset="utf-8">
    <title>DSC Inventory System Portal</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
       
    <!-- The styles -->
    <link id="bs-css" href="<c:url value="/resources/css/bootstrap-cerulean.min.css"/>" rel="stylesheet">

    <link href="<c:url value="/resources/css/charisma-app.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/bower_components/chosen/chosen.min.css"/>" rel='stylesheet'>
    <link href="<c:url value="/resources/bower_components/responsive-tables/responsive-tables.css"/>" rel='stylesheet'>
    <link href="<c:url value="/resources/css/jquery.noty.css"/>" rel='stylesheet'>
    <link href="<c:url value="/resources/css/noty_theme_default.css"/>" rel='stylesheet'>
    <link href="<c:url value="/resources/css/elfinder.min.css"/>" rel='stylesheet'>
    <link href="<c:url value="/resources/css/elfinder.theme.css"/>" rel='stylesheet'>
    <link href="<c:url value="/resources/css/uploadify.css"/>" rel='stylesheet'>
    <link href="<c:url value="/resources/css/animate.min.css"/>" rel='stylesheet'>
    <link href="<c:url value="/resources/css/style.css"/>" rel='stylesheet'>

    <!-- jQuery -->
    <script src="<c:url value="/resources/bower_components/jquery/jquery.min.js"/>"></script>
    

    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <script>var ctx = "${pageContext.request.contextPath}"; </script>
    <sec:csrfMetaTags />
</head>



<body>
	<div id="main">
		<header>
			<tiles:insertAttribute name="header" />

		</header>
		<div class="ch-container">
    <div class="row">
        
			<tiles:insertAttribute name="menu" />
		
		<noscript>
			<div id="noscriptmsg">
				<tiles:insertAttribute name="jsdisable" />
			</div>
		</noscript>
		 <div id="content" class="col-lg-10 col-sm-10" style="display: none;">
            <!-- content starts -->
		
				<tiles:insertAttribute name="message" />
				<tiles:insertAttribute name="body" />
			</div>
			</div>
			</div>
		
		<%-- <div id="scroll">
			<a title="Scroll to the top" class="top" href="#"><img
				src="<c:url value="/resources/images/top.png"/>" alt="top" /></a>
		</div> --%>
	</div>

	<div>
		<footer>
			<tiles:insertAttribute name="footer" />
		</footer>
	</div>

	



	<script>
	//document.getElementById("noscriptmsg").style.display = "none";
	document.getElementById("content").style.display = "block";
	</script>


<!-- external javascript -->

<script src="<c:url value="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"/>"></script>

<!-- library for cookie management -->
<script src="<c:url value="/resources/js/jquery.cookie.js"/>"></script>
<!-- calender plugin -->
<script src="<c:url value="/resources/bower_components/moment/min/moment.min.js"/>"></script>
<!-- data table plugin -->
<script src="<c:url value="/resources/js/jquery.dataTables.min.js"/>"></script>

<!-- select or dropdown enhancer -->
<script src="<c:url value="/resources/bower_components/chosen/chosen.jquery.min.js"/>"></script>

<!-- notification plugin -->
<script src="<c:url value="/resources/js/jquery.noty.js"/>"></script>
<!-- library for making tables responsive -->
<script src="<c:url value="/resources/bower_components/responsive-tables/responsive-tables.js"/>"></script>

<!-- star rating plugin -->
<script src="<c:url value="/resources/js/jquery.raty.min.js"/>"></script>

<!-- autogrowing textarea plugin -->
<script src="<c:url value="/resources/js/jquery.autogrow-textarea.js"/>"></script>
<!-- multiple file upload plugin -->
<script src="<c:url value="/resources/js/jquery.uploadify-3.1.min.js"/>"></script>
<!-- history.js for cross-browser state change on ajax -->
<script src="<c:url value="/resources/js/jquery.history.js"/>"></script>

<!-- application script for Charisma demo -->
<script src="<c:url value="/resources/js/charisma.js"/>"></script>



</body>
</html>

