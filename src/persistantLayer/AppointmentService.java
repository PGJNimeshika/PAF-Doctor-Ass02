package persistantLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import models.AppointmentModel;
import models.Prescription;

public class AppointmentService {
	Connection conn;
	
	public AppointmentService(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList<AppointmentModel> getAppointments(AppointmentModel appointModel) throws SQLException {
		ArrayList<AppointmentModel> apointmentList = new ArrayList<AppointmentModel>();
		
		PreparedStatement selectStmnt = conn.prepareStatement("SELECT * FROM appointment WHERE doctor_id=? AND appoint_date=? AND appoint_status=?");
		selectStmnt.setString(1, appointModel.getDoctor_id());
		selectStmnt.setString(2, appointModel.getAppoint_date());
		selectStmnt.setString(3, appointModel.getAppoint_status());
		
		ResultSet result = selectStmnt.executeQuery();
		
		while (result.next()) {
			AppointmentModel am = new AppointmentModel();
			am.setAppoint_id(result.getString("appoint_id"));
			am.setAppoint_date(result.getString("appoint_date"));
			am.setAppoint_time(result.getString("appoint_time"));
			am.setPatient_id(result.getString("patient_id"));
			am.setAppoint_status(result.getString("appoint_status"));
			apointmentList.add(am);
		}
		
		return apointmentList;
	}
	
	public boolean rejectAppointment(String appoint_id) {
		try {        
			PreparedStatement updateStmnt = conn.prepareStatement("UPDATE appointment SET appoint_status=? WHERE appoint_id=?");
			updateStmnt.setString(1, "rejected");
			updateStmnt.setString(2, appoint_id);
			updateStmnt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public boolean acceptAppointment(Prescription pres) {
		try { 
			PreparedStatement updateStmnt = conn.prepareStatement("UPDATE appointment SET appoint_status=? WHERE appoint_id=?");
			updateStmnt.setString(1, "approved");
			updateStmnt.setString(2, pres.getAppoint_id());
			updateStmnt.execute();
			
			PreparedStatement insertStment = conn.prepareStatement("INSERT INTO prescription(patient_id, pres_date, pres_time, descript, doctor_id, appoint_id) VALUES(?, ?, ?, ?, ?, ?)");
			String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
			
			insertStment.setString(1, pres.getPatient_id());
			insertStment.setString(2, currentDate);
			insertStment.setString(3, currentTime);
			insertStment.setString(4, pres.getDescript());
			insertStment.setString(5, pres.getDoctor_id());
			insertStment.setString(6, pres.getAppoint_id());
			insertStment.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}	
	}
	
	public boolean checkAppoinmentAvailable(String appoint_id) throws SQLException {
		boolean isAppoinmentFound = false;
		String app_id = "";
		PreparedStatement selectStmnt = conn.prepareStatement("SELECT * FROM appointment WHERE appoint_id=?");
		selectStmnt.setString(1, appoint_id);
		ResultSet result = selectStmnt.executeQuery();
		
		while (result.next()) {
			app_id = result.getString("appoint_id");
		}
		
		if (!app_id.equals("")) {
			isAppoinmentFound = true;
		}
		
		return isAppoinmentFound;
	}
}
