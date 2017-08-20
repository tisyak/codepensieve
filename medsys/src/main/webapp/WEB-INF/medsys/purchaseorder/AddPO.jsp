<%@page import="com.medsys.orders.model.PurchaseOrder"%>
<%@page import="com.medsys.ui.util.UIActions"%>
<%@page import="java.util.ArrayList"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>

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
	value="<%=UIActions.FORWARD_SLASH + UIActions.ADD_PURCHASE_ORDER%>"
	var="action" />
<form:form cssClass="form-horizontal" method="POST" action="${action}" modelAttribute="purchaseOrder">


	<div class="form-group">
		<label class="col-sm-2" for="inputpurchaseOrderNumber">PurchaseOrder No</label>
		<div class="col-sm-5">
			<form:input path="purchaseOrderNumber" cssClass="form-control"
				title="purchaseOrderNumber" />
		</div>
		<label class="col-sm-1" for="inputPurchaseOrderId">PO ID</label>
		<div class="col-sm-4">
			<form:input disabled="true" path="purchaseOrderId" cssClass="form-control"
				title="purchaseOrderId" />
			<form:hidden path="purchaseOrderId" />
		</div>
	</div>


	<div class="form-group">

		<label class="col-sm-2" for="inputSetName">Purchase From</label>
		<div class="col-sm-5">


			<form:select cssClass="form-control" path="supplier.supplierId">
				<form:option value="" label="--  Select  --" />
				<c:forEach items="${supplierList}" var="supplier">
					<form:option value="${supplier.supplierId}">${supplier.name}</form:option>
				</c:forEach>
			</form:select>
			<form:errors path="supplier.supplierId" cssClass="error" />
		</div>

		<label class="col-sm-1" for="inputPurchaseOrderDate">PO Date</label>
		<div class="col-sm-4">
			<form:input path="purchaseOrderDate" cssClass="form-control" />
		</div>
	</div>


<div class="form-group">

		<label class="col-sm-2" for="inputPurchaseOrderNumber">PurchaseOrder
			Status</label>
		<div class="col-sm-4">
			<form:input path="purchaseOrderStatus.purchaseOrderStatusDesc" cssClass="form-control"
				title="purchaseOrderStatus" disabled="true"/>
			<form:hidden path="purchaseOrderStatus.purchaseOrderStatusId"/>
		</div>

		<label class="col-sm-1" for="inputReceiveDate">Receive Date</label>
		<div class="col-sm-4">
			<form:input path="receiveDate" cssClass="form-control" />
		</div>
	</div>


	<div>

		<c:url
			value="<%=UIActions.FORWARD_SLASH + UIActions.SEARCH_PRODUCT_ORDERS%>"
			var="searchPurchaseOrderAction" />

		<button type="submit" formmethod="post" class="btn btn-primary"
			formaction="${action}">Save and Add Products to PO </button>
		<button type="submit" formmethod="get" class="btn btn-default"
			formaction="${searchPurchaseOrderAction}">Cancel / Back</button>
	
	</div>
</form:form>