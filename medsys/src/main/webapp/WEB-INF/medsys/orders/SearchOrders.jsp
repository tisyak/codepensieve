<%@page import="java.util.List"%>
<%@page import="com.medsys.customer.model.Customer"%>
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.EDIT_CUSTOMER%>"
	var="editCustomer" />
<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_DELETE_CUSTOMER%>"
	var="deleteCustomer" />
<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_ADD_CUSTOMER_DSC%>"
	var="addCertificates" />
	
	


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.SEARCH_CUSTOMER%>"
	var="action" />
 <form:form method="POST" action="${action}" modelAttribute="customerInfo" autocomplete="off">
        <div class="form-group">
            <label for="inputName">Name</label>
            	<form:input path="name" cssClass="form-control" title="name" autocomplete="off" /> 
				<form:errors path="name" cssClass="error" /> 
            
        </div>
      
        
         <div class="form-group">
            <label for="inputPanNo">PAN</label>
            	<form:input path="pan" cssClass="form-control" title="pan" autocomplete="off" /> 
				<form:errors path="pan" cssClass="error" /> 
            
        </div>
        
          <div class="form-group">
            <label for="inputMobileNo">Mobile No</label>
            	<form:input path="mobileNo" cssClass="form-control" title="mobileNo" autocomplete="off" /> 
				<form:errors path="mobileNo" cssClass="error" /> 
            
        </div>
      
        <button type="submit" class="btn btn-primary">Search Customer</button>
        <br>
        
    </form:form>


<%
	if(request.getAttribute("customerInfos")!=null){
%>



<div class="table-responsive">
	<table
		class="table table-condensed table-striped table-bordered table-hover no-margin">
		<thead>
			<tr>
				<th style="width: 5%">
					<!-- <input class="no-margin" type="checkbox"> -->
				</th>
				<th style="width: 25%">Name</th>
				<th style="width: 20%" class="hidden-phone">Email</th>
				<th style="width: 10%" class="hidden-phone">Mobile No</th>
				<th style="width: 15%" class="hidden-phone">PAN No</th>
				<th style="width: 15%" class="hidden-phone">Company/LLP Name</th>
				<th style="width: 10%" class="hidden-phone">Actions</th>
			</tr>
		</thead>
		<tbody>

			<%
				for (Customer customerInfo: (List<Customer>)request.getAttribute("customerInfos")) {
			%>
			<tr>
				<td><input class="no-margin" type="checkbox"></td>
				<td><span class="name"><%=customerInfo.getName()%></span></td>
				<td class="hidden-phone"><%=customerInfo.getEmail()%></td>
				<td class="hidden-phone"><%=customerInfo.getMobileNo()%></td>
				<td class="hidden-phone">
					<div class="btn-group">
						<button data-toggle="dropdown" class="btn btn-xs dropdown-toggle"
							data-original-title="" title="">
							Action <span class="caret"> </span>
						</button>


						<ul class="dropdown-menu pull-right">
							<li><a href="${editCustomer}?customerId=<%=customerInfo.getCustomerId()%>">Edit</a></li>
							<li><a href="${addCertificates}?customerId=<%=customerInfo.getCustomerId()%>">Add Certificate Info</a></li>
							<li><a href="${viewCertificates}?customerId=<%=customerInfo.getCustomerId()%>">View Certificates</a></li>
							<li><a href="${deleteCustomer}?customerId=<%=customerInfo.getCustomerId()%>">Delete</a></li>
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
