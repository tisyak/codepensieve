<%@page import="com.medsys.ui.util.UIConstants"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%-- <c:if test="${sysErrorMsg!=null}">
	<h5>
		<span class="dispfailuremsg"><spring:message
				code="${sysErrorMsg}" arguments="${arg1},${arg2}" /></span>
	</h5>
</c:if>

<c:if test="${msgFailure!=null}">
	<h5>
		<span class="dispfailuremsg"> <spring:message
				code="${msgFailure}" arguments="${arg1},${arg2}" /></span>
	</h5>
</c:if>
<c:if test="${errorMsg!=null}">
	<h5>
		<span class="dispfailuremsg"> <spring:message
				code="${errorMsg}" arguments="${arg1},${arg2}" /></span>
	</h5>
</c:if>


<c:if
	test="${msgSuccess!=null && errorMsg==null && msgFailure==null && sysErrorMsg==null}">
	<h5>
		<span class="dispsuccessmsg"> <spring:message
				code="${msgSuccess}" arguments="${arg1},${arg2}" /></span>
	</h5>
</c:if>



<c:if test="${warnMsg!=null}">
	<h5>
		<span class="dispwarnmsg"> <spring:message code="${warnMsg}"
				arguments="${arg1},${arg2}" /></span>
	</h5>
</c:if>


<c:if test="${msg!=null}">
	<h5>
		<span class="dispsimplemsg"> <spring:message code="${msg}"
				arguments="${arg1},${arg2}" /></span>
	</h5>
</c:if>



<c:if test="${logoutMsg!=null}">
	<h5>
		<i><spring:message code="${logoutMsg}" /></i>
	</h5>
</c:if> --%>

<c:if test="${sysErrorMsg!=null}">
	<h5>
		<span class="dispfailuremsg">${sysErrorMsg}</span>
	</h5>
</c:if>

<c:if test="${msgFailure!=null}">
	<h5>
		<span class="dispfailuremsg">${msgFailure}</span>
	</h5>
</c:if>
<c:if test="${errorMsg!=null}">
	<h5>
		<span class="dispfailuremsg"> ${errorMsg}</span>
	</h5>
</c:if>


<c:if
	test="${msgSuccess!=null && errorMsg==null && msgFailure==null && sysErrorMsg==null}">
	<h5>
		<span class="dispsuccessmsg">${msgSuccess}/></span>
	</h5>
</c:if>



<c:if test="${warnMsg!=null}">
	<h5>
		<span class="dispwarnmsg">${warnMsg}</span>
	</h5>
</c:if>


<c:if test="${msg!=null}">
	<h5>
		<span class="dispsimplemsg">${msg}</span>
	</h5>
</c:if>



<c:if test="${logoutMsg!=null}">
	<h5>
		<i>${logoutMsg}</i>
	</h5>
</c:if>

