<%@page import="com.medsys.orders.model.PurchaseOrder"%>
<%@page import="com.medsys.master.model.PurchaseOrderStatusCode"%>
<%@page import="com.medsys.ui.util.UIActions"%>
<%@page import="java.util.ArrayList"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>

<script>
$(function() {
	$("#purchaseOrderDate").datepicker({
		dateFormat : "dd-M-yy"
	});
	
	$("#receiveDate").datepicker({
		dateFormat : "dd-M-yy"
	});
});

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
<c:url value="/${UIActions.LIST_ALL_PRODUCT_PURCHASE_ORDER}"
	var="recordsUrl" />
<c:url value="/${UIActions.ADD_PRODUCT_PURCHASE_ORDER}" var="addUrl" />
<c:url value="/${UIActions.EDIT_PRODUCT_PURCHASE_ORDER}" var="saveUrl" />
<c:url value="/purchaseOrderproduct/update" var="editUrl" />
<c:url value="/${UIActions.DELETE_PRODUCT_PURCHASE_ORDER}"
	var="deleteUrl" />
<c:url value="/${UIActions.SEARCH_PRODUCT_GRP_BY_SET_URL}"
	var="getFilteredProductGroupsUrl" />
<c:url value="/${UIActions.SEARCH_PRODUCTS_BY_SET_GRP_URL}"
	var="getFilteredProductsUrl" />


<c:url value="/${UIActions.GET_PURCHASE_ORDER_EXCEL}" var="downloadUrl" />
<c:url value="/${UIActions.GET_TOKEN}" var="downloadTokenUrl" />
<c:url value="/${UIActions.GET_PROGRESS}" var="downloadProgressUrl" />

<!--End of JQGrid Action URLs -->
<%
	PurchaseOrder purchaseOrder = (PurchaseOrder) request.getAttribute("purchaseOrder");
	pageContext.setAttribute("setList", request.getAttribute("setList"));
	pageContext.setAttribute("pdtGroupList", request.getAttribute("pdtGroupList"));
	pageContext.setAttribute("pdtList", request.getAttribute("pdtList"));

	pageContext.setAttribute("purchaseOrderId", purchaseOrder.getPurchaseOrderId());
%>
<script>
		
	var data = {};
	var headers = {};
	//data[csrfParameter] = csrfToken;
	
	//headers[csrfHeader] = csrfToken; 
	
	data["purchaseOrderId"] =${purchaseOrderId};
	
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
		   	colNames:['productSetId','Set', 'Group ID','Product Id','Product Code', 'Group Name','Product Name', 'Qty', 'Actions'],
		   	colModel:[
		   		{name:'productSetId',index:'id',  hidden:true},
		   		{name:'setId',index:'setId',  hidden: true,edittype:"select",editable: true, editrules: { edithidden: true }, 
					editoptions: { 
						value: ${setList},
					  	dataEvents :[
							{ type: 'change', fn: function(e) {
								//alert("Calling filter products");
								var thisval = $(e.target).val();
								//alert($(e.target).attr("id"));
								$.get('${getFilteredProductGroupsUrl}?setId='+thisval, 
									function(data)
									{ 
										//alert("data: " + data);
										var res = $.parseJSON(data);
										//alert(res);
										s = "";
															 
										$.each(res, function(i, item) {
											 
											 s += '<option value="' + res[i].groupId  + '">' + res[i].groupName +
											   '</option>';
										})
										
										//s += "</select>";
										//alert(s);
										form = $(e.target).closest('form.FormGrid');
                                        $("#groupId", form[0]).html(s);
										//alert($("select#product\\.productId.FormElement", form[0]).html());
									}
								); // end get
								}//end func
							} // end type
						] // dataevents
            
            
					}
			},
	   		{name:'groupId',index:'groupId',  hidden: true,edittype:"select", editable: true, editrules: { edithidden: true }, 
				editoptions: { 
					value: ${pdtGroupList},
					dataEvents :[
							{ type: 'change', fn: function(e) {
								//alert("Calling filter products");
								var thisval = $(e.target).val();
								form = $(e.target).closest('form.FormGrid');
                                var chosenSetId=$("#setId", form[0]).val();
								//alert($(e.target).attr("id"));
								$.get('${getFilteredProductsUrl}?groupId='+thisval+'&setId='+chosenSetId, 
									function(data)
									{ 
										//alert("data: " + data);
										var res = $.parseJSON(data);
										//alert(res);
										s = "";
															 
										$.each(res, function(i, item) {
											 
											 s += '<option value="' + res[i].productId  + '">' + res[i].productCode + " - " + res[i].productDesc +
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
						dataUrl: '${getFilteredProductsUrl}',
						buildSelect: function(response){
                                            var data = $.parseJSON(eval(response));
                                            s = "<select>";
											 
											$.each(data, function(i, item) {
												 
												 s += '<option value="' + data[i].productId + '">' + data[i].productCode +
                                                   '</option>';
											})
                                            
                                            return s + "</select>";
                                        }
						}	
					
	   		},
				{name:'product.productCode',index:'product.productCode', width: 40 },
		   		{name:'product.group.groupName',index:'product.group.groupName', width:200},
		   		{name:'product.productDesc',index:'product.productDesc', width:125,cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"' } },
		   		{name:'qty',index:'qty', width:15, editable:true, editrules:{required:true}, editoptions:{size:5} ,align:'center'},
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
							extraparam:{purchaseOrderId:'${purchaseOrderId}'},
							//triggering reloadGrid after save
							afterSave: function () { $("#grid").setGridParam({datatype:'json',postData: data}).trigger('reloadGrid');},
							//if true will show delete icon, else will hide
							delbutton : true ,
							delOptions:{
								url: '${deleteUrl}'
								
							} 
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
		    jsonReader : {
		        root: "rows",
		        page: "page",
		        total: "total",
		        records: "records",
		        repeatitems: false,
		        cell: "cell",
		        id: "productSetId"
		        
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
                           purchaseOrderId: ${purchaseOrderId}
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
	

        function downloadXls() {
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
				var purchaseOrderId=${purchaseOrderId} + "";
    			var win = window.open('${downloadUrl}'+'?token='+token+'&purchaseOrderId='+purchaseOrderId, '_blank');
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
	value="<%=UIActions.FORWARD_SLASH + UIActions.SAVE_PURCHASE_ORDER%>"
	var="action" />
<form:form class="form-horizontal" method="POST" action="${action}"
	modelAttribute="purchaseOrder" autocomplete="off">


	<div class="form-group">
		<label class="col-sm-2" for="inputpurchaseOrderNumber">PurchaseOrder
			No</label>
		<div class="col-sm-5">
			<form:input path="purchaseOrderNumber" cssClass="form-control"
				title="purchaseOrderNumber" />
		</div>
		<label class="col-sm-1" for="inputPurchaseOrderId">PO ID</label>
		<div class="col-sm-4">
			<form:input disabled="true" path="purchaseOrderId"
				cssClass="form-control" title="purchaseOrderId" />
			<form:hidden path="purchaseOrderId" />
		</div>
	</div>


	<div class="form-group">

		<label class="col-sm-2" for="inputSetName">Purchase From</label>
		<div class="col-sm-5">


			<form:select cssClass="form-control" path="supplier.supplierId">
				<form:option value="" label="--  Select  --" />
				<c:forEach items="${supplierList}" var="supplier">
					<form:option value="${supplier.supplierId}">${supplier.name}</form:option>
				</c:forEach>
			</form:select>
			<form:errors path="supplier.supplierId" cssClass="error" />
		</div>

		<label class="col-sm-1" for="inputPurchaseOrderDate">PO Date</label>
		<div class="col-sm-4">
			<form:input path="purchaseOrderDate" cssClass="form-control" />
		</div>
	</div>


	<div class="form-group">

		<label class="col-sm-2" for="inputPurchaseOrderNumber">PurchaseOrder
			Status</label>
		<div class="col-sm-4">
			<form:input path="purchaseOrderStatus.purchaseOrderStatusDesc"
				cssClass="form-control" title="purchaseOrderStatus" disabled="true" />
			<form:hidden path="purchaseOrderStatus.purchaseOrderStatusId" />
		</div>

		<label class="col-sm-1" for="inputReceiveDate">Receive Date</label>
		<div class="col-sm-4">
			<form:input path="receiveDate" cssClass="form-control" />
		</div>
	</div>





	<div>

		<c:url
			value="<%=UIActions.FORWARD_SLASH + UIActions.SEARCH_PRODUCT_ORDERS%>"
			var="searchPurchaseOrderAction" />


		<%
			if (purchaseOrder.getPurchaseOrderStatus().getPurchaseOrderStatusCode()
						.equals(PurchaseOrderStatusCode.ACTIVE.getCode())) {
		%>
		<button type="submit" formmethod="post" class="btn btn-primary"
			formaction="${action}">Save PO</button>
		<button type="submit" formmethod="get" class="btn btn-default"
			formaction="${searchPurchaseOrderAction}">Cancel / Back</button>
		<%
			} else {
		%>
		<button type="submit" formmethod="get" class="btn btn-default"
			formaction="${searchPurchaseOrderAction}">Back</button>

		<%
			}
		%>


		<br /> <br />

		<!-- <input type="button" value="Edit Products in PurchaseOrder"
			onclick="startEdit()" /> <input type="button"
			value="Save Products to PurchaseOrder" onclick="saveRows()" /> <br /> <br />
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