
<%@page import="com.medsys.ui.util.UIActions"%>

<%@page import="com.medsys.ui.util.UIActions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="box-content">
	<!-- Monthwise Stats Div -->
	<div class="row">
		<div class="box col-md-12">
			<div class="box-inner">
				<div class="box-header well" data-original-title="">
					<h2>
						<i class="glyphicon glyphicon-signal"></i> This month statistics
					</h2>
					<div class="box-icon">
						<a href="#" class="btn btn-minimize btn-round btn-default"><i
							class="glyphicon glyphicon-chevron-up"></i></a>
					</div>
				</div>
				<div class="box-content">
					<div class="row">
						<div class="col-md-3 col-sm-3 col-xs-6">

							<!-- TODO:Change to Orders this month -->
							<c:url
								value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_ORDERS%>"
								var="listAllOrders" />
							<a data-toggle="tooltip" title="Orders Count"
								class="well top-block" href="${listAllOrders}"> <i
								class="glyphicon glyphicon-save  blue"></i>

								<div>Orders till date</div>
								<div><%=request.getAttribute("totalOrdersInMonth")%></div> <span
								class="notification"><%=request.getAttribute("totalOrdersInWeek")%></span>
							</a>
						</div>



						<div class="col-md-3 col-sm-3 col-xs-6">
							<!-- TODO:Change to Invoices this month -->
							<c:url
								value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_INVOICES%>"
								var="listAllInvoicesInMonth" />
							<a data-toggle="tooltip" title="Invoice Details (Amount & Count)"
								class="well top-block" href="${listAllInvoicesInMonth}"> <i
								class="glyphicon glyphicon-open" style="color: gold"></i>

								<div>Invoices raised</div>
								<div><%=request.getAttribute("totalInvoiceAmountInMonth")%></div>
								<span class="notification yellow"><%=request.getAttribute("totalInvoicesInMonth")%></span>

							</a>
						</div>

						<div class="col-md-3 col-sm-3 col-xs-6">
							<!-- TODO:Change to showing products in deficit -->
							<c:url
								value="<%=UIActions.FORWARD_SLASH + UIActions.MANAGE_PRODUCT_INVENTORY%>"
								var="listProductInventory" />
							<a data-toggle="tooltip" title="NO.of POs raised"
								class="well top-block" href="${listProductInventory}"> <i
								class="glyphicon glyphicon-thumbs-down" style="color: red"></i>

								<div>No.of products in deficit</div>
								<div><%=request.getAttribute("countOfProductsInDeficit")%></div>
								<span class="notification green"><%=request.getAttribute("countOfPOsInMonth")%></span>
							</a>
						</div>

						<div class="col-md-3 col-sm-3 col-xs-6">
							<!-- TODO:Change to Customer to be billed this month -->
							<c:url
								value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_CUSTOMERS%>"
								var="listAllCustomersBilledInMonth" />
							<a data-toggle="tooltip" title="No.of Customers billed"
								class="well top-block" href="${listAllCustomersBilledInMonth}">
								<i class="glyphicon glyphicon-user" style="color: palevioletred"></i>

								<div>No.of Customers billed</div>
								<div><%=request.getAttribute("countOfCustomersBilledInMonth")%></div>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- End of Monthwise Stats Div -->
	<!-- Yearwise Stats Div -->
	<div class="row">
		<div class="box col-md-12">
			<div class="box-inner" style="background-color: aliceblue">
				<div class="box-header well " data-original-title="">
					<h2>
						<i class="glyphicon  glyphicon-road"></i> Financial Year
						statistics
					</h2>
					<div class="box-icon">
						<a href="#" class="btn btn-minimize btn-round btn-default"><i
							class="glyphicon glyphicon-chevron-up"></i></a>
					</div>
				</div>
				<div class="box-content">
					<div class="row">
						<div class="col-md-3 col-sm-3 col-xs-6">

							<!-- TODO:Change to Orders this year -->
							<c:url
								value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_ORDERS%>"
								var="listAllOrdersThisYear" />
							<a data-toggle="tooltip" title="Orders Count"
								class="well top-block" href="${listAllOrdersThisYear}"> <i
								class="glyphicon glyphicon-save  blue"></i>

								<div>Orders till date</div>
								<div><%=request.getAttribute("orderCountForYear")%></div>
							</a>
						</div>



						<div class="col-md-3 col-sm-3 col-xs-6">
							<!-- TODO:Change to Invoices this year -->
							<c:url
								value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_INVOICES%>"
								var="listAllInvoicesThisYear" />
							<a data-toggle="tooltip" title="Invoice Details (Amount & Count)"
								class="well top-block" href="${listAllInvoicesThisYear}"> <i
								class="glyphicon glyphicon-open" style="color: gold"></i>

								<div>Invoices raised</div>
								<div><%=request.getAttribute("salesInYear")%></div> <span
								class="notification yellow"><%=request.getAttribute("invoiceCountForYear")%></span>

							</a>
						</div>

						<div class="col-md-3 col-sm-3 col-xs-6">
							<!-- TODO:Change to VAT THIS YEAR -->
							<c:url
								value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_INVOICES%>"
								var="listVatThisYear" />
							<a data-toggle="tooltip" title="NO.of POs raised"
								class="well top-block" href="${listVatThisYear}"> <i
								class="glyphicon glyphicon-exclamation-sign"
								style="color: limegreen"></i>

								<div>Sales Tax</div>
								<div><%=request.getAttribute("VATForYear")%></div>
							</a>
						</div>

						<div class="col-md-3 col-sm-3 col-xs-6">
							<!-- TODO:Change to Customer to be billed this year -->
							<c:url
								value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_CUSTOMERS%>"
								var="listAllCustomersBilledThisYear" />
							<a data-toggle="tooltip" title="No.of Customers billed"
								class="well top-block" href="${listAllCustomersBilledThisYear}">
								<i class="glyphicon glyphicon-user" style="color: palevioletred"></i>

								<div>No.of Customers billed</div>
								<div><%=request.getAttribute("countOfCustomersBilledThisYear")%></div>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- End Of Yearwise stats div -->


</div>

