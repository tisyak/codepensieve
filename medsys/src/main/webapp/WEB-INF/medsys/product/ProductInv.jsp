<%@page import="com.medsys.product.model.ProductInv"%>
<%@page import="com.medsys.ui.util.UIActions"%>
<%@page import="java.util.ArrayList"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>

<script>
 $( function() {
    $( "#productInvDate" ).datepicker({
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
<c:url value="/${UIActions.LIST_ALL_PRODUCT_INVENTORY}" var="recordsUrl" />
<c:url value="/${UIActions.ADD_PRODUCT_INVENTORY}" var="addUrl" />
<c:url value="/${UIActions.EDIT_PRODUCT_INVENTORY}" var="saveUrl" />
<c:url value="/productinv/update" var="editUrl" />
<c:url value="/${UIActions.DELETE_PRODUCT_INVENTORY}" var="deleteUrl" />
<c:url value="/${UIActions.SEARCH_PRODUCT_GRP_BY_SET_URL}"
	var="getFilteredProductGroupsUrl" />
<c:url value="/${UIActions.SEARCH_PRODUCTS_BY_SET_GRP_URL}" var="getFilteredProductsUrl" />


<c:url value="/${UIActions.GET_PRODUCT_INVENTORY_REPORT}" var="downloadUrl" />
<c:url value="/${UIActions.GET_TOKEN}" var="downloadTokenUrl" />
<c:url value="/${UIActions.GET_PROGRESS}" var="downloadProgressUrl" />

<!--End of JQGrid Action URLs -->
<%
	ProductInv productInv = (ProductInv) request.getAttribute("productInv");

	pageContext.setAttribute("setList", request.getAttribute("setList"));
	pageContext.setAttribute("pdtGroupList", request.getAttribute("pdtGroupList"));

%>
<script>
		
	var data = {};
	var headers = {};
	//data[csrfParameter] = csrfToken;
	
	var setList = ${setList};
	var pdtGroupList = ${pdtGroupList};
	//headers[csrfHeader] = csrfToken; 
	
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
		   	colNames:['productInvProductId','Set', 'Group ID','Product Id','Product Code', 'Group Name','Product Name', 'Org Qty','Engd Qty','Sold Qty','Dscrd Qty','Avlbl Qty','ADD Qty','Remove Qty','Price','MRP', 'Actions'],
		   	colModel:[
		   		{name:'productInvId',index:'id',  hidden:true},
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
												 
												 s += '<option value="' + res[i].productId  + '">' + res[i].productCode +
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
				{name:'product.productId',index:'product.productId', width:20, hidden:true, editable: true, editrules: { edithidden: true }, 
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
				{name:'product.productCode',index:'product.productCode', width: 30 },
		   		{name:'product.group.groupName',index:'product.group.groupName', width:200},
		   		{name:'product.productDesc',index:'product.productDesc', width:125,cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"' } },
		   		{name:'orgQty',index:'orgQty', width:20 },
		   		{name:'engagedQty',index:'engagedQty', width:20, editable:false, editrules:{required:true}, editoptions:{size:5} },
		   		{name:'soldQty',index:'soldQty', width:20, editable:false, editrules:{required:true}, editoptions:{size:5} },
		   		{name:'discardedQty',index:'discardedQty', width:20, editable:false, editrules:{required:true}, editoptions:{size:5} },
		   		{name:'availableQty',index:'availableQty', width:20, editable:false, editrules:{required:true}, editoptions:{size:5} },
				{name:'qtyTobeAdded',index:'qtyTobeAdded',  width:20, editable:true, editrules:{edithidden: true}, editoptions:{size:5} },
				{name:'qtyTobeDiscarded',index:'qtyTobeDiscarded', width:20, editable:true, editrules:{edithidden: true}, editoptions:{size:5} },
		   		{name:'price',index:'price', editable: true, editrules: { edithidden: true }, width:30, formatter:'currency', formatoptions: {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, prefix: "Rs.", suffix:"", defaultValue: '0.00'},align:'right'},
				{name:'mrp',index:'mrp',editable: true,editrules: { edithidden: true }, width:30, formatter:'currency', formatoptions: {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, prefix: "Rs.", suffix:"", defaultValue: '0.00'},align:'right'},
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
							url: '${saveUrl}',
							//triggering reloadGrid after save
							afterSave: function () { $("#grid").setGridParam({datatype:'json',postData: data}).trigger('reloadGrid');},
							//if true will show delete icon, else will hide
							delbutton : true 
                    }
				}
		   	],
			rowNum:15,
		   	rowList:[],
		   	//height: auto,
		   	autowidth: true,
			rownumbers: true,
		    pager: '#pager',
			toppager: true,
		   	sortname: 'product.productCode',
		    viewrecords: true,
		    sortorder: "asc",
		    caption:"Product Inventory",
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
		        id: "productInvId"
		        
		    }
			
			
		});
		$("#grid").jqGrid('navGrid','#pager',
				{edit:false, add:true, del:false, search:true, cloneToTop: true },
				{}, 
				// options for the Add Dialog
                {
					beforeShowForm: function(form) { 
												//During edit hide the Set and Group Columns 
												//and disable the Product Code
												$("#tr_qtyTobeAdded", form).hide(); 
												$("#tr_qtyTobeDiscarded", form).hide(); 
												
											},
                    closeAfterAdd: true,
					url:'${addUrl}', 
					width:500,
                    recreateForm: true,
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
    			
    			
    			window.location = '${downloadUrl}'+'?token='+token+'&type='+type ;
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
	value="<%=UIActions.FORWARD_SLASH + UIActions.ADD_PRODUCT_INVENTORY%>"
	var="action" />
<form:form class="form-horizontal" method="POST" action="${action}"
	modelAttribute="productInv" autocomplete="off">

	<div>

		<br /> <br />
		<!-- JQGrid HTML -->
		<div id='jqgrid'>
			<div id='pager'></div>
			<table id='grid'></table>

		</div>

		<div id='msgbox' title='' style='display: none'></div>

		<!-- End of JQGrid HTML -->
	</div>
</form:form>