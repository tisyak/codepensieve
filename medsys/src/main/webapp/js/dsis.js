function ajaxCallForAvailableDSCsOfClient(field) {
	//alert("Available DSCs Of Client Ajax call.");
	var fieldVal = field.val().trim();
	
	/*Parameters added for spring security csrf protection to work*/
	var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	var csrfToken = $("meta[name='_csrf']").attr("content");
	var csrfHeader = $("meta[name='_csrf_header']").attr("content");
	
	var data = {};
	var headers = {};
	data[csrfParameter] = csrfToken;
	data["clientId"] = fieldVal;
	headers[csrfHeader] = csrfToken; 
	$.ajax({
		type : "Post",
		url :ctx+ '/client/dsc/getAvailable',
		headers: headers, 
		data: data,
		dataType:"json",
        success: function(response) {
			//alert("response: "+ JSON.stringify(response));
            $("#clientDSCInfo\\.dscSerialNo").get(0).options.length = 0;
            $("#clientDSCInfo\\.dscSerialNo").get(0).options[0] = new Option("Select Certificate", "");   
						
		   $.each(response, function(index, item) {
			   
				$("#clientDSCInfo\\.dscSerialNo").get(0).options[$("#clientDSCInfo\\.dscSerialNo").get(0).options.length] = new Option(item.dscSerialNo, item.dscSerialNo);
            });
			
        },
        error: function() {
           // alert("Failed to load certificates");
        }
	});

	return true;

}
