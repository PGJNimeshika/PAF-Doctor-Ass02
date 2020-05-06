package controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import models.DoctorModel;
import models.DoctorScheduleModel;
import persistantLayer.DoctorScheduleService;
import persistantLayer.DoctorService;


@Path("/doctorSchedule")
public class DoctorScheduleController {
	
	@POST
    @Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String json) throws ClassNotFoundException, SQLException, JSONException, ParseException {
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
		
		DoctorScheduleService dss = new DoctorScheduleService(conn);
		DoctorScheduleModel dsm = new DoctorScheduleModel();
		DoctorService ds = new DoctorService(conn);
		dsm.setDoctor_id(receivedJson.getString("doctor_id"));
		dsm.setSchedule_date(receivedJson.getString("schedule_date"));
		dsm.setTime_slot(receivedJson.getString("time_slot"));
		
		String msg = ds.checkedLoggedIn(receivedJson.getString("doctor_id"), receivedJson.getString("session_id"));
		
		if (msg.equals("LoggedIn")) {
			boolean exists = dss.checkTimeSlotExists(dsm);
			
			if (exists) {
				sendingjson.put("sts", 0);
				sendingjson.put("error", "Record already exists");
			} else {
				boolean success = dss.createSchedule(dsm);
				
				sendingjson = new JSONObject();
				if (success) {
					sendingjson.put("sts", 1);
				} else {
					sendingjson.put("sts", 0);
					sendingjson.put("error", "Error creating doctor schedule");
				}
			}
			
			
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
			
		} else {
			sendingjson.put("error", msg);
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
		}
		
	
    }
	
	@GET
    @Path("/view")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewSchedules(@QueryParam("session_id") String session_id, @QueryParam("doctor_id") String doctor_id, @QueryParam("schedule_date") String schedule_date) throws ClassNotFoundException, SQLException, JSONException, ParseException {
		Connection conn = DBConnection.getConnection();
		JSONObject receivedJson;
		JSONObject sendingjson = new JSONObject();
		System.out.println(doctor_id);
			
		DoctorScheduleService dss = new DoctorScheduleService(conn);
		DoctorService ds = new DoctorService(conn);
	
		String msg = ds.checkedLoggedIn(doctor_id, session_id);
		
		if (msg.equals("LoggedIn")) {
			
			String receivedDate = schedule_date.trim();
			
			
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


			ArrayList<DoctorScheduleModel> scheduleList = dss.getScheduleList(schedule_date, doctor_id);
			
			if (scheduleList.isEmpty()) {
				sendingjson.put("sts", 0);
				sendingjson.put("msg", "No Records Found");
			} else {
				 JSONArray jsonArray = new JSONArray();
			      for (DoctorScheduleModel dsm : scheduleList) { 		      
			    	  JSONObject dsmJson = new JSONObject();
			    	  dsmJson.put("schedule_date", dsm.getSchedule_date());
			    	  dsmJson.put("time_slot", dsm.getTime_slot());
			    	  dsmJson.put("schedule_id", dsm.getSchedule_id());
			    	  jsonArray.put(dsmJson);
			      }
			      
			      sendingjson.put("sts", 1);
			      sendingjson.put("schedule_list", jsonArray);
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
    @Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(String json) throws ClassNotFoundException, SQLException, JSONException, ParseException {
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
		
		String receivedDate = receivedJson.getString("schedule_date");
		
		try {
        	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        	formatter.parse(receivedDate);
        } catch(ParseException ex) {
        	ex.printStackTrace();
			sendingjson.put("sts", 0);
			sendingjson.put("error", "Invalid Date Format");
		
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
        }
		
		DoctorScheduleService dss = new DoctorScheduleService(conn);
		DoctorScheduleModel dsm = new DoctorScheduleModel();
		DoctorService ds = new DoctorService(conn);

	
		String msg = ds.checkedLoggedIn(receivedJson.getString("doctor_id"), receivedJson.getString("session_id"));	
		if (msg.equals("LoggedIn")) {	
			dsm.setDoctor_id(receivedJson.getString("doctor_id"));
			dsm.setTime_slot(receivedJson.getString("time_slot"));
			dsm.setSchedule_id(receivedJson.getString("schedule_id"));
			dsm.setSchedule_date(receivedDate);
			
			boolean success = dss.updateSchedule(dsm);
			
			sendingjson = new JSONObject();
			if (success) {
				sendingjson.put("sts", 1);
			} else {
				sendingjson.put("sts", 0);
				sendingjson.put("error", "Error updating doctor schedule");
			}
			
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
			
		} else {
			sendingjson.put("error", msg);
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
		}
		
	
    }
	
	@DELETE
    @Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(String json) throws ClassNotFoundException, SQLException, JSONException, ParseException {
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
						
		DoctorScheduleService dss = new DoctorScheduleService(conn);
		DoctorService ds = new DoctorService(conn);

	
		String msg = ds.checkedLoggedIn(receivedJson.getString("doctor_id"), receivedJson.getString("session_id"));	
		if (msg.equals("LoggedIn")) {	
		
			boolean success = dss.deleteSchedule(receivedJson.getString("schedule_id"));
			
			sendingjson = new JSONObject();
			if (success) {
				sendingjson.put("sts", 1);
			} else {
				sendingjson.put("sts", 0);
				sendingjson.put("error", "Error deleting doctor schedule");
			}
			
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
			
		} else {
			sendingjson.put("error", msg);
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
		}
		
	
    }
	

}
