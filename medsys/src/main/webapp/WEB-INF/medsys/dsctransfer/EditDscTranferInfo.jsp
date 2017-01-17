 <%@page import="com.medsys.ui.util.UIActions"%>
 
 
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
 <head>
 <script src="<c:url value="/resources/js/datepicker.js"/>"></script>
	<link href="<c:url value="/resources/css/datepicker.css"/>" rel='stylesheet' >
 </head>
 
 <c:url value="<%=UIActions.FORWARD_SLASH + UIActions.SAVE_DSC_TRANSFER_INFO%>" var="action"/>
 <form:form method="POST" action="${action}" modelAttribute="dscTransferInfo" autocomplete="off">
     <div class="form-group">
            <label for="inputCustomerId">Customer ID</label>
            	<c:out value="${dscTransferInfo.customerDSCInfo.customerInfo.name}(${dscTransferInfo.customerDSCInfo.customerInfo.customerId})" />
            	<form:hidden path="customerDSCInfo.customerInfo.customerId"/> 
				<form:hidden path="transferId"/> 
        </div>
       <div class="form-group">
            <label for="inputDscSerialNo">Serial No</label>
            <c:out value="${dscTransferInfo.customerDSCInfo.dscSerialNo}" />
            	<form:hidden path="customerDSCInfo.dscSerialNo"/> 
        </div>
        
          <div class="form-group">
            <label for="inputTransferDate">Outward Date</label>
            	<form:input path="transferDate" cssClass="form-control" title="transferDate"/> 
				<form:errors path="transferDate" cssClass="error" /> 
            
        </div>
        
        
        <div class="form-group">
            <label for="inputDscReturnDate">Inward Date</label>
            	<form:input path="dscReturnDate" cssClass="form-control" title="dscReturnDate"/> 
				<form:errors path="dscReturnDate" cssClass="error" /> 
            
        </div>
        
        
          <div class="form-group">
            <label for="inputReturned">DSC Inward</label>
            <c:out value="${dscTransferInfo.returned}" />
            	<form:hidden path="returned"/> 
        </div>
        
         <div class="form-group">
            <label for="inputDscSerialNo">Current Third Party Holder</label>
            <c:if test="${dscTransferInfo.thirdPartyInfo.tpId != null}">
            <c:out value="${dscTransferInfo.thirdPartyInfo.name}(${dscTransferInfo.thirdPartyInfo.tpId})" />
            </c:if>
        </div>
                
        <button type="submit" class="btn btn-primary">Update DSC of Customer</button>
        <c:url value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_DSC_TRANSFER_INFOS%>" var="listAllDSCTransfersAction"/>
							
		<button type="submit" formmethod="get" class="btn btn-default"
											formaction="${listAllDSCTransfersAction}">
		Cancel </button> 
        
       
    </form:form>
    
       <script type="text/javascript">
$(function() {
    
$('input[name="transferDate"]').datepicker({
	altFormat: "mm/dd/yyyy" });
	
$('input[name="transferDate"]').on('changeDate', function(ev){
    $(this).datepicker('hide'); 
    $("#transferDate\\.errors").remove();	});
    
$('input[name="dscReturnDate"]').datepicker({
	altFormat: "mm/dd/yyyy" });
	
$('input[name="dscReturnDate"]').on('changeDate', function(ev){
    $(this).datepicker('hide'); 
    $("#dscReturnDate\\.errors").remove();	});
    
    
});
</script>