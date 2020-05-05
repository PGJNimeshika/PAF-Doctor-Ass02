$(document).ready(function() {
	$('#datepicker').datepicker({
        uiLibrary: 'bootstrap',
        format: 'yyyy-mm-dd'
    });
	
	$('#datepicker1').datepicker({
        uiLibrary: 'bootstrap',
        format: 'yyyy-mm-dd'
    });
	
	$('#datepicker2').datepicker({
        uiLibrary: 'bootstrap',
        format: 'yyyy-mm-dd'
    });
	
	
	let contextPath = 'http://localhost:8010/NimeshikaJAX/jax-rest';
	let doctor_id = '';
	let session_id = '';
	let schedule_data = '';

	$("#loginSubmit").click(function() {
		let username = $("#username").val();
		let password = $("#password").val();
		
		$.ajax(contextPath + '/doctor/login', {
			method: 'POST',
		    dataType: 'json', // type of response data
		    contentType: 'application/json',
		    data: "{\"username\": \""+username+"\",\"password\": \""+password+"\"}",
		    success: function (data) {   // success callback function
		    	if (data.sts === 1) {
		    		$("#loginAlert").html("Logged In - Session Id: " + data.session_id + " Doctor Id: " + data.doctor_id);
		    		doctor_id = data.doctor_id;
		    		session_id = data.session_id;
		    	} else {
		    		$("#loginAlert").html("Error: " + data.error);
		    	}
		    },
		    error: function (jqXhr, textStatus, errorMessage) { // error callback 
		        console.error(errorMessage);
		    }
		});
	});
	
	$("#viewDoctor").click(function() {
		$.ajax(contextPath + '/doctor/view', {
			method: 'POST',
		    dataType: 'json', // type of response data
		    contentType: 'application/json',
		    data: "{\"session_id\": \""+session_id+"\",\"doctor_id\": \""+doctor_id+"\"}",
		    success: function (data) {   // success callback function
		    	if (data.sts === 1) {
		    		console.error(data);
		    		$("#doctorUpdateAlert").html("Data Loaded");
		    		$("#doctorLicenceNo").val(data.doctor_license_no);
		    		$("#doctorName").val(data.doctor_name);
		    		$("#doctorEmail").val(data.doctor_email);
		    		$("#doctorPhone").val(data.doctor_phone);
		    		$("#doctorAddress").val(data.doctor_address);
		    		$("#doctorGender").val(data.doctor_gender);
		    		$("#doctorSpec").val(data.doctor_specialization);
		    		
		    	} else {
		    		$("#doctorUpdateAlert").html("Error: " + data.error);
		    	}
		    },
		    error: function (jqXhr, textStatus, errorMessage) { // error callback 
		    	console.error(errorMessage);
		    }
		});
	});
	
	$("#doctorUpdateBtn").click(function() {
		let doctor_license_no = $("#doctorLicenceNo").val();
		let doctor_name = $("#doctorName").val();
		let doctor_email = $("#doctorEmail").val();
		let doctor_phone = $("#doctorPhone").val();
		let doctor_address = $("#doctorAddress").val();
		let doctor_gender = $("#doctorGender").val();
		let doctor_specialization = $("#doctorSpec").val();
		
		
		$.ajax(contextPath + '/doctor/update', {
			method: 'PUT',
		    dataType: 'json', // type of response data
		    contentType: 'application/json',
		    data: "{\"session_id\": \""+session_id+"\", \"doctor_id\": \""+doctor_id+"\", \"doctor_name\": \""+doctor_name+"\", \"doctor_license_no\": \""+doctor_license_no+"\", \"doctor_address\": \""+doctor_address+"'\", \"doctor_phone\": \""+doctor_phone+"\", \"doctor_email\": \""+doctor_email+"\", \"doctor_gender\": \""+doctor_gender+"\", \"doctor_specialization\": \""+doctor_specialization+"\"}",
		    success: function (data) {   // success callback function
		    	if (data.sts === 1) {
		    		console.error(data);
		    		$("#doctorUpdateAlert").html("Doctor Details Updated!");
		    		    		
		    	} else {
		    		$("#doctorUpdateAlert").html("Error: " + data.error);
		    	}
		    },
		    error: function (jqXhr, textStatus, errorMessage) { // error callback 
		    	console.error(errorMessage);
		    }
		});
	});
	
	$("#doctorCreateSchedule").click(function() {
		let schedule_date = $("#datepicker").val();
		let time_slot = $("#timeSlot").val();
		
		
		$.ajax(contextPath + '/doctorSchedule/create', {
			method: 'POST',
		    dataType: 'json', // type of response data
		    contentType: 'application/json',
		    data: "{\"session_id\": \""+session_id+"\", \"doctor_id\": \""+doctor_id+"\", \"time_slot\": \""+time_slot+"\", \"schedule_date\": \""+schedule_date+"\"}",
		    success: function (data) {   // success callback function
		    	if (data.sts === 1) {
		    		console.error(data);
		    		$("#scheduleCreateAlert").html("Schedule Created");
		    		    		
		    	} else {
		    		$("#scheduleCreateAlert").html("Error: " + data.error);
		    	}
		    },
		    error: function (jqXhr, textStatus, errorMessage) { // error callback 
		    	console.error(errorMessage);
		    }
		});
	});
	
	$("#viewSchedules").click(function() {
		let schedule_date = $("#datepicker1").val();
		let htmlData = '';
		
		$.ajax(contextPath + '/doctorSchedule/view', {
			method: 'GET',
		    dataType: 'json', // type of response data
		    data: "session_id="+session_id+"&doctor_id="+doctor_id+"&schedule_date="+schedule_date,
		    success: function (data) {   // success callback function
		    	if (data.sts === 1) {
		    		console.error(data);
		    		schedule_data = data;
		    		$.each( data.schedule_list, function( key, value ) {
		    			htmlData += '<tr><td>'+value.schedule_id+'</td><td>'+value.schedule_date+'</td><td>'+value.time_slot+'</td><td><button type="button" class="btn btn-warning btn-sm click-row" data-schedule-id="'+value.schedule_id+'" data-time="'+value.time_slot+'" data-date="'+value.schedule_date+'">Edit</button></td><td><button type="button" class="btn btn-danger btn-sm delete-row" data-schedule-id="'+value.schedule_id+'">Delete</button></td></tr>';
		    			});
		    		$("#scheduleBody").empty();
		    		$("#scheduleBody").append(htmlData);
		    		
		    		$(".click-row").click(function() {
		    			$("#updschedule_id").val($(this).data("schedule-id"));
		    			$("#datepicker2").val($(this).data("date"));
		    			$("#updTimeSlot").val($(this).data("time"));
		    		});
		    		
		    		$(".delete-row").click(function() {
		    			let schedule_id = $(this).data("schedule-id");
		    			
		    			$.ajax(contextPath + '/doctorSchedule/delete', {
		    				method: 'DELETE',
		    			    dataType: 'json', // type of response data
		    			    contentType: 'application/json',
		    			    data: "{\"schedule_id\": \""+schedule_id+"\",\"session_id\": \""+session_id+"\", \"doctor_id\": \""+doctor_id+"\"}",
		    			    success: function (data) {   // success callback function
		    			    	if (data.sts === 1) {
		    			    		console.error(data);
		    			    		$("#scheduleUpdateAlert").html("Schedule Deleted");
		    			    		    		
		    			    	} else {
		    			    		$("#scheduleUpdateAlert").html("Error: " + data.error);
		    			    	}
		    			    },
		    			    error: function (jqXhr, textStatus, errorMessage) { // error callback 
		    			    	console.error(errorMessage);
		    			    }
		    			});

		    		});
		    		    		
		    	} else {
		    		$("#scheduleCreateAlert").html("Error: " + data.error);
		    	}
		    },
		    error: function (jqXhr, textStatus, errorMessage) { // error callback 
		    	console.error(errorMessage);
		    }
		});
	});
	
	$("#updateSchedule").click(function() {
		let schedule_date = $("#datepicker2").val();
		let time_slot = $("#updTimeSlot").val();
		let schedule_id = $("#updschedule_id").val();
		
		
		$.ajax(contextPath + '/doctorSchedule/update', {
			method: 'PUT',
		    dataType: 'json', // type of response data
		    contentType: 'application/json',
		    data: "{\"schedule_id\": \""+schedule_id+"\",\"session_id\": \""+session_id+"\", \"doctor_id\": \""+doctor_id+"\", \"time_slot\": \""+time_slot+"\", \"schedule_date\": \""+schedule_date+"\"}",
		    success: function (data) {   // success callback function
		    	if (data.sts === 1) {
		    		console.error(data);
		    		$("#scheduleUpdateAlert").html("Schedule Updated");
		    		    		
		    	} else {
		    		$("#scheduleUpdateAlert").html("Error: " + data.error);
		    	}
		    },
		    error: function (jqXhr, textStatus, errorMessage) { // error callback 
		    	console.error(errorMessage);
		    }
		});
	});
	
	
});