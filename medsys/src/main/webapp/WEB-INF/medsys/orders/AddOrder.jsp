
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="<c:url value="/resources/js/moment.min.js"/>"></script>
<script src="<c:url value="/resources/js/datepicker.js"/>"></script>
<link href="<c:url value="/resources/css/datepicker.css"/>"
	rel='stylesheet'>

<script>
	var dateErrMsg = 'Please enter a valid date';
	$(document)
			.ready(
					function() {
						var form = $("#spData");

						$("#mouStartDate")
								.blur(
										function() {
											if (!validate_field(
													$("#mouStartDate"),
													true,
													'(^([0-9]{1,2})\/([0-9]{1,2})\/([0-9]{4})$)',
													dateErrMsg)) {
												return false;
											}
										});

						$("#mouExpiryDate")
								.blur(
										function() {
											if (!validate_field(
													$("#mouExpiryDate"),
													true,
													'(^([0-9]{1,2})\/([0-9]{1,2})\/([0-9]{4})$)',
													dateErrMsg)) {
												return false;
											}
										});
					});
</script>


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.ADD_ORDER%>"
	var="action" />
<form:form method="POST" action="${action}" modelAttribute="order"
	autocomplete="off">

	<form:hidden path="orderId" cssClass="form-control" title="orderId"
		autocomplete="off" />

	<div class="form-group">
		<label for="inputOrderNumber">Order No</label>
		<form:label path="orderNumber" cssClass="form-control"
			title="orderNumber" />


	</div>
	<div class="form-group">
		<label for="inputCustomerName">Order From</label>
		<form:select path="customer.customerId" cssClass="resizedSelect"
			disabled="true">
			<form:option value="" label="--  Select  --" />
			<c:forEach items="${customerList}" var="customer">
				<form:option value="${customer.customerId}">${customer.name}</form:option>
			</c:forEach>
		</form:select>
		<form:hidden path="customer.customerId" />
		<form:errors path="customer.customerId" cssClass="error" />

	</div>

	<div class="form-group">
		<label for="inputReferredBy">Referred By</label>
		<form:input path="refSource" cssClass="form-control" title="refSource"
			autocomplete="off" />
		<form:errors path="refSource" cssClass="error" />
	</div>



	<div class="form-group">
		<label for="inputPatientName">Mobile No</label>
		<form:input path="mobileNo" cssClass="form-control" title="mobileNo" />
		<form:errors path="mobileNo" cssClass="error" />

	</div>

	<div class="form-group">
		<form:input path="orderDate" placeholder="Order Date"
			cssClass="form-control" />
	</div>
	<div class="form-group">
		<form:input path="deliveryDate" placeholder="Delivery Date"
			cssClass="form-control" />
	</div>



	<button type="submit" class="btn btn-primary">Add Order</button>
	<c:url value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_ORDERS%>"
		var="listAllOrdersAction" />

	<button type="submit" formmethod="get" class="btn btn-default"
		formaction="${listAllOrdersAction}">Cancel</button>
</form:form>