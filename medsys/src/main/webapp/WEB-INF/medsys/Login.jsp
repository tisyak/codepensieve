<%@page import="com.medsys.ui.util.UIActions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    
    <div class="row">
        <div class="col-md-12 center login-header">
            <!--<h2>Welcome to DSC Inventory System Portal</h2>-->
        </div>
        <!--/span-->
    </div><!--/row-->

<div class="row">
        <div class="well col-md-5 center login-box">
            <div class="alert alert-info">
                Please login with your Username and Password.
            </div>
            <form:form method="post" action="login" modelAttribute="adminUser"
		autocomplete="off">
		<c:if test="${param.error != null && !(empty param.error)}">
				<div class="alert alert-danger">    
                    <spring:message
				code="${param.error}"/>
                </div>
                </c:if>
                <c:if test="${param.logout != null && !(empty param.logout)}">
                <div class="alert alert-success"> 
                <spring:message
				code="${param.logout}"/>
                </div>
                </c:if>
                <fieldset>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user red"></i></span>
                        <input class="form-control" placeholder="User ID" type="text" id="username" name="username"></input>
                    </div>
                    <div class="clearfix"></div><br>

                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock red"></i></span>
                        <input class="form-control" placeholder="Password" type="password" id="password" name="password"></input>
                    </div>
                    <div class="clearfix"></div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<!--	
                    <div class="input-prepend">
                        <label class="remember" for="remember"><input type="checkbox" id="remember"> Remember me</label>
                    </div>-->
                    <div class="clearfix"></div>

                    <p class="center col-md-5">
                        <button type="submit" class="btn btn-primary">Login</button>
                    </p>
                    
                    <p>
                      <spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.FORGOT_PASSWORD + UIActions.FORWARD_SLASH%>"
						var="forgotPassword" /> 
                               
                               <a href="${forgotPassword}"><span> Forgot Password? Click Here </span></a>
                    </p>
                
                
                
                
           
        </fieldset>
            </form:form>
        </div>
        <!--/span-->
    </div><!--/row-->
    
    

 
   
        
    