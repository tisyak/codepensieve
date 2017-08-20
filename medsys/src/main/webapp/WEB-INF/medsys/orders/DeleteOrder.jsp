
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>

<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.DELETE_ORDER%>"
	var="action" />
<form:form class="form-horizontal" method="POST" action="${action}" modelAttribute="order"
	autocomplete="off">

	<form:hidden path="orderId" cssClass="form-control" title="orderId"
		autocomplete="off" />

	<div class="form-group">
		<label class="col-sm-2" for="inputCustomerName">Order From</label>
		<div class="col-sm-5">
		<form:input disabled="true" path="customer.name" cssClass="form-control"
			title="customer.name" />
		
		<form:hidden path="customer.customerId" />
		</div>
		
		<label class="col-sm-1" for="inputOrderNumber">Order No</label>
		<div class="col-sm-4">
		<form:input disabled="true" path="orderNumber" cssClass="form-control"
			title="orderNumber" />
			<form:hidden path="orderNumber" />
			</div>
	</div>	
	

	<div class="form-group">
		<label class="col-sm-2" for="inputReferredBy">Referred By</label>
		<div class="col-sm-5">
		<form:input path="refSource" disabled="true" cssClass="form-control" title="refSource"
			autocomplete="off" />
			</div>
		<label class="col-sm-1" for="inputOrderDate">Order Dt</label>
		<div class="col-sm-4">
		<form:input path="orderDate" disabled="true" placeholder="Order Date"
			cssClass="form-control" />
			</div>
	</div>


	<div class="form-group">
	<label  class="col-sm-2" for="inputSetName">Order for Set</label>
	<div class="col-sm-5">
		<form:input disabled="true" path="set.setName" cssClass="form-control"
			title="set.setName" />
		
		</div>
		<form:hidden path="set.setId" />
		<form:errors path="set.setId" cssClass="error" />
		<label  class="col-sm-1"  for="inputDeliveryDate">Delivery Dt</label>
		<div class="col-sm-4">
		<form:input path="deliveryDate" disabled="true"  placeholder="Delivery Date"
			cssClass="form-control" />
			</div>
	</div>
	
	<div class="form-group">
		<label class="col-sm-2" for="inputOrderStatus">Order Status</label>
		<div class="col-sm-5">
		<form:input disabled="true" path="orderStatus.orderStatusDesc" cssClass="form-control"
			title="orderStatus" />
			<form:hidden path="orderStatus.orderStatusId" />
			</div>
	</div>

	
	<button type="submit" class="btn btn-primary">Confirm Delete</button>
	<c:url value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_SEARCH_ORDERS%>"
		var="searchAllOrdersAction" />

	<button type="submit" formmethod="get" class="btn btn-default"
		formaction="${searchAllOrdersAction}">Back</button>
</form:form>