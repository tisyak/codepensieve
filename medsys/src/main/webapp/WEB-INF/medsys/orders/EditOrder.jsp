<%@page import="com.medsys.orders.model.Orders"%>
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>

<script>
 $( function() {
    $( "#orderDate" ).datepicker({
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
<c:url value="/${UIActions.LIST_ALL_PRODUCT_ORDERS}" var="recordsUrl"/>
<c:url value="/${UIActions.EDIT_PRODUCT_ORDER}" var="saveUrl"/>
<c:url value="/orderproduct/update" var="editUrl"/>
<c:url value="/orderproduct/delete" var="deleteUrl"/>

<c:url value="/${UIActions.GET_ORDER_REPORT}" var="downloadUrl"/>
<c:url value="/${UIActions.GET_TOKEN}" var="downloadTokenUrl"/>
<c:url value="/${UIActions.GET_PROGRESS}" var="downloadProgressUrl"/>

<!--End of JQGrid Action URLs -->	
<%
	Orders order = (Orders)request.getAttribute("order");
	pageContext.setAttribute("setId",order.getSet().getSetId());
	pageContext.setAttribute("orderId",order.getOrderId());
%>
<script>
		
	var data = {};
	var headers = {};
	//data[csrfParameter] = csrfToken;
	data["setId"] = ${setId};
	data["orderId"] =${orderId};
	//headers[csrfHeader] = csrfToken; 
	
	//alert("data for ajax submit: " + data);
	
	$(function() {
		$("#grid").jqGrid({
		   	url:'${recordsUrl}',
			datatype: 'json',
			mtype: 'POST',
			postData: data,
		   	colNames:['orderProductSetId', 'setPdtId', 'Product Code', 'Group Name','Product Name', 'Qty','Adnl.Avl. Qty', 'Actions'],
		   	colModel:[
		   		{name:'orderProductSetId',index:'id',  hidden:true},
		   		{name:'setPdtId',index:'setPdtId',hidden:true},
		   		{name:'product.productCode',index:'product.productCode', width:50, editable:"hidden"},
				{name:'product.group.groupName',index:'product.group.groupName', width:100},
		   		{name:'product.productDesc',index:'product.productDesc', width:200, editable:false,cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"' } },
		   		{name:'qty',index:'qty', width:50, editable:true, editrules:{required:true}, editoptions:{size:5}, 
					cellattr: function(rowId, cellValue, rawObject, cm, item) {if(!(typeof(item)  === "undefined")){
							if((cellValue > item.availableQty)){return ' style="color:red;font-weight:bold;background-color:yellow;"' };
						}}},
		   		{name:'availableQty',index:'availableQty', width:50, editable:false},
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
		   	height: 300,
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
		    loadComplete: function() {},
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
		        id: "orderProductSetId"
		        
		    }
			
			
		});
		$("#grid").jqGrid('navGrid','#pager',
				{edit:false, add:false, del:false, search:true},
				{}, {}, {}, 
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
					extraparam:{orderId:'${orderId}'}
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
    			window.location = '${downloadUrl}'+'?token='+token+'&type='+type+'&orderId='+${orderId};
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


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.SAVE_ORDER%>"
	var="action" />
<form:form class="form-horizontal" method="POST" action="${action}" modelAttribute="order"
	autocomplete="off">

	<form:hidden path="orderId" title="orderId" />

	<div class="form-group">
		<label class="col-sm-2" for="inputCustomerName">Order From</label>
		<div class="col-sm-5">
	
		
		<form:select class="form-control" path="customer.customerId">	
		<form:option value="" label="--  Select  --" />
			<c:forEach items="${customerList}" var="customer">
				<form:option value="${customer.customerId}">${customer.name}</form:option>
			</c:forEach>
		</form:select>
		<form:errors path="customer.customerId" cssClass="error" />
		</div>
		
		<label class="col-sm-1" for="inputOrderNumber">Order No</label>
		<div class="col-sm-4">
		<form:input disabled="true" path="orderNumber" cssClass="form-control"
			title="orderNumber" />
			<form:hidden path="orderNumber" title="orderNumber" />
			</div>
	</div>	
	

	<div class="form-group">
		<label class="col-sm-2" for="inputReferredBy">Referred By</label>
		<div class="col-sm-5">
		<form:input path="refSource" cssClass="form-control" title="refSource"
			autocomplete="off" />
			</div>
		<form:errors path="refSource" cssClass="error" />
		<label class="col-sm-1" for="inputOrderDate">Order Dt</label>
		<div class="col-sm-4">
		<form:input path="orderDate" placeholder="Order Date"
			cssClass="form-control" />
			</div>
	</div>


	<div class="form-group">
	<label  class="col-sm-2" for="inputSetName">Order for Set</label>
	<div class="col-sm-5">
		<form:select disabled="true" class="form-control"  path="set.setId">	
		<form:option value="" label="--  Select Set --" />
			<c:forEach items="${setList}" var="setItem">
				<form:option value="${setItem.setId}">${setItem.setName}</form:option>
			</c:forEach>
		</form:select>
		</div>
		<form:hidden path="set.setId" />
		<form:errors path="set.setId" cssClass="error" />
		<label  class="col-sm-1"  for="inputDeliveryDate">Delivery Dt</label>
		<div class="col-sm-4">
		<form:input path="deliveryDate" placeholder="Delivery Date"
			cssClass="form-control" />
			</div>
	</div>
	
	<div class="form-group">
		<label class="col-sm-2" for="inputOrderNumber">Order Status</label>
		<div class="col-sm-5">
		<form:input disabled="true" path="orderStatus" cssClass="form-control"
			title="orderStatus" />
			<form:hidden path="orderStatus" title="orderStatus" />
			</div>
	</div>
	
	
	<div>
	
	<c:url value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_ORDERS%>"
		var="listAllOrdersAction" />

	<button type="submit"  formmethod="post" class="btn btn-primary"
		formaction="${action}">Save Order</button>
	<button type="submit"  formmethod="get" class="btn btn-default"
		formaction="${listAllOrdersAction}">Cancel</button>
		 <br />  <br />
    <input type="button" value="Edit Products in Order" onclick="startEdit()" />
    <input type="button" value="Save Products to Order" onclick="saveRows()" />

   <br />  <br />
	<!-- JQGrid HTML -->
	<div id='jqgrid'>
		<div id='pager'></div>
		<table id='grid'></table>
		
	</div>

	<div id='msgbox' title='' style='display: none'></div>

	<!-- End of JQGrid HTML -->
	</div>
	
	
		
		
</form:form>