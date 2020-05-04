package controllers;

import java.sql.*;
import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import models.DoctorModel;
import persistantLayer.DoctorService;

@Path("/doctor")
public class DoctorController {
	
	@POST
    @Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String json) throws ClassNotFoundException, SQLException {
		Connection conn = DBConnection.getConnection();
		JSONObject receivedJson;
		JSONObject sendingjson;
		
		try {
			receivedJson = new JSONObject(json);
		} catch (JSONException ex) {
			sendingjson = new JSONObject();
			sendingjson.put("error", "There is an error in JSON syntax");
			String result = sendingjson.toString();
			return Response.status(200).entity(result).build();
		}
		
		DoctorService ds = new DoctorService(conn);
		String loginSuccess = ds.doctorLogin(receivedJson.getString("username"), receivedJson.getString("password"));
		
		sendingjson = new JSONObject();
		if (!loginSuccess.equals("")) {
			sendingjson.put("session_id", loginSuccess);
			sendingjson.put("sts", 1);
		} else {
			sendingjson.put("sts", 0);
			sendingjson.put("error", "Username or password is incorrect");
		}
		
		String result = sendingjson.toString();
		return Response.status(200).entity(result).build();
  
    }
	
	@PUT
    @Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response updateDoctor(String json) throws ClassNotFoundException, SQLException, JSONException, ParseException {
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
		
		DoctorModel dm = new DoctorModel();
		DoctorService ds = new DoctorService(conn);
		
		String msg = ds.checkedLoggedIn(receivedJson.getString("doctor_id"), receivedJson.getString("session_id"));
		
		if (msg.equals("LoggedIn")) {
			dm.setDoctor_id(receivedJson.getString("doctor_id"));
			
			dm = ds.getDoctor(dm);
			dm.setDoctor_license_no(receivedJson.getString("doctor_license_no"));
			dm.setDoctor_name(receivedJson.getString("doctor_name"));
			dm.setDoctor_phone(receivedJson.getString("doctor_phone"));
			dm.setDoctor_address(receivedJson.getString("doctor_address"));
			dm.setDoctor_email(receivedJson.getString("doctor_email"));
			dm.setDoctor_gender(receivedJson.getString("doctor_gender"));
			dm.setDoctor_specialization(receivedJson.getString("doctor_specialization"));
			
			boolean isUpdated = ds.updateDoctor(dm);
			
			if (isUpdated) {
				sendingjson.put("sts", 1);
			} else {
				sendingjson.put("sts", 0);
				sendingjson.put("error", "There is an error in updating doctor details");
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
    @Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response logoutDoctor(String json) throws ClassNotFoundException, SQLException {
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
		
		DoctorService ds = new DoctorService(conn);
		
		boolean isLoggedOut = ds.doctorLogout(receivedJson.getString("doctor_id"));
		
		if (isLoggedOut) {
			sendingjson.put("sts", 1);
		} else {
			sendingjson.put("sts", 0);
			sendingjson.put("error", "There is an error in logging out");
		}
			
		String result = sendingjson.toString();
		return Response.status(200).entity(result).build();
	}
}
