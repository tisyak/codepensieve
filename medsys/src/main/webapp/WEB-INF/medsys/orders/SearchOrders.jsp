<%@page import="java.util.List"%>
<%@page import="com.medsys.orders.model.Orders"%>
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.EDIT_ORDER%>"
	var="editOrder" />
<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_DELETE_ORDER%>"
	var="deleteOrder" />
	<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_DELETE_ORDER%>"
	var="viewProducts" />
	
	
	


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.SEARCH_ORDERS%>"
	var="action" />
 <form:form method="POST" action="${action}" modelAttribute="order" autocomplete="off">
        <div class="form-group">
            <label for="inputName">Order Number</label>
            	<form:input path="orderNumber" cssClass="form-control" title="orderNumber" autocomplete="off" /> 
				<form:errors path="orderNumber" cssClass="error" /> 
            
        </div>
      
        
         <div class="form-group">
            <label for="inputCustomer">Customer</label>
            	<form:input path="customer.name" cssClass="form-control" title="customer" autocomplete="off" /> 
				<form:errors path="customer" cssClass="error" /> 
            
        </div>
        
          <div class="form-group">
            <label for="inputOrderDate">Order Date</label>
            	<form:input path="orderDate" cssClass="form-control" title="orderDate" autocomplete="off" /> 
				<form:errors path="orderDate" cssClass="error" /> 
            
        </div>
        
            <div class="form-group">
            <label for="inputDeliveryDate">Delivery Date</label>
            	<form:input path="deliveryDate" cssClass="form-control" title="deliveryDate" autocomplete="off" /> 
				<form:errors path="deliveryDate" cssClass="error" /> 
            
        </div>
      
        <button type="submit" class="btn btn-primary">Search Order</button>
        <br>
        
    </form:form>


<%
	if(request.getAttribute("orders")!=null){
%>



<div class="table-responsive">
	<table
		class="table table-condensed table-striped table-bordered table-hover no-margin">
		<thead>
			<tr>
				<th style="width: 5%">
					<!-- <input class="no-margin" type="checkbox"> -->
				</th>
				<th style="width: 25%">Order Number</th>
				<th style="width: 20%" class="hidden-phone">Customer</th>
				<th style="width: 10%" class="hidden-phone">Order Date</th>
				<th style="width: 15%" class="hidden-phone">Delivery Date</th>
				<th style="width: 10%" class="hidden-phone">Actions</th>
			</tr>
		</thead>
		<tbody>

			<%
				for (Orders order: (List<Orders>)request.getAttribute("orders")) {
			%>
			<tr>
				<td><input class="no-margin" type="checkbox"></td>
				<td><span class="name"><%=order.getOrderNumber()%></span></td>
				<td class="hidden-phone"><%=order.getCustomer().getName()%></td>
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
							<li><a href="${viewProducts}?orderId=<%=order.getOrderId()%>">View Products</a></li>
							<li><a href="${deleteOrder}?orderId=<%=order.getOrderId()%>">Delete</a></li>
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
