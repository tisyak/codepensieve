 <%@page import="com.medsys.ui.util.UIActions"%>
 
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.SAVE_CUSTOMER%>"
	var="action" />
 <form:form method="POST" action="${action}" modelAttribute="customerInfo" autocomplete="off">
   <div class="form-group">
            <label for="inputCustomerId">Customer ID</label>
            	<c:out value="${customerInfo.customerId}" />
            	<form:hidden path="customerId"/> 
        </div>
        <div class="form-group">
            <label for="inputName">Name</label>
            	<form:input path="name" cssClass="form-control" title="name" autocomplete="off" /> 
				<form:errors path="name" cssClass="error" /> 
            
        </div>
       <div class="form-group">
            <label for="inputEmail">Email</label>
            	<form:input path="email" cssClass="form-control" title="email" autocomplete="off" /> 
				<form:errors path="email" cssClass="error" /> 
            
        </div>
        
         <div class="form-group">
            <label for="inputMobileNo">Mobile No</label>
            	<form:input path="mobileNo" cssClass="form-control" title="mobileNo" autocomplete="off" /> 
				<form:errors path="mobileNo" cssClass="error" /> 
            
        </div>
        
         <div class="form-group">
            <label for="inputAddress">Address</label>
            	<form:textarea path="address" cssClass="form-control" title="address" autocomplete="off" /> 
				<form:errors path="address" cssClass="error" /> 
            
        </div>
        
         <div class="form-group">
            <label for="inputCity">City</label>
            	<form:input path="city" cssClass="form-control" title="city" autocomplete="off" /> 
				<form:errors path="city" cssClass="error" /> 
            
        </div>
        
         <div class="form-group">
            <label for="inputPincode">Pincode</label>
            	<form:input path="pincode" cssClass="form-control" title="pincode" autocomplete="off" /> 
				<form:errors path="pincode" cssClass="error" /> 
            
        </div>
        
         <div class="form-group">
            <label for="inputCompanyName">Company/LLP Name</label>
            	<form:input path="companyName" cssClass="form-control" title="companyName" autocomplete="off" /> 
				<form:errors path="companyName" cssClass="error" /> 
            
        </div>
        
         <div class="form-group">
            <label for="inputPanNo">PAN</label>
            	<form:input path="pan" cssClass="form-control" title="pan" autocomplete="off" /> 
				<form:errors path="pan" cssClass="error" /> 
            
        </div>
      
        <button type="submit" class="btn btn-primary">Update Customer Info</button>
           <c:url value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_CUSTOMERS%>" var="listAllCustomersAction"/>
							
		<button type="submit" formmethod="get" class="btn btn-default"
											formaction="${listAllCustomersAction}">
		Cancel </button> 
    </form:form>