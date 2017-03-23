<%@page import="com.medsys.orders.model.Invoice"%>
<%@page import="com.medsys.ui.util.UIActions"%>
<%@page import="java.util.ArrayList"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>

<script>
 $( function() {
    $( "#invoiceDate" ).datepicker({
		dateFormat: "dd-M-yy"
	});
  } );
  
   $( function() {
    $( "#deliveryDate" ).datepicker( {
		dateFormat: "dd-M-yy"
	});
  } );
</script>



<!-- JQGrid Header Files -->


<link rel="stylesheet" type="text/css" media="screen"
	href='<c:url value="/resources/css/ui.jqgrid.min.css"/>' />

<script type='text/javascript'
	src='<c:url value="/resources/js/jqgrid/grid.locale-en.min.js"/>'></script>
<script type='text/javascript'
	src='<c:url value="/resources/js/jqgrid/jquery.jqgrid.min.js"/>'></script>


<!-- End of JQGrid Header Files -->

<script src="<c:url value="/resources/js/moment.min.js"/>"></script>
<!-- JQGrid Action URLs -->
<c:url value="/${UIActions.LIST_ALL_PRODUCT_INVOICES}" var="recordsUrl" />
<c:url value="/${UIActions.ADD_PRODUCT_INVOICE}" var="addUrl" />
<c:url value="/${UIActions.EDIT_PRODUCT_INVOICE}" var="saveUrl" />
<c:url value="/invoiceproduct/update" var="editUrl" />
<c:url value="/${UIActions.DELETE_PRODUCT_INVOICE}" var="deleteUrl" />

<c:url value="/${UIActions.SEARCH_PRODUCTS_BY_SET_GRP_URL}" var="getFilteredProductsUrl" />


<c:url value="/${UIActions.GET_INVOICE_REPORT}" var="downloadUrl" />
<c:url value="/${UIActions.GET_TOKEN}" var="downloadTokenUrl" />
<c:url value="/${UIActions.GET_PROGRESS}" var="downloadProgressUrl" />

<!--End of JQGrid Action URLs -->
<%
	Invoice invoice = (Invoice) request.getAttribute("invoice");

	pageContext.setAttribute("vatTypeList", request.getAttribute("vatTypeList"));
	pageContext.setAttribute("setList", request.getAttribute("setList"));
	pageContext.setAttribute("pdtGroupList", request.getAttribute("pdtGroupList"));
%>
<script>
		
	var data = {};
	var headers = {};
	//data[csrfParameter] = csrfToken;
	
	var vatTypeList = ${vatTypeList};
	var setList = ${setList};
	var pdtGroupList = ${pdtGroupList};
	//headers[csrfHeader] = csrfToken; 
	
	//alert("data for ajax submit: " + data);
	
	$(function() {
		$("#grid").jqGrid({
		   	url:'${recordsUrl}',
			datatype: 'json',
			mtype: 'POST',
			postData: data,
		   	colNames:['invoiceProductId','Set', 'Group ID','Product Code', 'Group Name','Product Name', 'Rate per Unit','Qty','VAT','Total', 'Actions'],
		   	colModel:[
		   		{name:'invoiceProductSetId',index:'id',  hidden:true},
		   		{name:'setId',index:'setId',  hidden: true,edittype:"select",editable: true, editrules: { edithidden: true }, 
						editoptions: { 
							value: ${setList},
						  	dataEvents :[
								{ type: 'change', fn: function(e) {
									var thisval = $(e.target).val();
									$.get('${getFilteredProductsUrl}?setId='+thisval, 
										function(data)
										{ 
											var res = $(data).html();
											var id = $("#grid").jqGrid('getGridParam', 'selrow');
											var t = '#' + id + '_product.productCode';
											$(t).html(res);
										}
									); // end get
									}//end func
								} // end type
							] // dataevents
                
                
						}
				},
		   		{name:'groupId',index:'groupId',  hidden: true,edittype:"select", editable: true, editrules: { edithidden: true }, editoptions: { value: ${pdtGroupList}}},
		   		{name:'product.productCode',index:'product.productCode', width:50, editable: true, editrules: { edithidden: true },
		   			edittype:"select", editoptions: {
											dataUrl: '${getFilteredProductsUrl}',
											buildSelect: function(response){
                                                                var data = $.parseJSON(response);
                                                                s = "<select>";
																 
																$.each(data, function(i, item) {
																	 
																	 s += '<option value="' + data[i].productId + '">' + data[i].productCode +
                                                                       '</option>';
																})
                                                                
                                                                return s + "</select>";
                                                            }
											}	
		   		},
				{name:'product.group.groupName',index:'product.group.groupName', width:200},
		   		{name:'product.productDesc',index:'product.productDesc', width:125, editable: true, editrules: { edithidden: true }, cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"' } },
		   		{name:'ratePerUnit',index:'ratePerUnit', editable: true, editrules: { edithidden: true }, width:30},
		   		{name:'qty',index:'qty', width:15, editable:true, editrules:{required:true}, editoptions:{size:5} },
		   		{name:'VAT',index:'vatType', width:20,editable: true, editrules: { edithidden: true } ,editoptions: { value: vatTypeList}},
		   		{name:'Total',index:'totalPrice',editable: true, editrules: { edithidden: true }, width:30},
				{
					name: 'Actions', index: 'Actions', width: 25,  editable: false, formatter: 'actions',
					formatoptions: {
							//Allow enter and esc to be used for save / cancel of inline edit. 
							//Currently,setting this to false
                            keys: false,
							// When the editformbutton parameter is set to true the form editing dialogue appear instead of in-line edit.
                            editformbutton:false,
							//if true will show edit icon, else will hide
							editbutton : false, 
							//if true will show delete icon, else will hide
							delbutton : true 
                    }
				}
		   	],
			rowNum:-1,
		   	rowList:[],
		   	//height: auto,
		   	autowidth: true,
			rownumbers: true,
		    pager: '#pager',
		   	sortname: 'product.productCode',
		    viewrecords: true,
		    sortorder: "asc",
		    caption:"Products",
		    emptyrecords: "Empty records",
		    // Hence,enabled as true. This enables client side sorting!! 
		    loadonce: true,
			forceClientSorting: true,
			navOptions: { reloadGridOptions: { fromServer: true } },
			footerrow: true,
			
		    grouping: true,
		   	groupingView : {
		   		groupField : ['product.group.groupName'],
		   		groupColumnShow : [false],
		   		groupText : ['<b>{0}</b>'],
		   		groupCollapse : false,
				groupOrder: ['asc'],
				groupSummary : [true],
				groupDataSorted : true
		   	},
		  
		    jsonReader : {
		        root: "rows",
		        page: "page",
		        total: "total",
		        records: "records",
		        repeatitems: false,
		        cell: "cell",
		        id: "invoiceProductId"
		        
		    }
			
			
		});
		$("#grid").jqGrid('navGrid','#pager',
				{edit:false, add:true, del:false, search:true, cloneToTop: true },
				{}, 
				// options for the Add Dialog
                {
                    //closeAfterAdd: true,
					url:'${addUrl}', 
					width:500,
                    //recreateForm: true,
                    /*errorTextFormat: function (data) {
                        return 'Error: ' + data.responseText
                    }*/
                }, {}, 
				{ 	// search
					sopt:['cn', 'eq', 'ne', 'lt', 'gt', 'bw', 'ew'],
					closeOnEscape: true, 
					multipleSearch: true, 
					closeAfterSearch: true
				}
		);
		
		$("#grid").navButtonAdd('#pager',
				{ 	caption:"Pdf", 
					buttonicon:"ui-icon-arrowreturn-1-s", 
					onClickButton: downloadPdf,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
			);
		
		$("#grid").navButtonAdd('#pager',
				{ 	caption:"Excel", 
					buttonicon:"ui-icon-arrowreturn-1-s", 
					onClickButton: downloadXls,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
			);
	});


	
	
	
	
	function deleteRow(obj, args) {
		// Get the currently selected row
	    var row = $('#grid').jqGrid('getGridParam','selrow');
	    // A pop-up dialog will appear to confirm the selected action
		if( row != null ) 
			$('#grid').jqGrid( 'delGridRow', row,
	          	{	url:'${deleteUrl}', 
					recreateForm: true,
				    beforeShowForm: function(form) {
				    	//Change title
				        $(".delmsg").replaceWith('<span style="white-space: pre;">' +
				        		'Delete selected record?' + '</span>');
		            	//hide arrows
				        $('#pData').hide();  
				        $('#nData').hide();
				    },
	          		reloadAfterSubmit:true,
	          		closeAfterDelete: true,
	          		serializeDelData: function (postdata) {
		          	      var rowdata = $('#grid').getRowData(postdata.id);
		          	      // append postdata with any information 
		          	      return {id: postdata.id, oper: postdata.oper, username: rowdata.productCode};
		          	},
	          		afterSubmit : function(response, postdata) 
					{ 
			            var result = eval('(' + response.responseText + ')');
						var errors = "";
						
			            if (result.success == false) {
							for (var i = 0; i < result.message.length; i++) {
								errors +=  result.message[i] + "<br/>";
							}
			            }  else {
			            	$('#msgbox').text('Entry has been deleted successfully');
							$('#msgbox').dialog( 
									{	title: 'Success',
										modal: true,
										buttons: {"Ok": function()  {
											$(this).dialog("close");} 
										}
									});
		                }
				    	// only used for adding new records
				    	var newId = null;
			        	
						return [result.success, errors, newId];
					}
	          	});
		else {
			$('#msgbox').text('You must select a record first!');
			$('#msgbox').dialog( 
					{	title: 'Error',
						modal: true,
						buttons: {"Ok": function()  {
							$(this).dialog("close");} 
						}
					});
		}
	}
	
	function startEdit() {
            var grid = $("#grid");
            var ids = grid.jqGrid('getDataIDs');
			//Done in reverse manner so that focus remains on the first record for edit
            for (var i = (ids.length -1); i >=0; i--) {
               grid.editRow(ids[i]);
            }
	};

        function saveRows() {
			//alert("Save rows called");
            var grid = $("#grid");
            var ids = grid.jqGrid('getDataIDs');

            for (var i = 0; i < ids.length; i++) {
                grid.jqGrid('saveRow',ids[i], 
				{ 
					url: '${saveUrl}',
					extraparam:{invoiceId:'${invoiceId}'}
				});
            }
			grid.setGridParam({datatype:'json', page:1}).trigger('reloadGrid');
        }

        
        function downloadXls() {download('xls');}
    	
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
				var invoiceId=${invoiceId} + "";
    			window.location = '${downloadUrl}'+'?token='+token+'&invoiceId='+invoiceId+'&type='+type ;
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
	value="<%=UIActions.FORWARD_SLASH + UIActions.ADD_INVOICE%>"
	var="action" />
<form:form class="form-horizontal" method="POST" action="${action}"
	modelAttribute="invoice" autocomplete="off">

	<form:hidden path="invoiceId" title="invoiceId" />

	<div class="form-group">
		<label class="col-sm-2" for="inputOrderNumber">Invoice For
			Order</label>
		<div class="col-sm-5">


			<form:select class="form-control" path="order.orderId">
				<form:option value="" label="--  Select Order No --" />
				<c:forEach items="${orderList}" var="order">
					<form:option value="${order.orderId}">${order.orderNumber}</form:option>
				</c:forEach>
			</form:select>
			<form:errors path="order.orderId" cssClass="error" />
		</div>

		<label class="col-sm-1" for="inputInvoiceNo">Invoice No</label>
		<div class="col-sm-4">
			<form:input disabled="true" path="invoiceNo" cssClass="form-control"
				title="invoiceNo" />
			<form:hidden path="invoiceNo" title="invoiceNo" />
		</div>
	</div>


	<div class="form-group">

		<label class="col-sm-2" for="inputSetName">Invoice To</label>
		<div class="col-sm-5">


			<form:select class="form-control" path="customer.customerId">
				<form:option value="" label="--  Select  --" />
				<c:forEach items="${customerList}" var="customer">
					<form:option value="${customer.customerId}">${customer.name}</form:option>
				</c:forEach>
			</form:select>
			<form:errors path="customer.customerId" cssClass="error" />
		</div>

		<label class="col-sm-1" for="inputInvoiceDate">Invoice Dt</label>
		<div class="col-sm-4">
			<form:input path="invoiceDate" placeholder="Invoice Date"
				cssClass="form-control" />
		</div>
	</div>


	<div class="form-group">
		<label class="col-sm-2" for="inputReferredBy">Referred By</label>
		<div class="col-sm-5">
			<form:input path="refSource" cssClass="form-control"
				title="refSource" autocomplete="off" />
		</div>
		<form:errors path="refSource" cssClass="error" />
		<label class="col-sm-1" for="inputPaymentTerms">Payment Terms</label>
		<div class="col-sm-4">
			<form:select class="form-control"
				path="paymentTerms.paymentTermsId">
				<form:option value="" label="--  Select  --" />
				<c:forEach items="${paymentTermsList}" var="paymentTerms">
					<form:option value="${paymentTerms.paymentTermsId}">${paymentTerms.paymentTermsDesc}</form:option>
				</c:forEach>
			</form:select>
			<form:errors path="paymentTerms.paymentTermsId" cssClass="error" />
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2" for="inputPatientName">PatientName</label>
		<div class="col-sm-5">
			<form:input path="patientName" cssClass="form-control"
				title="patientName" autocomplete="off" />
		</div>
		<form:errors path="patientName" cssClass="error" />
		<label class="col-sm-1" for="inputInvoiceNumber">Invoice
			Status</label>
		<div class="col-sm-4">
			<form:input path="invoiceStatus.invoiceStatusDesc" cssClass="form-control"
				title="invoiceStatus" />
			<form:hidden path="invoiceStatus.invoiceStatusId" title="invoiceStatus" />
		</div>
	</div>




	<div>

		<c:url
			value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_INVOICES%>"
			var="listAllInvoiceAction" />

		<button type="submit" formmethod="post" class="btn btn-primary"
			formaction="${action}">Save Invoice</button>
		<button type="submit" formmethod="get" class="btn btn-default"
			formaction="${listAllInvoiceAction}">Cancel</button>
		<br /> <br /> <input type="button" value="Edit Products in Invoice"
			onclick="startEdit()" /> <input type="button"
			value="Save Products to Invoice" onclick="saveRows()" /> <br /> <br />
		<!-- JQGrid HTML -->
		<div id='jqgrid'>
			<div id='pager'></div>
			<table id='grid'></table>

		</div>

		<div id='msgbox' title='' style='display: none'></div>

		<!-- End of JQGrid HTML -->
	</div>
</form:form>