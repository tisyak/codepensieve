<%@page import="com.medsys.product.model.ProductGroup"%>
<%@page import="com.medsys.ui.util.UIActions"%>
<%@page import="java.util.ArrayList"%>

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
<c:url value="/${UIActions.LIST_ALL_PRODUCT_GROUP}" var="recordsUrl" />
<c:url value="/${UIActions.ADD_PRODUCT_GROUP}" var="addUrl" />
<c:url value="/${UIActions.EDIT_PRODUCT_GROUP}" var="saveUrl" />
<c:url value="/${UIActions.DELETE_PRODUCT_GROUP}" var="deleteUrl" />

<!--End of JQGrid Action URLs -->
<%
	ProductGroup productGroup = (ProductGroup) request.getAttribute("productGroup");

%>
<script>
		
	var data = {};
	var headers = {};
	//data[csrfParameter] = csrfToken;
	
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
		   	colNames:['groupId','Name', 'Description', 'Actions'],
		   	colModel:[
		   		{name:'groupId',index:'id',  hidden:true},
		   		{name:'groupName',index:'groupName',width:50, editable: true},
		   		{name:'groupDesc',index:'groupDesc',width:50,editable: true},
				{
					name: 'Actions', index: 'Actions', width: 25,  editable: false, formatter: 'actions',
					formatoptions: {
							//Allow enter and esc to be used for save / cancel of inline edit. 
							//Currently,setting this to false
                            keys: false,
							// When the editformbutton parameter is set to true the form editing dialogue appear instead of in-line edit.
                            editformbutton:true,
							//if true will show edit icon, else will hide
							editbutton : true, 
							editOptions:{ 	url: '${saveUrl}',
											closeAfterEdit: true
							},
							
							//if true will show delete icon, else will hide
							delbutton : true,
							delOptions:{
								url: '${deleteUrl}'
								
							}
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
		   	sortname: 'name',
		    viewrecords: true,
		    sortorder: "asc",
		    caption:"Product Group List",
		    emptyrecords: "Empty records",
		    // Hence,enabled as true. This enables client side sorting!! 
		    loadonce: true,
			forceClientSorting: true,
			navOptions: { reloadGridOptions: { fromServer: true } },
			grouping: false,
		    jsonReader : {
		        root: "rows",
		        page: "page",
		        total: "total",
		        records: "records",
		        repeatitems: false,
		        cell: "cell",
		        id: "groupId"
		        
		    }
			
			
		});
		$("#grid").jqGrid('navGrid','#pager',
				{edit:false, add:true, del:false, search:true, cloneToTop: true },
				{}, 
				// options for the Add Dialog
                {
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
	
	});



</script>


<spring:url
	value="<%=UIActions.FORWARD_SLASH + UIActions.ADD_PRODUCT_GROUP%>"
	var="action" />
<form:form class="form-horizontal" method="POST" action="${action}"
	modelAttribute="productGroup" autocomplete="off">
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