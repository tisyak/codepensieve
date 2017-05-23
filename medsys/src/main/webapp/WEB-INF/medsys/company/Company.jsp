<%@page import="com.medsys.company.model.Company"%>
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
<c:url value="/${UIActions.LIST_ALL_COMPANIES}" var="recordsUrl" />
<c:url value="/${UIActions.ADD_COMPANY}" var="addUrl" />
<c:url value="/${UIActions.EDIT_COMPANY}" var="saveUrl" />
<c:url value="/productinv/update" var="editUrl" />
<c:url value="/${UIActions.DELETE_COMPANY}" var="deleteUrl" />

<!--End of JQGrid Action URLs -->
<%
	Company company = (Company) request.getAttribute("company");

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
		   	colNames:['companyId','Name', 'Email','Contact No','Address', 'City','Pincode','Gumasta No','TIN No','CST No','FDA 20B Lc No','FDA 21B Lc No', 'Actions'],
		   	colModel:[
		   		{name:'companyId',index:'id',  hidden:true},
		   		{name:'name',index:'name',width:30, editable: true},
		   		{name:'email',index:'email',width:40,editable: true},
				{name:'mobileNo',index:'mobileNo', width:20 ,editable: true},
				//Used word wrap by using the cellattr attribute
				{name:'address',index:'address', width:50, editable: true, cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"' } },
				{name:'city',index:'city', width: 12 ,editable: true},
		   		{name:'pincode',index:'pincode', width:15,editable: true},
		   		{name:'shopEstLcNo',index:'shopEstLcNo', width:20,editable: true},
		   		{name:'vatTinNo',index:'vatTinNo', width:20,editable: true},
		   		{name:'cstNo',index:'cstNo', width:20,editable: true},
		   		{name:'fda20bLcNo',index:'fda20bLcNo', width:25,editable: true},
		   		{name:'fda21bLcNo',index:'fda21bLcNo', width:25,editable: true},
				{
					name: 'Actions', index: 'Actions', width: 15,  editable: false, formatter: 'actions',
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
							delbutton : false 
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
		    caption:"Company List",
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
		        id: "companyId"
		        
		    }
			
			
		});
		$("#grid").jqGrid('navGrid','#pager',
				{edit:false, add:false, del:false, search:true, cloneToTop: true },
				{}, {}, {}, 
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
	value="<%=UIActions.FORWARD_SLASH + UIActions.ADD_COMPANY%>"
	var="action" />
<form:form class="form-horizontal" method="POST" action="${action}"
	modelAttribute="company" autocomplete="off">
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