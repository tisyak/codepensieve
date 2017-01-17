 <%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.DELETE_CUSTOMER%>"
	var="action"/>
 <form:form method="POST" action="${action}" modelAttribute="customerInfo" autocomplete="off">
   <div class="form-group">
            <label for="inputCustomerId">Customer ID</label>
            	<c:out value="${customerInfo.customerId}" />
            	<form:hidden path="customerId"/> 
        </div>
        <div class="form-group">
            <label for="inputName">Name</label>
            	<c:out value="${customerInfo.name}" />
            
        </div>
      
      
        <button type="submit" class="btn btn-primary">Delete Customer Info</button>
        	&nbsp; &nbsp; 
        <c:url value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_CUSTOMERS%>" var="listAllCustomersAction"/>
							
		<button type="submit" formmethod="get" class="btn btn-default"
											formaction="${listAllCustomersAction}">
		Cancel </button> 
        
    </form:form>