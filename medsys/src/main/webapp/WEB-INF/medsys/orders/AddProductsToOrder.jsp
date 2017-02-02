<%@page import="com.medsys.orders.model.Orders"%>
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<!-- JQGrid Header Files -->

<link rel="stylesheet" type="text/css" media="screen"
	href='<c:url value="/resources/css/ui-lightness/jquery-ui.min.css"/>' />
<link rel="stylesheet" type="text/css" media="screen"
	href='<c:url value="/resources/css/ui.jqgrid.min.css"/>' />

<script type='text/javascript'
	src='<c:url value="/resources/js/jquery-ui.min.js"/>'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/jqgrid/grid.locale-en.min.js"/>'></script>
<script type='text/javascript'
	src='<c:url value="/resources/js/jqgrid/jquery.jqgrid.min.js"/>'></script>
<script type='text/javascript'
	src='<c:url value="/resources/js/jqgrid/order.product.grid.js"/>'></script>


<!-- End of JQGrid Header Files -->

<script src="<c:url value="/resources/js/moment.min.js"/>"></script>
<script src="<c:url value="/resources/js/datepicker.js"/>"></script>
<link href="<c:url value="/resources/css/datepicker.css"/>" rel='stylesheet'>
<!-- JQGrid Action URLs -->	
<c:url value="/setPdtTemplate/list" var="recordsUrl"/>
<c:url value="/orderproduct/create" var="addUrl"/>
<c:url value="/orderproduct/update" var="editUrl"/>
<c:url value="/orderproduct/delete" var="deleteUrl"/>
<!--End of JQGrid Action URLs -->	
<%
	Orders order = (Orders)request.getAttribute("order");
	pageContext.setAttribute("setId",order.getSet().getSetId());
	pageContext.setAttribute("orderId",order.getOrderId());
%>
<script>
	var dateErrMsg = 'Please enter a valid date';
	
	var data = {};
	var headers = {};
	//data[csrfParameter] = csrfToken;
	data["setId"] = ${setId};
	data["orderId"] =${orderId};
	//headers[csrfHeader] = csrfToken; 
	
	alert("data for ajax submit: " + data);
	
	$(function() {
		$("#grid").jqGrid({
		   	url:'${recordsUrl}',
			datatype: 'json',
			data: data,
			mtype: 'GET',
		   	colNames:['orderProductSetId', 'setPdtId', 'Product Code', 'Group Name','Product Name', 'Qty', 'Role'],
		   	colModel:[
		   		{name:'orderProductSetId',index:'id',  hidden:true},
		   		{name:'setPdtId',index:'setPdtId',hidden:true},
		   		{name:'product.productCode',index:'product.productCode', width:50, editable:true, editrules:{required:true}, editoptions:{size:20}},
				{name:'product.group.groupName',index:'product.group.groupName', width:100},
		   		{name:'product.productDesc',index:'product.productDesc', width:200, editable:true, editrules:{required:true}, editoptions:{size:250}},
		   		{name:'qty',index:'qty', width:50, editable:true, editrules:{required:true}, editoptions:{size:5}},				
		   		{name:'role',index:'role', width:50, editable:true, editrules:{required:true}, 
		   			edittype:"select", formatter:'select', stype: 'select', 
		   			editoptions:{value:"1:Admin;2:Regular"},
		   			formatoptions:{value:"1:Admin;2:Regular"}, 
		   			searchoptions: {sopt:['eq'], value:":;1:Admin;2:Regular"}}
		   	],
		   	postData: {},
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
		    // this enables client side sorting!! Hence,enabled as true
		    // previously this was false
		    loadonce: true,
		    loadComplete: function() {},
			grouping: true,
		   	groupingView : {
		   		groupField : ['product.group.groupName'],
		   		groupColumnShow : [true],
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
		        id: "setId"
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
				{ 	caption:"Add", 
					buttonicon:"ui-icon-plus", 
					onClickButton: addRow,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
		);
		
		$("#grid").navButtonAdd('#pager',
				{ 	caption:"Edit", 
					buttonicon:"ui-icon-pencil", 
					onClickButton: editRow,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
		);
		
		$("#grid").navButtonAdd('#pager',
			{ 	caption:"Delete", 
				buttonicon:"ui-icon-trash", 
				onClickButton: deleteRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
		);
		// Toolbar Search
		$("#grid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : true, defaultSearch:"cn"});
	});
	function addRow() {
   		$("#grid").jqGrid('setColProp', 'productCode', {editoptions:{readonly:false, size:20}});
   		$("#grid").jqGrid('setColProp', 'qty', {editrules:{required:true}});
   		
		// Get the currently selected row
		$('#grid').jqGrid('editGridRow','new',
	    		{ 	url: '${addUrl}', 
					editData: {},
	                serializeEditData: function(data){ 
	                    data.id = 0; 
	                    return $.param(data);
	                },
				    recreateForm: true,
				    beforeShowForm: function(form) {
			            $('#pData').hide();  
			            $('#nData').hide();
			            $('#qty',form).addClass('ui-widget-content').addClass('ui-corner-all');
				    },
					beforeInitData: function(form) {},
					closeAfterAdd: true,
					reloadAfterSubmit:true,
					afterSubmit : function(response, postdata) 
					{ 
				        var result = eval('(' + response.responseText + ')');
						var errors = "";
						
				        if (result.success == false) {
							for (var i = 0; i < result.message.length; i++) {
								errors +=  result.message[i] + "<br/>";
							}
				        }  else {
				        	$('#msgbox').text('Entry has been added successfully');
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
   		//$("#grid").jqGrid('setColProp', 'password', {hidden: true});
	} // end of addRow
	function editRow() {
		$("#grid").jqGrid('setColProp', 'productCode', {editoptions:{readonly:false, size:20}});
   		$("#grid").jqGrid('setColProp', 'qty', {editrules:{required:true}});
   		
		// Get the currently selected row
		var row = $('#grid').jqGrid('getGridParam','selrow');
		
		if( row != null ) {
		
			$('#grid').jqGrid('editGridRow', row,
				{	url: '${editUrl}', 
					editData: {},
			        recreateForm: true,
			        beforeShowForm: function(form) {
			            $('#pData').hide();  
			            $('#nData').hide();
			        },
					beforeInitData: function(form) {},
					closeAfterEdit: true,
					reloadAfterSubmit:true,
					afterSubmit : function(response, postdata) 
					{ 
			            var result = eval('(' + response.responseText + ')');
						var errors = "";
						
			            if (result.success == false) {
							for (var i = 0; i < result.message.length; i++) {
								errors +=  result.message[i] + "<br/>";
							}
			            }  else {
			            	$('#msgbox').text('Entry has been edited successfully');
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
		} else {
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
	
	
</script>


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.ADD_ORDER%>"
	var="action" />
<form:form method="POST" action="${action}" modelAttribute="order"
	autocomplete="off">

	<form:hidden path="orderId" cssClass="form-control" title="orderId"
		autocomplete="off" />
		
		<form:hidden path="set.setId" cssClass="form-control" title="set.setId"
		autocomplete="off" />
	<div>

	<!-- JQGrid HTML -->
	<div id='jqgrid'>
		<table id='grid'></table>
		<div id='pager'></div>
	</div>

	<div id='msgbox' title='' style='display: none'></div>

	<!-- End of JQGrid HTML -->
	</div>
	
	<button type="submit" class="btn btn-primary">Add Order</button>
	<c:url value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_ORDERS%>"
		var="listAllOrdersAction" />

	<button type="submit" formmethod="get" class="btn btn-default"
		formaction="${listAllOrdersAction}">Cancel</button>
</form:form>