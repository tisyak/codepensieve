<%@page import="com.medsys.ui.util.UIConstants"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<%@page import="com.medsys.ui.util.MedsysUITiles"%>
<%@page import="com.medsys.ui.util.UIActions"%>
<!-- left menu starts -->
<div class="col-sm-2 col-lg-2">
	<div class="sidebar-nav">
		<div class="nav-canvas">
			<div class="nav-sm nav nav-stacked"></div>
			<ul class="nav nav-pills nav-stacked main-menu">
				<li class="nav-header">Main</li>
				<li><spring:url
						value="<%=UIActions.FORWARD_SLASH + UIActions.HOME%>" var="home" />
					<a class="ajax-link" href="${home}"><i
						class="glyphicon glyphicon-home"></i><span> Home</span></a></li>
				<sec:authorize access="hasRole('ROLE_MASTER_ADMIN')">
					<li class="nav-header hidden-md">Sales Section</li>
					<li class="accordion"><a href="#"><i
							class="glyphicon glyphicon-plus"></i><span> Orders &amp;
								Invoices</span></a>
						<ul class="nav nav-pills nav-stacked">

							<li><spring:url
									value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_SEARCH_ORDERS%>"
									var="searchOrders" /> <a href="${searchOrders}"><i
									class="glyphicon  glyphicon-search"></i><span> Search
										Orders</span></a></li>
							<li><spring:url
									value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_ADD_ORDER%>"
									var="addOrder" /> <a href="${addOrder}"><i
									class="glyphicon glyphicon-plus-sign"></i><span> Create
										New Order </span></a></li>

							<li><spring:url
									value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_SEARCH_INVOICES%>"
									var="searchInvoices" /> <a href="${searchInvoices}"><i
									class="glyphicon  glyphicon-search"></i><span> Search
										Invoices</span></a></li>

							<li><spring:url
									value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_ADD_INVOICE%>"
									var="addInvoice" /> <a href="${addInvoice}"><i
									class="glyphicon glyphicon-plus-sign"></i><span>
										Generate Invoice </span></a></li>



						</ul></li>



					<li class="nav-header hidden-md">Product Management Section</li>
					<li><spring:url
							value="<%=UIActions.FORWARD_SLASH + UIActions.MANAGE_PRODUCT_INVENTORY%>"
							var="manageProductInv" /> <a href="${manageProductInv}"><i
							class="glyphicon  glyphicon-list"></i><span> Manage
								Product Inventory</span></a></li>

					<li class="accordion"><a href="#"><i
							class="glyphicon glyphicon-plus"></i><span> Set Management</span></a>

						<ul class="nav nav-pills nav-stacked">
							<li><spring:url
									value="<%=UIActions.FORWARD_SLASH + UIActions.MANAGE_PRODUCT_INVENTORY%>"
									var="manageProductInv" /> <a href="${manageProductInv}"><i
									class="glyphicon  glyphicon-list"></i><span> Manage Set</span></a></li>
							<li><spring:url
									value="<%=UIActions.FORWARD_SLASH + UIActions.MANAGE_PRODUCT_INVENTORY%>"
									var="manageProductInv" /> <a href="${manageProductInv}"><i
									class="glyphicon  glyphicon-list"></i><span> Manage
										Products in a Set</span></a></li>

						</ul></li>
					<li><spring:url
							value="<%=UIActions.FORWARD_SLASH + UIActions.MANAGE_PRODUCT_GROUP%>"
							var="manageProductGroup" /> <a href="${manageProductGroup}"><i
							class="glyphicon  glyphicon-list"></i><span> Manage
								Product Group</span></a></li>
					<li class="nav-header hidden-md">Master Records</li>
					<li><spring:url
							value="<%=UIActions.FORWARD_SLASH + UIActions.MANAGE_CUSTOMERS%>"
							var="manageCustomer" /> <a href="${manageCustomer}"><i
							class="glyphicon  glyphicon-list"></i><span> Manage
								Customers</span></a></li>
					<li>
					<li class="accordion"><a href="#"><i
							class="glyphicon glyphicon-plus"></i><span> Manage Other
								Masters</span></a>
						<ul class="nav nav-pills nav-stacked">
							<li><spring:url
									value="<%=UIActions.FORWARD_SLASH + UIActions.MANAGE_COMPANIES%>"
									var="manageCompany" /> <a href="${manageCompany}"><i
									class="glyphicon  glyphicon-list"></i><span> Company
										Details</span></a></li>
							<li>
							<li><spring:url
									value="<%=UIActions.FORWARD_SLASH + UIActions.MANAGE_CUSTOMERS%>"
									var="manageCustomer" /> <a href="${manageCustomer}"><i
									class="glyphicon  glyphicon-list"></i><span> Tax Master</span></a></li>
							<li>
						</ul></li>

					<li class="nav-header hidden-md">Reports</li>
					<li><spring:url
							value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_REQUEST_FOR_SALES_TAX_REPORT%>"
							var="viewSalesTax" /> <a href="${viewSalesTax}"><i
							class="glyphicon glyphicon-list"></i><span> Sales Tax</span></a></li>
					<li><spring:url
							value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_REQUEST_FOR_SALES_TAX_REPORT%>"
							var="productsInDeficit" /> <a href="${productsInDeficit}"><i
							class="glyphicon glyphicon-list"></i><span> Product
								Deficit Report</span></a></li>


				</sec:authorize>

				<li class="nav-header hidden-md">My Account Settings</li>
				<li><spring:url
						value="<%=UIActions.FORWARD_SLASH + UIActions.VIEW_PROFILE%>"
						var="viewProfile" /> <a href="${viewProfile}"><i
						class="glyphicon glyphicon-eye-open"></i><span> View
							Profile</span></a></li>
				<li><spring:url
						value="<%=UIActions.FORWARD_SLASH + UIActions.RESET_PASSWORD%>"
						var="resetPassword" /> <a href="${resetPassword}"><i
						class="glyphicon glyphicon-edit"></i><span> Reset Password</span></a></li>

			</ul>

		</div>
	</div>
</div>
<!--/span-->
<!-- left menu ends -->