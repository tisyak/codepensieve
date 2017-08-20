
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.ADD_SET%>"
	var="action" />
<form:form class="form-horizontal" method="POST" action="${action}"
	modelAttribute="set" autocomplete="off">

	<form:hidden path="setId" cssClass="form-control" title="setId"
		autocomplete="off" />

	<div class="form-group">
		<label class="col-sm-2" for="inputSetName">Set Name</label>
		<div class="col-sm-5">
			<form:input path="setName" cssClass="form-control" title="setName" />
			<form:errors path="setName" cssClass="error" />
		</div>

	</div>

	<div class="form-group">
		<label class="col-sm-2" for="inputSetDesc">Set Desc</label>
		<div class="col-sm-5">
			<form:input path="setDesc" cssClass="form-control" title="setDesc" />
		</div>
		<form:errors path="setDesc" cssClass="error" />

	</div>

	<button type="submit" class="btn btn-primary">Add Products to
		Set</button>
	<c:url value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_SETS%>"
		var="listAllSetAction" />

	<button type="submit" formmethod="get" class="btn btn-default"
		formaction="${listAllSetAction}">Cancel / Back</button>
</form:form>