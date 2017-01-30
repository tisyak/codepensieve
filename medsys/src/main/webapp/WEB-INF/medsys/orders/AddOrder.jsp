
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<script src="<c:url value="/resources/js/moment.min.js"/>"></script>
<script src="<c:url value="/resources/js/datepicker.js"/>"></script>
<link href="<c:url value="/resources/css/datepicker.css"/>" rel='stylesheet'>

<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.ADD_ORDER%>"
	var="action" />
<form:form method="POST" action="${action}" modelAttribute="order"
	autocomplete="off">

	<form:hidden path="orderId" cssClass="form-control" title="orderId"
		autocomplete="off" />

	<div class="form-group col-md-6">
		<label for="inputOrderNumber">Order No</label>
		<form:input path="orderNumber" cssClass="form-control"
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
	
	<div class="form-group col-md-6">
		<label for="inputSetName">Order for Set</label>
		<form:select path="set.setId" cssClass="resizedSelect">	
		<form:option value="" label="--  Select Set --" />
			<c:forEach items="${setList}" var="set">
				<form:option value="${set.setId}">${set.setName}</form:option>
			</c:forEach>
		</form:select>
		<form:hidden path="set.setId" />
		<form:errors path="set.setId" cssClass="error" />

	</div>
	
	<div class="form-group col-md-6">
		<label for="inputOrderNumber">Order Status</label>
		<form:input path="orderStatus" cssClass="form-control"
			title="orderStatus" />
	</div>

	
	<button type="submit" class="btn btn-primary">Add Products to Order</button>
	<c:url value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_ORDERS%>"
		var="listAllOrdersAction" />

	<button type="submit" formmethod="get" class="btn btn-default"
		formaction="${listAllOrdersAction}">Cancel</button>
</form:form>