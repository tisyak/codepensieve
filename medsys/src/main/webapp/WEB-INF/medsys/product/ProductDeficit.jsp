<%@page import="java.util.List"%>
<%@page import="com.medsys.product.model.ProductDeficitSummary"%>
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<c:url value="/${UIActions.VIEW_PRODUCT_DEFICIT}" var="downloadUrl" />
<c:url value="/${UIActions.GET_TOKEN}" var="downloadTokenUrl" />
<c:url value="/${UIActions.GET_PROGRESS}" var="downloadProgressUrl" />


<script>
	
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
			
			
			var win = window.open('${downloadUrl}'+'?token='+token+'&type='+type, '_blank');
			if (win) {
			    //Browser has allowed it to be opened
			    win.focus();
			} else {
			    //Browser has blocked it
			    alert('Please allow popups for this Site');
			}
		
			
		});
	}
</script>




<spring:url
	value="<%=UIActions.FORWARD_SLASH + UIActions.LOAD_REQUEST_FOR_PRODUCT_DEFICIT_REPORT%>"
	var="action" />
<form:form method="POST" action="${action}" 
	autocomplete="off" class="form-inline">

	
	<input id="GetProductDeficitReport" type="button" value="Get Product Deficit Report" onclick="downloadPdf();"/>
</form:form>

