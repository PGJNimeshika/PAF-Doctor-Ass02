package controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import models.AppointmentModel;
import models.DoctorScheduleModel;
import models.Prescription;
import persistantLayer.AppointmentService;
import persistantLayer.DoctorScheduleService;
import persistantLayer.DoctorService;

@Path("/appointment")
public class AppointmentController {
	
	@GET
    @Path("/view")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewSchedules(String json) throws ClassNotFoundException, SQLException, JSONException, ParseException {
		Connection conn = DBConnection.getConnection();
		JSONObject receivedJson;
		JSONObject sendingjson = new JSONObject();
		
		try {
			receivedJson = new JSONObject(json);
		} catch (JSONException ex) {
			sendingjson.put("error", "There is an error in JSON syntax");
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
		}
		
		AppointmentService as = new AppointmentService(conn);
		DoctorService ds = new DoctorService(conn);
	
		String msg = ds.checkedLoggedIn(receivedJson.getString("doctor_id"), receivedJson.getString("session_id"));
		
		if (msg.equals("LoggedIn")) {
			
			String receivedDate = receivedJson.getString("appoint_date").trim();
			
            try {
            	if (!receivedDate.equals("")) {
                	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                	formatter.parse(receivedDate);
            	}
            } catch(ParseException ex) {
            	ex.printStackTrace();
				sendingjson.put("sts", 0);
				sendingjson.put("error", "Invalid Date Format");
			
				String result = sendingjson.toString();
				return Response.status(200).entity(result).build();
            }

            AppointmentModel am = new AppointmentModel();
            am.setDoctor_id(receivedJson.getString("doctor_id"));
            am.setAppoint_status(receivedJson.getString("appoint_status"));
            am.setAppoint_date(receivedJson.getString("appoint_date"));

			ArrayList<AppointmentModel> appointmentList = as.getAppointments(am);
			
			if (appointmentList.isEmpty()) {
				sendingjson.put("sts", 1);
				sendingjson.put("msg", "No Records Found");
			} else {
				 JSONArray jsonArray = new JSONArray();
			      for (AppointmentModel appointModel : appointmentList) { 		      
			    	  JSONObject amJson = new JSONObject();
			    	  amJson.put("appoint_id", appointModel.getAppoint_id());
			    	  amJson.put("appoint_date", appointModel.getAppoint_date());
			    	  amJson.put("appoint_time", appointModel.getAppoint_time());
			    	  amJson.put("appoint_status", appointModel.getAppoint_status());
			    	  amJson.put("patient_id", appointModel.getPatient_id());
			    	  amJson.put("doctor_id", appointModel.getDoctor_id());
			    	  jsonArray.put(amJson);
			      }
  
			      sendingjson.put("sts", 1);
			      sendingjson.put("appoint_list", jsonArray);
			}

			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
			
		} else {
			sendingjson.put("error", msg);
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
		}
	
    }
	
	@PUT
    @Path("/reject")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reject(String json) throws ClassNotFoundException, SQLException, JSONException, ParseException {
		Connection conn = DBConnection.getConnection();
		JSONObject receivedJson;
		JSONObject sendingjson = new JSONObject();
		
		try {
			receivedJson = new JSONObject(json);
		} catch (JSONException ex) {
			sendingjson.put("error", "There is an error in JSON syntax");
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
		}

		AppointmentService as = new AppointmentService(conn);
		DoctorService ds = new DoctorService(conn);

	
		String msg = ds.checkedLoggedIn(receivedJson.getString("doctor_id"), receivedJson.getString("session_id"));	
		if (msg.equals("LoggedIn")) {
			
			boolean success = as.rejectAppointment(receivedJson.getString("appoint_id"));
			
			sendingjson = new JSONObject();
			if (success) {
				sendingjson.put("sts", 1);
			} else {
				sendingjson.put("sts", 0);
				sendingjson.put("error", "Error in rejecting appoinment");
			}
			
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
			
		} else {
			sendingjson.put("error", msg);
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
		}
		
    }
	
	@POST
    @Path("/approve")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response approve(String json) throws ClassNotFoundException, SQLException, JSONException, ParseException {
		Connection conn = DBConnection.getConnection();
		JSONObject receivedJson;
		JSONObject sendingjson = new JSONObject();
		
		try {
			receivedJson = new JSONObject(json);
		} catch (JSONException ex) {
			sendingjson.put("error", "There is an error in JSON syntax");
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
		}

		AppointmentService as = new AppointmentService(conn);
		DoctorService ds = new DoctorService(conn);

	
		String msg = ds.checkedLoggedIn(receivedJson.getString("doctor_id"), receivedJson.getString("session_id"));	
		if (msg.equals("LoggedIn")) {
		
			Prescription pres = new Prescription();
			pres.setDescript(receivedJson.getString("descript"));
			pres.setDoctor_id(receivedJson.getString("doctor_id"));
			pres.setPatient_id(receivedJson.getString("patient_id"));
			pres.setAppoint_id(receivedJson.getString("appoint_id"));
			
			boolean isAppointmentAvailable = as.checkAppoinmentAvailable(pres.getAppoint_id());
			
			if (isAppointmentAvailable) {
				boolean success = as.acceptAppointment(pres);
				
				sendingjson = new JSONObject();
				if (success) {
					sendingjson.put("sts", 1);
				} else {
					sendingjson.put("sts", 0);
					sendingjson.put("error", "Error in creating prescription");
				}
				
				String result = sendingjson.toString();
				return Response.status(200).entity(result).build();
			} else {
				sendingjson.put("sts", 0);
				sendingjson.put("error", "No appointment for the appoinment Id");
				String result = sendingjson.toString();
				return Response.status(200).entity(result).build();
			}		
			
		} else {
			sendingjson.put("error", msg);
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
		}
		
    }
}
