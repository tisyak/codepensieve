 <%@page import="com.medsys.ui.util.UIActions"%>
 
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
 <head>
 <script src="<c:url value="/resources/js/datepicker.js"/>"></script>
	<link href="<c:url value="/resources/css/datepicker.css"/>" rel='stylesheet' >
 </head>
 
 <script>
$(document).ready(function() {
	 
	 $("#customerDSCInfo\\.customerInfo\\.customerId").change(function(){
			//alert("Customer select value changed.");
			if (ajaxCallForAvailableDSCsOfCustomer($("#customerDSCInfo\\.customerInfo\\.customerId"))) {
				return true;
			}else{
				return false;
			}
		});
	
});
 </script>
 
 <c:url value="<%=UIActions.FORWARD_SLASH + UIActions.ADD_DSC_TRANSFER_INFO%>" var="action"/>
 <form:form method="POST" action="${action}" modelAttribute="dscTransferInfo" autocomplete="off">
       
       <div class="form-group">
            <label for="inputCustomerId">Customer</label>
            	<form:select path="customerDSCInfo.customerInfo.customerId">
									<form:option value="" label="-  Select  -" />
									<c:forEach items="${customerInfos}" var="customer" varStatus="status">
										<form:option value="${customer.customerId}">${customer.name}</form:option>
									</c:forEach>
								</form:select>  
				<form:errors path="customerDSCInfo.customerInfo.customerId" cssClass="error" /> 
            
        </div>
        
         <div class="form-group">
            <label for="inputDscSerialNo">Digital Certificate </label>
            	<form:select path="customerDSCInfo.dscSerialNo">
									<form:option value="" label="-  Select  -" />
									
								</form:select>  
				<form:errors path="customerDSCInfo.dscSerialNo" cssClass="error" /> 
            
        </div>
        
         <div class="form-group">
            <label for="inputTpId">Third Party</label>
            	<form:select path="thirdPartyInfo.tpId">
									<form:option value="" label="-  Select  -" />
									<c:forEach items="${thirdPartyInfos}" var="thirdParty" varStatus="status">
										<form:option value="${thirdParty.tpId}">${thirdParty.name}</form:option>
									</c:forEach>
								</form:select>  
				<form:errors path="thirdPartyInfo.tpId" cssClass="error" /> 
            
        </div>
        
         <div class="form-group">
            <label for="inputTransferDate">Outward Date</label>
            	<form:input path="transferDate" cssClass="form-control" title="transferDate"/> 
				<form:errors path="transferDate" cssClass="error" /> 
            
        </div>
        
             
             
        <button type="submit" class="btn btn-primary">Outward DSC</button>
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
    
});
</script>