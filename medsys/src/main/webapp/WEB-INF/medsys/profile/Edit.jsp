 <%@page import="com.medsys.ui.util.UIActions"%>
 
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.SAVE_PROFILE%>"
	var="action" />
 <form:form method="POST" action="${action}" modelAttribute="adminuser" autocomplete="off">
   <div class="form-group">
            <label for="inputUsername">Username</label>
            	<c:out value="${adminuser.username}" />
            	<form:hidden path="username"/> 
        </div>
        <div class="form-group">
            <label for="inputName">Name</label>
            	<form:input path="name" cssClass="form-control" title="name" autocomplete="off" /> 
            	<form:hidden path="password" /> 
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
            <label for="inputCompanyName">Company/LLP Name</label>
            	<form:input path="companyName" cssClass="form-control" title="companyName" autocomplete="off" /> 
				<form:errors path="companyName" cssClass="error" /> 
            
        </div>
        
           
        <button type="submit" class="btn btn-primary">Update Profile</button>
           <c:url value="<%=UIActions.FORWARD_SLASH + UIActions.VIEW_PROFILE%>" var="viewProfile"/>
							
		<button type="submit" formmethod="get" class="btn btn-default"
											formaction="${viewProfile}">
		Cancel </button> 
    </form:form>