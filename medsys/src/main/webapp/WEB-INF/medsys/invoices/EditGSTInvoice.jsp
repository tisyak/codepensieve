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

<c:url value="/${UIActions.SEARCH_PRODUCTS_BY_SET_GRP_URL}"
	var="getFilteredProductsUrl" />


<c:url value="/${UIActions.GET_INVOICE_REPORT}" var="downloadUrl" />
<c:url value="/${UIActions.GET_TOKEN}" var="downloadTokenUrl" />
<c:url value="/${UIActions.GET_PROGRESS}" var="downloadProgressUrl" />

<!--End of JQGrid Action URLs -->
<%
	Invoice invoice = (Invoice) request.getAttribute("invoice");

	pageContext.setAttribute("cgstTypeList", request.getAttribute("cgstTypeList"));
	pageContext.setAttribute("sgstTypeList", request.getAttribute("sgstTypeList"));
	pageContext.setAttribute("pdtGroupList", request.getAttribute("pdtGroupList"));

	pageContext.setAttribute("invoiceId", invoice.getInvoiceId());
	pageContext.setAttribute("setId", invoice.getOrder().getSet().getSetId());
%>
<script>
		
	var data = {};
	var headers = {};
	//data[csrfParameter] = csrfToken;
	
	var vatTypeList = ${vatTypeList};
	var pdtGroupList = ${pdtGroupList};
	//headers[csrfHeader] = csrfToken; 
	
	data["invoiceId"] =${invoiceId};
	
	//alert("data for ajax submit: " + data);
	
	//force reloading from the server after Add/Edit operation of form editing 
	$.extend($.jgrid.edit, {
    beforeSubmit: function () {
        $("#grid").jqGrid("setGridParam", {datatype: "json"});
        return [true,"",""];
		}
	});
	
	$(function() {
		$("#grid").jqGrid({
		   	url:'${recordsUrl}',
			datatype: 'json',
			mtype: 'POST',
			postData: data,
		   	colNames:['invoiceProductId', 'Group ID','Product Id','Product Code', 'Group Name','Product Name', 'Rate per Unit','Qty','Taxable Value','Discount','CGST %','CGST Amount','SGST %','SGST Amount','Total', 'Actions'],
		   	colModel:[
		   		{name:'invoiceProductSetId',index:'id',  hidden:true},
		   			{name:'groupId',index:'groupId',  hidden: true,edittype:"select", editable: true, editrules: { edithidden: true }, 
					editoptions: { 
						value: ${pdtGroupList},
						dataEvents :[
								{ type: 'change', fn: function(e) {
									//alert("Calling filter products");
									var thisval = $(e.target).val();
									form = $(e.target).closest('form.FormGrid');
                                    var chosenSetId=${setId};
									//alert($(e.target).attr("id"));
									$.get('${getFilteredProductsUrl}?groupId='+thisval+'&setId='+chosenSetId, 
										function(data)
										{ 
											//alert("data: " + data);
											var res = $.parseJSON(data);
											//alert(res);
											s = "";
																 
											$.each(res, function(i, item) {
												 
												 s += '<option value="' + res[i].productId  + '">' + res[i].productCode + ' - '+ res[i].productDesc + 
												   '</option>';
											})
											
											//s += "</select>";
											//alert(s);
											form = $(e.target).closest('form.FormGrid');
                                            $("#product\\.productId", form[0]).html(s);
											//alert($("select#product\\.productId.FormElement", form[0]).html());
										}
									); // end get
									}//end func
								} // end type
							] // dataevents
					}},
				{name:'product.productId',index:'product.productId', hidden:true, width:50, editable: true, editrules: { edithidden: true },
		   			edittype:"select", editoptions: {
											dataUrl: '${getFilteredProductsUrl}?setId='+${setId},
											dataType: "json",
											buildSelect: function(response){
                                                                var data = $.parseJSON(eval(response));
																//alert(data);
                                                                s = "<select>";
																// s = "";
																$.each(data, function(i, item) {
																	 
																	 s += '<option value="' + data[i].productId + '">' + data[i].productCode + ' - '+ data[i].productDesc + 
                                                                       '</option>';
																})
                                                                s += "</select>";
																//alert(s);
                                                                return s;
                                                            }
											}	
		   		},
				{name:'product.productCode',index:'product.productCode', width: 40 },
		   		{name:'product.group.groupName',index:'product.group.groupName', width:200},
		   		{name:'product.productDesc',index:'product.productDesc', width:125,cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"' } },
		   		{name:'ratePerUnit',index:'ratePerUnit', editable: true, editrules: { edithidden: true }, width:30,align:'right'},
		   		{name:'qty',index:'qty', width:15, editable:true, editrules:{required:true}, editoptions:{size:5} ,align:'center'},
				{name:'totalBeforeTax',index:'totalBeforeTax', editrules: { edithidden: true }, width:30,align:'right'},
				{name:'discount',index:'discount', editable: true, editrules: { edithidden: true }, width:30,align:'right'},
		   		{name:'cgstType.taxId',index:'cgstType.taxId', width:15,formatter: 'select',edittype:"select",editable: true, editoptions: { value: ${cgstTypeList}},align:'right'},
		   		{name:'cgstAmount',index:'cgstAmount',width:20,editrules: { edithidden: true }, width:30, align:'right'},
		   		{name:'sgstType.taxId',index:'sgstType.taxId', width:15,formatter: 'select',edittype:"select",editable: true, editoptions: { value: ${sgstTypeList}},align:'right'},
				{name:'sgstAmount',index:'sgstAmount',width:20,editrules: { edithidden: true }, width:30, formatter:'currency',  
				/*formatter: function (cellValue, option, rowObject) {
								alert('value8: ' + rowObject.ratePerUnit + ' value9: ' + rowObject.qty + ' value10: ' + rowObject['vatType.taxId']);
								return parseFloat(rowObject.ratePerUnit, 10) * parseFloat(rowObject.qty, 10) * parseFloat(rowObject['vatType.taxId'], 10);
				},*/ formatoptions: {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, prefix: "Rs.", suffix:"", defaultValue: '0.00'},align:'right'},
				
		   		{name:'totalPrice',index:'totalPrice',editrules: { edithidden: true }, width:30, formatter:'currency', formatoptions: {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, prefix: "Rs.", suffix:"", defaultValue: '0.00'},align:'right'},
				{
					name: 'Actions', index: 'Actions', width: 25,  editable: false, formatter: 'actions',
					formatoptions: {
							//Allow enter and esc to be used for save / cancel of inline edit. 
							//Currently,setting this to false
                            keys: false,
							// When the editformbutton parameter is set to true the form editing dialogue appear instead of in-line edit.
                            editformbutton:false,
							//if true will show edit icon, else will hide
							editbutton : true, 
							//inline edit save url
							url: '${saveUrl}',
							extraparam:{invoiceId:'${invoiceId}'},
							//triggering reloadGrid after save
							afterSave: function () { $("#grid").setGridParam({datatype:'json',postData: data}).trigger('reloadGrid');},
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
		    toppager: true,
		   	sortname: 'product.productCode',
		    viewrecords: true,
		    sortorder: "asc",
		    caption:"Products",
		    emptyrecords: "Empty records",
		    // Hence,enabled as true. This enables client side sorting!! 
		    loadonce: true,
			forceClientSorting: true,
			navOptions: { reloadGridOptions: { fromServer: true } },
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
			footerrow: true,
			userDataOnFooter : false,
			loadComplete: function () {
				var $this = $(this),
					totalAmount = $this.jqGrid("getCol", "totalPrice", false, "sum"),
					cgstTaxSum = $this.jqGrid("getCol", "cgstAmount", false, "sum"),
					sgstTaxSum = $this.jqGrid("getCol", "sgstAmount", false, "sum"),
					totalDiscount = $this.jqGrid("getCol", "discount", false, "sum"),
					localData = $this.jqGrid("getGridParam", "data"),
					totalRows = localData.length,
					totalSum = 0,
					$footerRow = $(this.grid.sDiv).find("tr.footrow"),
                    $secondFooterRow = $(this.grid.sDiv).find("tr.myfootrow"),
                    $thirdFooterRow = $(this.grid.sDiv).find("tr.mythirdfootrow"),
					$fourthFooterRow = $(this.grid.sDiv).find("tr.myfourthfootrow"),
					i;
				
					if ($secondFooterRow.length === 0) {
                        // add second row of the footer if it's not exist
                        $secondFooterRow = $footerRow.clone();
                        $secondFooterRow.removeClass("footrow").addClass("myfootrow ui-widget-content");
                        $secondFooterRow.attr('id', 'secondFooterRow');
                        $secondFooterRow.children("td").each(function () {
                            this.style.width = ""; // remove width from inline CSS
                        });
                        $secondFooterRow.insertAfter($footerRow);
                    }      

                    if ($thirdFooterRow.length === 0) {
                        // add second row of the footer if it's not exist
                        $thirdFooterRow = $footerRow.clone();
                        $thirdFooterRow.removeClass("footrow").addClass("mythirdfootrow ui-widget-content");
                        $thirdFooterRow.attr('id', 'thirdFooterRow');
                        $thirdFooterRow.children("td").each(function () {
                            this.style.width = ""; // remove width from inline CSS
                        });
                        $thirdFooterRow.insertAfter($secondFooterRow);
                    } 
					
					if ($fourthFooterRow.length === 0) {
                        // add third row of the footer if it's not exist
                        $fourthFooterRow = $footerRow.clone();
                        $fourthFooterRow.removeClass("footrow").addClass("myfourthfootrow ui-widget-content");
                        $fourthFooterRow.attr('id', 'fourthFooterRow');
                        $fourthFooterRow.children("td").each(function () {
                            this.style.width = ""; // remove width from inline CSS
                        });
                        $fourthFooterRow.insertAfter($thirdFooterRow);
                    } 

                    //FIRST FOOTER ROW
                    $this.jqGrid("footerData", "set",  {"ratePerUnit": "Total CGST:", "cgstAmount": cgstTaxSum});
					//SECOND FOOTER ROW
                   $secondFooterRow.find(">td[aria-describedby=" + this.id + "_ratePerUnit]")
					.text("Total SGST:");
					$secondFooterRow.find(">td[aria-describedby=" + this.id + "_cgstAmount]")
						.text(sgstTaxSum);
                    //THIRD FOOTER ROW
                    $thirdFooterRow.find(">td[aria-describedby=" + this.id + "_ratePerUnit]")
					.text("Total Discount:");
					$thirdFooterRow.find(">td[aria-describedby=" + this.id + "_cgstAmount]")
						.text(totalDiscount);
                    //THIRD FOOTER ROW
                    $fourthFooterRow.find(">td[aria-describedby=" + this.id + "_ratePerUnit]")
					.text("Grand Total:");
					$fourthFooterRow.find(">td[aria-describedby=" + this.id + "_totalPrice]")
						.text(totalAmount);
						

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
                    closeAfterAdd: true,
					url:'${addUrl}', 
					editData: {
                           invoiceId: ${invoiceId}
                       },
					width:500,
                    recreateForm: true,
                    errorTextFormat: function (data) {
                        return 'Error: ' + data.responseText
                    }
                }, {}, 
				{ 	// search
					sopt:['cn', 'eq', 'ne', 'lt', 'gt', 'bw', 'ew'],
					closeOnEscape: true, 
					multipleSearch: true, 
					closeAfterSearch: true
				}
		);
		
		$("#grid").navButtonAdd('#pager',
				{ 	caption:"Org Pdf", 
					buttonicon:"ui-icon-arrowreturn-1-s", 
					onClickButton: downloadOrgPdf,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
			);
		$("#grid").navButtonAdd('#pager',
				{ 	caption:"Dup Pdf", 
					buttonicon:"ui-icon-arrowreturn-1-s", 
					onClickButton: downloadDupPdf,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
			);
		$("#grid").navButtonAdd('#pager',
				{ 	caption:"Trp Pdf", 
					buttonicon:"ui-icon-arrowreturn-1-s", 
					onClickButton: downloadTrpPdf,
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
		          	      return {id: postdata.id, oper: postdata.oper, username: rowdata.productId};
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
	
/*	function startEdit() {
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
			grid.setGridParam({datatype:'json',postData: data}).trigger('reloadGrid');
        }
*/
        
        function downloadXls() {download('xls');}
    	
    	function downloadOrgPdf() {download('pdf','original');}
    	function downloadDupPdf() {download('pdf','duplicate');}
    	function downloadTrpPdf() {download('pdf','triplicate');}
    	
    	function download(type,billVersion) {
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
    			var win = window.open('${downloadUrl}'+'?token='+token+'&invoiceId='+invoiceId+'&type='+type+'&billVersion='+billVersion , '_blank');
    			if (win) {
    			    //Browser has allowed it to be opened
    			    win.focus();
    			} else {
    			    //Browser has blocked it
    			    alert('Please allow popups for this Site');
    			}
    			// Check periodically if download has started
    			/*var frequency = 1000;
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
    			}, frequency);*/
    			
    		});
    	}
</script>


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.SAVE_INVOICE%>"
	var="action" />
<form:form class="form-horizontal" method="POST" action="${action}"
	modelAttribute="invoice" autocomplete="off">

	<form:hidden path="invoiceId" title="invoiceId" />

	<div class="form-group">
		<div class="col-sm-3">
		<label class="col-sm-3" for="inputOrderNumber">Order</label>
		<div class="col-sm-8">
			<form:input disabled="true" path="order.orderNumber" cssClass="form-control"
				title="order.orderNumber" />
		</div>
		</div>
		<div class="col-sm-4">
		
		<label class="col-sm-2" for="inputOrderSet">Set</label>
				<div class="col-sm-10">
			<form:input disabled="true" path="order.set.setName" cssClass="form-control"
				title="order.set.setName" />
		</div>	
		</div>	


		<label class="col-sm-1" for="inputInvoiceNo">Invoice No</label>
		<div class="col-sm-4">
			<form:input disabled="true" path="invoiceNo" cssClass="form-control"
				title="invoiceNo" />
			<form:hidden path="invoiceNo" title="invoiceNo" />
			<form:hidden path="invoiceId" title="invoiceId" />
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
			<form:select class="form-control" path="paymentTerms.paymentTermsId">
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
			<form:input path="invoiceStatus.invoiceStatusDesc"
				cssClass="form-control" title="invoiceStatus" disabled="true" />
			<form:hidden path="invoiceStatus.invoiceStatusId"
				title="invoiceStatus" />
		</div>
	</div>
	
	
	<div class="form-group">
	<label class="col-sm-2" for="inputPatientInfo">Patient Age / Gender</label>
		<div class="col-sm-5">
			<form:input path="patientInfo" cssClass="form-control"
				title="patientInfo" />
		</div>
		<form:errors path="patientInfo" cssClass="error" />
		<div class="col-sm-5">
		<div class="col-sm-1">
		<form:checkbox path="gstInvoice" title="gstInvoice" disabled="true"  /> 
		<form:hidden path="gstInvoice" title="gstInvoice" />
				</div>
				<label class="col-sm-3" for="gstInvoice">GST Invoice</label>
		<form:errors path="gstInvoice" cssClass="error" />	
		<div class="col-sm-1">
		<form:checkbox path="billToPatient" title="billToPatient"  /> 
				</div>
				<label class="col-sm-3" for="billToPatient">Bill to patient</label>
		<form:errors path="billToPatient" cssClass="error" />	
</div>		
	</div>




	<div>

		<c:url
			value="<%=UIActions.FORWARD_SLASH + UIActions.SEARCH_INVOICES%>"
			var="searchInvoiceAction" />

		<button type="submit" formmethod="post" class="btn btn-primary"
			formaction="${action}">Save Invoice</button>
		<button type="submit" formmethod="get" class="btn btn-default"
			formaction="${searchInvoiceAction}">Cancel</button>
		<br /> <br />

		<!-- <input type="button" value="Edit Products in Invoice"
			onclick="startEdit()" /> <input type="button"
			value="Save Products to Invoice" onclick="saveRows()" /> <br /> <br />
			-->

		<!-- JQGrid HTML -->
		<div id='jqgrid'>
			<div id='pager'></div>
			<table id='grid'></table>

		</div>

		<div id='msgbox' title='' style='display: none'></div>

		<!-- End of JQGrid HTML -->
	</div>
</form:form>