<%@page import="java.util.List"%>
<%@page import="com.medsys.orders.model.Invoice"%>
<%@page import="com.medsys.master.model.InvoiceStatusCode"%>
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.EDIT_INVOICE%>"
	var="editInvoice" />
<spring:url
	value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_DELETE_INVOICE%>"
	var="deleteInvoice" />



<script>
	$(function() {
		$("#invoiceDate").datepicker({
			dateFormat : "dd-M-yy"
		});
	});

</script>




<spring:url
	value="<%=UIActions.FORWARD_SLASH + UIActions.SEARCH_INVOICES%>"
	var="action" />
<form:form method="POST" action="${action}" modelAttribute="invoice"
	autocomplete="off" class="form-inline">
	<div class="form-group">
		<form:input path="invoiceNo" cssClass="form-control"
			title="invoiceNumber" placeholder="Invoice Number" autocomplete="off" />
		<form:errors path="invoiceNo" cssClass="error" />
	</div>


	<div class="form-group">
		<form:input path="customer.name" cssClass="form-control"
			title="customer" placeholder="Invoice To" autocomplete="off" />
		<form:errors path="customer" cssClass="error" />

	</div>

	<div class="form-group">
		<form:input path="invoiceDate" cssClass="form-control" title="invoiceDate"
			placeholder="Invoice Date" autocomplete="off" />
		<form:errors path="invoiceDate" cssClass="error" />

	</div>

	<%--   <div class="form-group">
            	<form:input path="deliveryDate" cssClass="form-control" title="deliveryDate" placeholder="Delivery Date" autocomplete="off" /> 
				<form:errors path="deliveryDate" cssClass="error" /> 
            
        </div> --%>

	<button type="submit" class="btn btn-primary">Search Invoice</button>
	</br>
	</br>
</form:form>


<%
	if (request.getAttribute("invoices") != null) {
%>



<div class="table-responsive">
	<table
		class="table table-condensed table-striped table-bordered table-hover no-margin">
		<thead>
			<tr>
				<th style="width: 20%">Invoice Number</th>
				<th style="width: 20%">Invoice To</th>
				<th style="width: 30%">Order Number</th>
				<th style="width: 10%">Invoice Date</th>
				<th style="width: 10%">Total Amount</th>
				<th style="width: 10%">Actions</th>
			</tr>
		</thead>
		<tbody>

			<%
				for (Invoice invoice : (List<Invoice>) request.getAttribute("invoices")) {
			%>
			<tr>
				<td><span class="name"><%=invoice.getInvoiceNo()%></span></td>
				<td class="hidden-phone"><%=invoice.getCustomer().getName()%></td>
				<td class="hidden-phone"><%=invoice.getOrder().getOrderNumber()%></td>
				<td class="hidden-phone"><%=invoice.getInvoiceDate()%></td>
				<td class="hidden-phone"><%=invoice.getTotalAmount()%></td>
				<td class="hidden-phone">
					<div class="btn-group">
						<button data-toggle="dropdown" class="btn btn-xs dropdown-toggle"
							data-original-title="" title="">
							Action <span class="caret"> </span>
						</button>


						<ul class="dropdown-menu pull-right">
							<%
								if (invoice.getInvoiceStatus().getInvoiceStatusCode().equals(InvoiceStatusCode.ACTIVE.getCode())) {
							%>
							<li><a href="${editInvoice}?invoiceId=<%=invoice.getInvoiceId()%>">Edit</a></li>
							<%
								}
							%>
							<%
								if (invoice.getInvoiceStatus().getInvoiceStatusCode().equals(InvoiceStatusCode.ACTIVE.getCode())
									|| invoice.getInvoiceStatus().getInvoiceStatusCode().equals(InvoiceStatusCode.ACTIVE.getCode())) {
							%>
							<li><a href="${deleteInvoice}?invoiceId=<%=invoice.getInvoiceId()%>">Delete</a></li>
							<%
								}
							%>
						</ul>
					</div>
				</td>
			</tr>

			<%
				}
			%>

		</tbody>
	</table>
</div>

<%
	}
%>
