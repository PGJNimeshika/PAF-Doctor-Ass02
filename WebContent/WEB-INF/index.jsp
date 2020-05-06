<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CDE IT Solutions</title>

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/bootstrap.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/bootstrap-grid.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/bootstrap-reboot.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/gijgo.min.css" />

</head>
<body>

	<div class="container mt-4">

		<div class="jumbotron">
			<h1 class="display-4 text-center">Doctor API</h1>

		</div>
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item active text-center" aria-current="page">Doctor
					API Test</li>
			</ol>
		</nav>

		<hr class="my-4">

		<form>
			<div class="form-group">
				<label for="">Username</label> <input type="text"
					class="form-control" id="username">
			</div>
			<div class="form-group">
				<label for="">Password</label> <input type="password"
					class="form-control" id="password">
			</div>
			<button type="button" class="btn btn-primary" id="loginSubmit">Submit</button>
		</form>
		
		<div class="mt-3 alert alert-info" id="loginAlert" role="alert">
				Try Login
		</div>
		
		<hr class="my-4">
		
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item active text-center" aria-current="page">View Doctor Details and Update</li>
			</ol>
		</nav>
		
		<button type="button" class="btn btn-success" id="viewDoctor">View Doctor Details</button>
		
		<form class="mt-2">
			<div class="form-group">
				<label for="">License No</label> 
				<input class="form-control form-control-sm" type="text" id="doctorLicenceNo">
			</div>
			<div class="form-group">
				<label for="">Name</label> 
				<input class="form-control form-control-sm" type="text" id="doctorName">
			</div>
			<div class="form-group">
				<label for="">Phone</label> 
				<input class="form-control form-control-sm" type="text" id="doctorPhone">
			</div>
			<div class="form-group">
				<label for="">Email</label> 
				<input class="form-control form-control-sm" type="email" id="doctorEmail">
			</div>
			<div class="form-group">
				<label for="">Address</label> 
				<input class="form-control form-control-sm" type="text" id="doctorAddress">
			</div>
			<div class="form-group">
				<label for="">Gender</label> 
			    <select class="form-control form-control-sm" id="doctorGender">
			      <option value="male">Male</option>
			      <option value="female">Female</option>

			    </select>
			</div>
			<div class="form-group">
				<label for="">Specialization</label> 
				<input class="form-control form-control-sm" type="text" id="doctorSpec">
			</div>
			<button type="button" class="btn btn-primary" id="doctorUpdateBtn">Submit</button>
		</form>
		
		<div class="mt-3 alert alert-info" id="doctorUpdateAlert" role="alert">
			Try Updating Doctor
		</div>
		
		<hr class="my-4">
		
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item active text-center" aria-current="page">Create Schedule</li>
			</ol>
		</nav>
		
		<form class="mt-2">
			<div class="form-group">
				<label for="">Date</label> 
				<input class="form-control form-control-sm" type="text" id="datepicker" readOnly>
			</div>
			<div class="form-group">
				<label for="">Time Slot</label> 
				<input class="form-control form-control-sm" type="text" id="timeSlot">
			</div>
			<button type="button" class="btn btn-primary" id="doctorCreateSchedule">Submit</button>
		</form>
		
		<div class="mt-3 alert alert-info" id="scheduleCreateAlert" role="alert">
			Try Creating a Schedule
		</div>
		
		<hr class="my-4">
		
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item active text-center" aria-current="page">View Schedules</li>
			</ol>
		</nav>
		
		<form class="mt-2">
			<div class="form-group">
				<label for="">Date</label> 
				<input class="form-control form-control-sm" type="text" id="datepicker1" readOnly>
			</div>
		</form>
		
		<button type="button" class="btn btn-success mb-3" id="viewSchedules">View Doctor Schedules</button>
		
		
		<table class="table table-striped">
		  <thead>
		    <tr>
		      <th scope="col">Schedule ID</th>
		      <th scope="col">Date</th>
		      <th scope="col">Time Slot</th>
		     <th scope="col"></th>
		     <th scope="col"></th>
		
		    </tr>
		  </thead>
		  <tbody id="scheduleBody">
		  </tbody>
		</table>
		
		<form class="mt-2">
			<div class="form-group">
				<label for="">Schedule ID</label> 
				<input class="form-control form-control-sm" type="text" id="updschedule_id" readOnly>
			</div>
			<div class="form-group">
				<label for="">Date</label> 
				<input class="form-control form-control-sm" type="text" id="datepicker2" disabled>
			</div>
			<div class="form-group">
				<label for="">Time Slot</label> 
				<input class="form-control form-control-sm" type="text" id="updTimeSlot">
			</div>
		</form>
		
		<button type="button" class="btn btn-primary mb-3" id="updateSchedule">Update Schedule</button>
		
	    <div class="mt-3 alert alert-info" id="scheduleUpdateAlert" role="alert">
			Try Creating a Schedule
		</div>

	</div>


	<script
		src="${pageContext.request.contextPath}/assets/js/jquery-3.5.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/gijgo.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
		

</body>
</html>