
<%@page import="com.medsys.ui.util.UIActions"%>

<%@page import="com.medsys.ui.util.UIActions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="box-header well" data-original-title="">
	<h2>
		<i class="glyphicon glyphicon-star-empty"></i>Home
	</h2>


</div>
<div class="box-content">
	<div class="alert alert-info">Welcome Admin!</div>
	<div class=" row">
		<div class="col-md-3 col-sm-3 col-xs-6">
			<c:url
				value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_INVOICES%>"
				var="listAllAvailableCustomerDSCs" />
			<a data-toggle="tooltip" title="" class="well top-block"
				href="${listAllAvailableCustomerDSCs}"> <i
				class="glyphicon glyphicon-user blue"></i>

				<div>Count of available DSCs</div>
				<div><%=request.getAttribute("totalDSCCount")%></div> <!-- <span class="notification"><%--<%=request.getAttribute("certExpiringCustomerCount") --%></span> -->
			</a>
		</div>



		<div class="col-md-3 col-sm-3 col-xs-6">
			<c:url
				value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_CUSTOMERS%>"
				var="listCurrentMonthInwardDSCs" />
			<a data-toggle="tooltip" title="" class="well top-block"
				href="${listCurrentMonthInwardDSCs}"> <i
				class="glyphicon glyphicon-save  yellow"></i>

				<div>Inward certificates this month</div>
				<div><%=request.getAttribute("monthlyInwardCertificatesCount")%></div>

			</a>
		</div>

		<div class="col-md-3 col-sm-3 col-xs-6">
			<c:url
				value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_ORDERS%>"
				var="listCurrentMonthOutwardDSCs" />
			<a data-toggle="tooltip" title="" class="well top-block"
				href="${listCurrentMonthOutwardDSCs}"> <i
				class="glyphicon glyphicon-open red"></i>

				<div>Outward certificates this month</div>
				<div><%=request.getAttribute("monthlyOutwardCertificatesCount")%></div>

			</a>
		</div>

		<div class="col-md-3 col-sm-3 col-xs-6">
			<c:url
				value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_THIRDPARTIES%>"
				var="listAllThirdParties" />
			<a data-toggle="tooltip" title="" class="well top-block"
				href="${listAllThirdParties}"> <i
				class="glyphicon glyphicon-star green"></i>

				<div>Third Parties</div>
				<div><%=request.getAttribute("thirdPartyCount")%></div>

			</a>
		</div>
	</div>
</div>