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
<!-- JQGrid Action URLs -->	
<c:url value="/${UIActions.LIST_ALL_PRODUCT_SET_TEMPLATE}" var="recordsUrl"/>
<c:url value="/${UIActions.ADD_PRODUCT_SET_TEMPLATE}" var="addUrl" />
<c:url value="/${UIActions.EDIT_PRODUCT_SET_TEMPLATE}" var="saveUrl"/>
<c:url value="/setPdtTemplate/update" var="editUrl"/>
<c:url value="/setPdtTemplate/delete" var="deleteUrl"/>

<c:url value="/${UIActions.GET_SET_REPORT}" var="downloadUrl"/>
<c:url value="/${UIActions.GET_TOKEN}" var="downloadTokenUrl"/>
<c:url value="/${UIActions.GET_PROGRESS}" var="downloadProgressUrl"/>

<!--End of JQGrid Action URLs -->	
<%
	Set set = (Set) request.getAttribute("set");

	pageContext.setAttribute("setId", set.getSetId());
%>

<script>
		
	var data = {};
	var headers = {};
	//data[csrfParameter] = csrfToken;
	//headers[csrfHeader] = csrfToken; 
	data["setId"] =${setId};
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
		   	colNames:['Id', 'Product Code', 'Group Name','Product Name', 'Qty','Actions'],
		   	colModel:[
		   		{name:'setPdtId',index:'id', width:25},
		   		{name:'product.productCode',index:'product.productCode', width:50, editable:"hidden"},
				{name:'product.group.groupName',index:'product.group.groupName', width:100},
		   		{name:'product.productDesc',index:'product.productDesc', width:200, editable:false,cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"' } },
		   		{name:'qty',index:'qty', width:50, editable:true, editrules:{required:true}, editoptions:{size:5}},
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
						extraparam:{setId:'${setId}'},
						//triggering reloadGrid after save
						afterSave: function () { $("#grid").setGridParam({datatype:'json',postData: data}).trigger('reloadGrid');},
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
			toppager: true,
		   	sortname: 'product.productCode',
		    viewrecords: true,
		    sortset: "asc",
		    caption:"Products in Set Template",
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
				groupSet: ['asc'],
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
		        id: "setPdtId"
		        
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
						setId: ${setId}
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
    			var win = window.open('${downloadUrl}'+'?token='+token+'&type='+type+'&setId='+${setId} , '_blank');
    			if (win) {
    			    //Browser has allowed it to be opened
    			    win.focus();
    			} else {
    			    //Browser has blocked it
    			    alert('Please allow popups for this Site');
    			}
    			// Check periodically if download has started
    			/* var frequency = 1000;
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
    			}, frequency); */
    			
    		});
    	}
</script>


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.SAVE_SET%>"
	var="action" />
<form:form class="form-horizontal" method="POST" action="${action}" modelAttribute="set"
	autocomplete="off">

	<form:hidden path="setId" cssClass="form-control" title="setId"
		autocomplete="off" />

	<div class="form-group">
		<label class="col-sm-2" for="inputSetName">Set Name</label>
		<div class="col-sm-5">
			<form:input path="setName" cssClass="form-control" title="setName" />
			<form:errors path="setName" cssClass="error" />
		</div>

	</div>

	<div class="form-group">
		<label class="col-sm-2" for="inputSetDesc">Set Desc</label>
		<div class="col-sm-5">
			<form:input path="setDesc" cssClass="form-control" title="setDesc" />
		</div>
		<form:errors path="setDesc" cssClass="error" />

	</div>
	
	<div>
	
	<c:url value="<%=UIActions.FORWARD_SLASH + UIActions.LIST_ALL_SETS%>"
		var="listAllSetAction" />

	<button type="submit"  formmethod="post" class="btn btn-primary"
		formaction="${action}">Save Set</button>
	<button type="submit"  formmethod="get" class="btn btn-default"
		formaction="${listAllSetAction}">Cancel</button>
		 <br />  <br />

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