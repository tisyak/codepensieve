<%@page import="java.util.List"%>
<%@page import="com.medsys.adminuser.model.AdminUser"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<%if(request.getAttribute("adminUsers")!=null){ %>

<ol> 
        <% for (AdminUser adminUser: (List<AdminUser>)request.getAttribute("adminUsers")) { %>
            <li> <%= adminUser %> </li>
        <% } %>
        </ol>
        <%} else {%>
        No Admin Users found in System.
        <%}%>