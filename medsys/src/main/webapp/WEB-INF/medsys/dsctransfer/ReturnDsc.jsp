
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url
	value="<%=UIActions.FORWARD_SLASH + UIActions.RETURN_DSC_TRANSFER%>"
	var="action" />
<form:form method="POST" action="${action}"
	modelAttribute="dscTransferInfo" autocomplete="off">
	<div class="form-group">
		<label for="inputCustomerId">Customer ID</label>
		<c:out value="${dscTransferInfo.customerDSCInfo.customerInfo.name} (${dscTransferInfo.customerDSCInfo.customerInfo.customerId} )" />
		<form:hidden path="customerDSCInfo.customerInfo.customerId" />
		<form:hidden path="transferId" />
	</div>
	<div class="form-group">
		<label for="inputDscSerialNo">Serial No</label>
		<c:out value="${dscTransferInfo.customerDSCInfo.dscSerialNo}" />
		<form:hidden path="customerDSCInfo.dscSerialNo" />
	</div>

	<div class="form-group">
		<label for="inputDscSerialNo">Current Third Party Holder</label>
		<c:if test="${dscTransferInfo.thirdPartyInfo.tpId != null}">
			<c:out value="${dscTransferInfo.thirdPartyInfo.name}" />
		</c:if>
	</div>

	<div class="form-group">
		<label for="inputTransferDate">Outward Date</label>
		<c:out value="${dscTransferInfo.transferDate}" />
		<form:errors path="transferDate" cssClass="error" />

	</div>



	<button type="submit" class="btn btn-primary">Inward Customer
		DSC </button>
        	&nbsp; &nbsp; 
        <c:url
		value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_DSC_TRANSFER_INFOS%>"
		var="listAllDSCTransfersAction" />

	<button type="submit" formmethod="get" class="btn btn-default"
		formaction="${listAllDSCTransfersAction}">Cancel</button>
</form:form>