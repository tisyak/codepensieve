<%@page import="java.util.List"%>
<%@page import="com.medsys.orders.model.PurchaseOrder"%>
<%@page import="com.medsys.master.model.PurchaseOrderStatusCode"%>
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.EDIT_PURCHASE_ORDER%>"
	var="editPurchaseOrder" />




<script>
	$(function() {
		$("#purchaseOrderDate").datepicker({
			dateFormat : "dd-M-yy"
		});
		
		$("#receiveDate").datepicker({
			dateFormat : "dd-M-yy"
		});
	});

</script>




<spring:url
	value="<%=UIActions.FORWARD_SLASH + UIActions.SEARCH_PURCHASE_ORDER%>"
	var="action" />
<form:form method="POST" action="${action}" modelAttribute="purchaseOrder"
	autocomplete="off" class="form-inline">
	<div class="form-group">
		<form:input path="purchaseOrderNumber" cssClass="form-control"
			title="purchaseOrderNumber" placeholder="PO Number" autocomplete="off" />
		<form:errors path="purchaseOrderNumber" cssClass="error" />
	</div>


	<div class="form-group">
		<form:input path="supplier.name" cssClass="form-control"
			title="supplier" placeholder="Purchase From" autocomplete="off" />
		<form:errors path="supplier" cssClass="error" />

	</div>

	<div class="form-group">
		<form:input path="purchaseOrderDate" cssClass="form-control" title="purchaseOrderDate"
			placeholder="PO Date" autocomplete="off" />
		<form:errors path="purchaseOrderDate" cssClass="error" />

	</div>

	<div class="form-group">
            	<form:input path="receiveDate" cssClass="form-control" title="receiveDate" placeholder="Receive Date" autocomplete="off" /> 
				<form:errors path="receiveDate" cssClass="error" /> 
            
        </div>

	<button type="submit" class="btn btn-primary">Search Purchase Order</button>
	</br>
	</br>
</form:form>


<%
	if (request.getAttribute("purchaseOrders") != null) {
%>



<div class="table-responsive">
	<table
		class="table table-condensed table-striped table-bordered table-hover no-margin">
		<thead>
			<tr>
				<th style="width: 20%">PurchaseOrder Number</th>
				<th style="width: 20%">Supplier</th>
				<th style="width: 30%">PurchaseOrder Date</th>
				<th style="width: 10%">Receive Date</th>
				<th style="width: 10%">PO Id</th>
				<th style="width: 10%">Actions</th>
			</tr>
		</thead>
		<tbody>

			<%
				for (PurchaseOrder purchaseOrder : (List<PurchaseOrder>) request.getAttribute("purchaseOrders")) {
			%>
			<tr>
				<td><span class="name"><%=purchaseOrder.getPurchaseOrderNumber()%></span></td>
				<td class="hidden-phone"><%=purchaseOrder.getSupplier().getName()%></td>
				<td class="hidden-phone"><%=purchaseOrder.getPurchaseOrderDate()%></td>
				<td class="hidden-phone"><%=purchaseOrder.getReceiveDate()%></td>
				<td class="hidden-phone"><%=purchaseOrder.getPurchaseOrderId()%></td>
				<td class="hidden-phone">
					<div class="btn-group">
						<button data-toggle="dropdown" class="btn btn-xs dropdown-toggle"
							data-original-title="" title="">
							Action <span class="caret"> </span>
						</button>


						<ul class="dropdown-menu pull-right">
							<%
								if (purchaseOrder.getPurchaseOrderStatus().getPurchaseOrderStatusCode().equals(PurchaseOrderStatusCode.ACTIVE.getCode())) {
							%>
							<li><a href="${editPurchaseOrder}?purchaseOrderId=<%=purchaseOrder.getPurchaseOrderId()%>">Edit / View</a></li>
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
