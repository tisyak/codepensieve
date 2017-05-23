<%@page import="java.util.List"%>
<%@page import="com.medsys.orders.model.SalesTax"%>
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<c:url value="/${UIActions.VIEW_SALES_TAX}" var="downloadUrl" />
<c:url value="/${UIActions.GET_TOKEN}" var="downloadTokenUrl" />
<c:url value="/${UIActions.GET_PROGRESS}" var="downloadProgressUrl" />


<script>
	$(function() {
		$("#fromDate").datepicker({
			dateFormat : "dd-M-yy"
		});
		
		$("#toDate").datepicker({
			dateFormat : "dd-M-yy"
		});

	});
	
	function downloadPdf() {download('pdf');}
	
	function download(type) {
		// Retrieve download token
		// When token is received, proceed with download
		$.get('${downloadTokenUrl}', function(response) {
			// Store token
			var token = response.message[0];
			
			// Show progress dialog
			$('#msgbox').text('Processing download...');
			$('#msgbox').dialog( 
					{	title: 'Download',
						modal: true,
						buttons: {"Close": function()  {
							$(this).dialog("close");} 
						}
					});
			
			// Start download
			var fromDate=$('#fromDate').val(); + "";
			var toDate=$('#toDate').val(); + "";
			var win = window.open('${downloadUrl}'+'?token='+token+'&fromDate='+fromDate+'&toDate='+toDate+'&type='+type, '_blank');
			if (win) {
			    //Browser has allowed it to be opened
			    win.focus();
			} else {
			    //Browser has blocked it
			    alert('Please allow popups for this Site');
			}
			// Check periodically if download has started
			var frequency = 1000;
			var timer = setInterval(function() {
				$.get('${downloadProgressUrl}', {token: token}, 
						function(response) {
							// If token is not returned, download has started
							// Close progress dialog if started
							if (response.message[0] != token) {
								$('#msgbox').dialog('close');
								clearInterval(timer);
							}
					});
			}, frequency);
			
		});
	}
</script>




<spring:url
	value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_REQUEST_FOR_SALES_TAX_REPORT%>"
	var="action" />
<form:form method="POST" action="${action}" modelAttribute="salesTax"
	autocomplete="off" class="form-inline">

	<div class="form-group">
		<form:input path="fromDate" cssClass="form-control" title="fromDate"
			placeholder="From Date" autocomplete="off" />
		<form:errors path="fromDate" cssClass="error" />

	</div>

	<div class="form-group">
		<form:input path="toDate" cssClass="form-control" title="toDate"
			placeholder="To Date" autocomplete="off" />
		<form:errors path="toDate" cssClass="error" />

	</div>

	<input id="GetSalesTaxReport" type="button" value="Get Sales Tax Report" onclick="downloadPdf();"/>
</form:form>

