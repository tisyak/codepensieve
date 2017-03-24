<%@page import="com.medsys.orders.model.Invoice"%>
<%@page import="com.medsys.ui.util.UIActions"%>
<%@page import="java.util.ArrayList"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>

<script>
 $( function() {
    $( "#invoiceDate" ).datepicker({
		dateFormat: "dd-M-yy"
	});
  } );
  
</script>
<spring:url
	value="<%=UIActions.FORWARD_SLASH + UIActions.ADD_INVOICE%>"
	var="action" />
<form:form class="form-horizontal" method="POST" action="${action}"
	modelAttribute="invoice" autocomplete="off">

	<form:hidden path="invoiceId" title="invoiceId" />

	<div class="form-group">
		<label class="col-sm-2" for="inputOrderNumber">Invoice For
			Order</label>
		<div class="col-sm-5">


			<form:select class="form-control" path="order.orderId">
				<form:option value="" label="--  Select Order No --" />
				<c:forEach items="${orderList}" var="order">
					<form:option value="${order.orderId}">${order.orderNumber}</form:option>
				</c:forEach>
			</form:select>
			<form:errors path="order.orderId" cssClass="error" />
		</div>

		<label class="col-sm-1" for="inputInvoiceNo">Invoice No</label>
		<div class="col-sm-4">
			<form:input disabled="true" path="invoiceNo" cssClass="form-control"
				title="invoiceNo" />
			<form:hidden path="invoiceNo" title="invoiceNo" />
		</div>
	</div>


	<div class="form-group">

		<label class="col-sm-2" for="inputSetName">Invoice To</label>
		<div class="col-sm-5">


			<form:select class="form-control" path="customer.customerId">
				<form:option value="" label="--  Select  --" />
				<c:forEach items="${customerList}" var="customer">
					<form:option value="${customer.customerId}">${customer.name}</form:option>
				</c:forEach>
			</form:select>
			<form:errors path="customer.customerId" cssClass="error" />
		</div>

		<label class="col-sm-1" for="inputInvoiceDate">Invoice Dt</label>
		<div class="col-sm-4">
			<form:input path="invoiceDate" placeholder="Invoice Date"
				cssClass="form-control" />
		</div>
	</div>


	<div class="form-group">
		<label class="col-sm-2" for="inputReferredBy">Referred By</label>
		<div class="col-sm-5">
			<form:input path="refSource" cssClass="form-control"
				title="refSource" autocomplete="off" />
		</div>
		<form:errors path="refSource" cssClass="error" />
		<label class="col-sm-1" for="inputPaymentTerms">Payment Terms</label>
		<div class="col-sm-4">
			<form:select class="form-control"
				path="paymentTerms.paymentTermsId">
				<form:option value="" label="--  Select  --" />
				<c:forEach items="${paymentTermsList}" var="paymentTerms">
					<form:option value="${paymentTerms.paymentTermsId}">${paymentTerms.paymentTermsDesc}</form:option>
				</c:forEach>
			</form:select>
			<form:errors path="paymentTerms.paymentTermsId" cssClass="error" />
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2" for="inputPatientName">PatientName</label>
		<div class="col-sm-5">
			<form:input path="patientName" cssClass="form-control"
				title="patientName" autocomplete="off" />
		</div>
		<form:errors path="patientName" cssClass="error" />
		<label class="col-sm-1" for="inputInvoiceNumber">Invoice
			Status</label>
		<div class="col-sm-4">
			<form:input path="invoiceStatus.invoiceStatusDesc" cssClass="form-control"
				title="invoiceStatus" disabled="true"/>
			<form:hidden path="invoiceStatus.invoiceStatusId" title="invoiceStatus" />
		</div>
	</div>




	<div>

		<c:url
			value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_INVOICES%>"
			var="listAllInvoiceAction" />

		<button type="submit" formmethod="post" class="btn btn-primary"
			formaction="${action}">Save and Add Products to Invoice </button>
		<button type="submit" formmethod="get" class="btn btn-default"
			formaction="${listAllInvoiceAction}">Cancel</button>
	
	</div>
</form:form>