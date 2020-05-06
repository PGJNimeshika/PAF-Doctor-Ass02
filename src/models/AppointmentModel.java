package models;

public class AppointmentModel {
	String appoint_id;
	String patient_id;
	String appoint_date;
	String appoint_time;
	String appoint_status;
	String doctor_id;

	public String getAppoint_id() {
		return appoint_id;
	}
	public void setAppoint_id(String appoint_id) {
		this.appoint_id = appoint_id;
	}
	public String getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}
	public String getAppoint_date() {
		return appoint_date;
	}
	public void setAppoint_date(String appoint_date) {
		this.appoint_date = appoint_date;
	}
	public String getAppoint_time() {
		return appoint_time;
	}
	public void setAppoint_time(String appoint_time) {
		this.appoint_time = appoint_time;
	}
	public String getAppoint_status() {
		return appoint_status;
	}
	public void setAppoint_status(String appoint_status) {
		this.appoint_status = appoint_status;
	}
	public String getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}
	
	
}
