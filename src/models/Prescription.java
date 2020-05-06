package models;

public class Prescription {
	String pres_id;
	String patient_id;
	String pres_date;
	String pres_time;
	String descript;
	String doctor_id;
	String appoint_id;
	
	public String getPres_id() {
		return pres_id;
	}
	public void setPres_id(String pres_id) {
		this.pres_id = pres_id;
	}
	public String getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}
	public String getPres_date() {
		return pres_date;
	}
	public void setPres_date(String pres_date) {
		this.pres_date = pres_date;
	}
	public String getPres_time() {
		return pres_time;
	}
	public void setPres_time(String pres_time) {
		this.pres_time = pres_time;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}
	public String getAppoint_id() {
		return appoint_id;
	}
	public void setAppoint_id(String appoint_id) {
		this.appoint_id = appoint_id;
	}
	
	
}
