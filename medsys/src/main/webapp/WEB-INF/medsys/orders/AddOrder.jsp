
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="<c:url value="/resources/js/moment.min.js"/>"></script>
<script src="<c:url value="/resources/js/datepicker.js"/>"></script>
<link href="<c:url value="/resources/css/datepicker.css"/>"
	rel='stylesheet'>
<!-- JQGrid Action URLs -->	
<c:url value="/orderproduct/records" var="recordsUrl"/>
<c:url value="/orderproduct/create" var="addUrl"/>
<c:url value="/orderproduct/update" var="editUrl"/>
<c:url value="/orderproduct/delete" var="deleteUrl"/>
<!--End of JQGrid Action URLs -->	

<!-- JQGrid Header Files -->

<link rel="stylesheet" type="text/css" media="screen"
	href='<c:url value="/resources/css/ui-lightness/jquery-ui-min.css"/>' />
<link rel="stylesheet" type="text/css" media="screen"
	href='<c:url value="/resources/css/ui.jqgrid.min.css"/>' />

<script type='text/javascript'
	src='<c:url value="/resources/js/jquery-ui.min.js"/>'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/jqgrid/grid.locale-en-min.js"/>'></script>
<script type='text/javascript'
	src='<c:url value="/resources/js/jqgrid/jquery.jqgrid.min.js"/>'></script>
<script type='text/javascript'
	src='<c:url value="/resources/js/jqgrid/order.product.grid.js"/>'></script>


<!-- End of JQGrid Header Files -->


<script>
	var dateErrMsg = 'Please enter a valid date';
</script>


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.ADD_ORDER%>"
	var="action" />
<form:form method="POST" action="${action}" modelAttribute="order"
	autocomplete="off">

	<form:hidden path="orderId" cssClass="form-control" title="orderId"
		autocomplete="off" />

	<div class="form-group col-md-6">
		<label for="inputOrderNumber">Order No</label>
		<form:label path="orderNumber" cssClass="form-control"
			title="orderNumber" />


	</div>
	<div class="form-group col-md-6">
		<label for="inputCustomerName">Order From</label>
		<form:select path="customer.customerId" cssClass="resizedSelect">
			<form:option value="" label="--  Select  --" />
			<c:forEach items="${customerList}" var="customer">
				<form:option value="${customer.customerId}">${customer.name}</form:option>
			</c:forEach>
		</form:select>
		<form:hidden path="customer.customerId" />
		<form:errors path="customer.customerId" cssClass="error" />

	</div>

	<div class="form-group col-md-6">
		<label for="inputReferredBy">Referred By</label>
		<form:input path="refSource" cssClass="form-control" title="refSource"
			autocomplete="off" />
		<form:errors path="refSource" cssClass="error" />
	</div>



	<div class="form-group col-md-6">
		<form:input path="orderDate" placeholder="Order Date"
			cssClass="form-control" />
	</div>
	<div class="form-group col-md-6">
		<form:input path="deliveryDate" placeholder="Delivery Date"
			cssClass="form-control" />
	</div>


	<!-- JQGrid HTML -->

	<h1 id='banner'>Products</h1>

	<div id='jqgrid'>
		<table id='grid'></table>
		<div id='pager'></div>
	</div>

	<div id='msgbox' title='' style='display: none'></div>

	<!-- End of JQGrid HTML -->
	
	
	<button type="submit" class="btn btn-primary">Add Order</button>
	<c:url value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_ORDERS%>"
		var="listAllOrdersAction" />

	<button type="submit" formmethod="get" class="btn btn-default"
		formaction="${listAllOrdersAction}">Cancel</button>
</form:form>