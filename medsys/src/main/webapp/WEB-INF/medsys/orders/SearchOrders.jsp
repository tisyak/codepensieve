<%@page import="com.medsys.master.model.OrderStatusCode"%>
<%@page import="java.util.List"%>
<%@page import="com.medsys.orders.model.Orders"%>
<%@page import="com.medsys.master.model.OrderStatusCode"%>
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.EDIT_ORDER%>"
	var="editOrder" />
<spring:url
	value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_DELETE_ORDER%>"
	var="deleteOrder" />
<spring:url
	value="<%=UIActions.FORWARD_SLASH + UIActions.ORDER_RESTORE_SET%>"
	var="orderRestoreSet" />



<script>
	$(function() {
		$("#orderDate").datepicker({
			dateFormat : "dd-M-yy"
		});
	});

	$(function() {
		$("#deliveryDate").datepicker({
			dateFormat : "dd-M-yy"
		});
	});
</script>




<spring:url
	value="<%=UIActions.FORWARD_SLASH + UIActions.SEARCH_ORDERS%>"
	var="action" />
<form:form method="POST" action="${action}" modelAttribute="order"
	autocomplete="off" class="form-inline">
	<div class="form-group">
		<form:input path="orderNumber" cssClass="form-control"
			title="orderNumber" placeholder="Order Number" autocomplete="off" />
		<form:errors path="orderNumber" cssClass="error" />
	</div>


	<div class="form-group">
		<form:input path="customer.name" cssClass="form-control"
			title="customer" placeholder="Order From" autocomplete="off" />
		<form:errors path="customer" cssClass="error" />

	</div>

	<div class="form-group">
		<form:input path="orderDate" cssClass="form-control" title="orderDate"
			placeholder="Order Date" autocomplete="off" />
		<form:errors path="orderDate" cssClass="error" />

	</div>

	<%--   <div class="form-group">
            	<form:input path="deliveryDate" cssClass="form-control" title="deliveryDate" placeholder="Delivery Date" autocomplete="off" /> 
				<form:errors path="deliveryDate" cssClass="error" /> 
            
        </div> --%>

	<button type="submit" class="btn btn-primary">Search Order</button>
	</br>
	</br>
</form:form>


<%
	if (request.getAttribute("ordersList") != null) {
%>



<div class="table-responsive">
	<table
		class="table table-condensed table-striped table-bordered table-hover no-margin">
		<thead>
			<tr>
				<th style="width: 20%">Order Number</th>
				<th style="width: 20%">Order From</th>
				<th style="width: 30%">Product Set</th>
				<th style="width: 10%">Order Date</th>
				<th style="width: 10%">Delivery Date</th>
				<th style="width: 10%">Actions</th>
			</tr>
		</thead>
		<tbody>

			<%
				for (Orders order : (List<Orders>) request.getAttribute("ordersList")) {
			%>
			<tr>
				<td><span class="name"><%=order.getOrderNumber()%></span></td>
				<td class="hidden-phone"><%=order.getCustomer().getName()%></td>
				<td class="hidden-phone"><%=order.getSet().getSetName()%></td>
				<td class="hidden-phone"><%=order.getOrderDate()%></td>
				<td class="hidden-phone"><%=order.getDeliveryDate()%></td>
				<td class="hidden-phone">
					<div class="btn-group">
						<button data-toggle="dropdown" class="btn btn-xs dropdown-toggle"
							data-original-title="" title="">
							Action <span class="caret"> </span>
						</button>


						<ul class="dropdown-menu pull-right">
							
							<li><a href="${editOrder}?orderId=<%=order.getOrderId()%>">Edit</a></li>
							
							<%
								if (order.getOrderStatus().getOrderStatusCode().equals(OrderStatusCode.ACTIVE.getCode())) {
							%>
							<li><a href="${deleteOrder}?orderId=<%=order.getOrderId()%>">Delete</a></li>
							<li><a href="${orderRestoreSet}?orderId=<%=order.getOrderId()%>">Restore Set</a></li>
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
