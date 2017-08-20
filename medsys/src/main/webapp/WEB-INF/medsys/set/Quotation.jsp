<%@page import="com.medsys.product.model.Set"%>
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>


<!-- JQGrid Header Files -->


<link rel="stylesheet" type="text/css" media="screen"
	href='<c:url value="/resources/css/ui.jqgrid.min.css"/>' />

<script type='text/javascript'
	src='<c:url value="/resources/js/jqgrid/grid.locale-en.min.js"/>'></script>
<script type='text/javascript'
	src='<c:url value="/resources/js/jqgrid/jquery.jqgrid.min.js"/>'></script>


<!-- End of JQGrid Header Files -->

<script src="<c:url value="/resources/js/moment.min.js"/>"></script>


<c:url value="/${UIActions.GET_SET_REPORT}" var="downloadUrl" />
<c:url value="/${UIActions.GET_TOKEN}" var="downloadTokenUrl" />
<c:url value="/${UIActions.GET_PROGRESS}" var="downloadProgressUrl" />

<!--End of JQGrid Action URLs -->


<script>
		
	
	
    	function downloadQuotationPdf() {
			//alert($("#quotationPercentage").val());
    		download('pdf','quotation',$("#quotationPercentage").val());
    		
    		
    	}
    	
    	function download(type,challanKind,extraParam) {
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
    			var win = window.open('${downloadUrl}'+'?token='+token+'&type='+type+'&setId=0'
    						+'&challanKind='+challanKind+'&extraParam='+extraParam  , '_blank');
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



<form:form class="form-horizontal" autocomplete="off">

	
	<div class="form-group">
		<label class="col-sm-2" for="inputQuotationPercentage">Quotation Percentage</label>
		<div class="col-sm-5">
			<input id="quotationPercentage" class="form-control" title="quotationPercentage" />
			
		</div>

	</div>

		<button class="btn btn-primary"
			onclick="downloadQuotationPdf();">Get Quotation</button>
	 
		<div id='msgbox' title='' style='display: none'></div>
	

</form:form>