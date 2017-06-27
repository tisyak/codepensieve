<%@page import="java.util.List"%>
<%@page import="com.medsys.product.model.Set"%>
<%@page import="com.medsys.ui.util.UIActions"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<spring:url value="<%=UIActions.FORWARD_SLASH + UIActions.EDIT_SET%>"
	var="editSet" />

<spring:url
	value="<%=UIActions.FORWARD_SLASH + UIActions.SEARCH_SETS%>"
	var="action" />
<form:form method="POST" action="${action}" modelAttribute="set"
	autocomplete="off" class="form-inline">
	<div class="form-group">
		<form:input path="setName" cssClass="form-control"
			title="setName" placeholder="Set Name" autocomplete="off" />
		<form:errors path="setName" cssClass="error" />
	</div>


	<div class="form-group">
		<form:input path="setDesc" cssClass="form-control"
			title="setDesc" placeholder="Set Desc" autocomplete="off" />
		<form:errors path="setDesc" cssClass="error" />

	</div>

	<button type="submit" class="btn btn-primary">Search Set</button>
	</br>
	</br>
</form:form>


<%
	if (request.getAttribute("setList") != null) {
%>



<div class="table-responsive">
	<table
		class="table table-condensed table-striped table-bseted table-hover no-margin">
		<thead>
			<tr>
				<th style="width: 30%">Set Name</th>
				<th style="width: 60%">Set Desc</th>
				<th style="width: 10%">Actions</th>
			</tr>
		</thead>
		<tbody>

			<%
				for (Set set : (List<Set>) request.getAttribute("setList")) {
			%>
			<tr>
				<td><span class="name"><%=set.getSetName()%></span></td>
				<td class="hidden-phone"><%=set.getSetDesc()%></td>
				<td class="hidden-phone">
					<div class="btn-group">
						<button data-toggle="dropdown" class="btn btn-xs dropdown-toggle"
							data-original-title="" title="">
							Action <span class="caret"> </span>
						</button>


						<ul class="dropdown-menu pull-right">

							<li><a href="${editSet}?setId=<%=set.getSetId()%>">Edit</a></li>

						</ul>
					</div>
				</td>
			</tr>

			<%
				}
			%>

		</tbody>
	</table>
</div>

<%
	}
%>
