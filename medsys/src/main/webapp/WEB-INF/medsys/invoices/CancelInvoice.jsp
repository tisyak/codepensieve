
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>

<spring:url
	value="<%=UIActions.FORWARD_SLASH + UIActions.CANCEL_INVOICE%>"
	var="action" />
<form:form class="form-horizontal" method="POST" action="${action}"
	modelAttribute="invoice" autocomplete="off">

	<form:hidden path="invoiceId" cssClass="form-control" title="invoiceId"
		autocomplete="off" />

	<div class="form-group">
		<label class="col-sm-2" for="inputInvoiceTo">Invoice To</label>
		<div class="col-sm-5">
			<form:input disabled="true" path="customer.name"
				cssClass="form-control" title="customer.name" />

			<form:hidden path="customer.customerId" />
		</div>

		<label class="col-sm-1" for="inputInvoiceNumber">Invoice No</label>
		<div class="col-sm-4">
			<form:input disabled="true" path="invoiceNo" cssClass="form-control"
				title="invoiceNo" />
			<form:hidden path="invoiceNo" />
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2" for="inputInvoiceAmount">Invoice
			Amount</label>
		<div class="col-sm-5">
			<form:input disabled="true" path="totalAmount"
				cssClass="form-control" title="totalAmount" />

		</div>
		<label class="col-sm-1" for="inputInvoiceDate">Invoice Dt</label>
		<div class="col-sm-4">
			<form:input path="invoiceDate" disabled="true"
				placeholder="Invoice Date" cssClass="form-control" />
		</div>
	</div>


	<div class="form-group">
		<label class="col-sm-2" for="inputOrderNumber">Order No</label>
		<div class="col-sm-5">
			<form:input path="order.orderNumber" disabled="true"
				cssClass="form-control" title="order.orderNumber" autocomplete="off" />
		</div>
		<label class="col-sm-1" for="inputOrderDate">Order Dt</label>
		<div class="col-sm-4">
			<form:input path="order.orderDate" disabled="true"
				placeholder="Order Date" cssClass="form-control" />
		</div>
	</div>



	<div class="form-group">
		<label class="col-sm-2" for="inputInvoiceStatus">Invoice
			Status</label>
		<div class="col-sm-5">
			<form:input disabled="true" path="invoiceStatus.invoiceStatusDesc"
				cssClass="form-control" title="invoiceStatus" />
			<form:hidden path="invoiceStatus.invoiceStatusId" />
		</div>
	</div>


	<button type="submit" class="btn btn-primary">Confirm
		Cancellation</button>
	<c:url
		value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_SEARCH_INVOICES%>"
		var="searchAllInvoicesAction" />

	<button type="submit" formmethod="get" class="btn btn-default"
		formaction="${searchAllInvoicesAction}">Back</button>
</form:form>