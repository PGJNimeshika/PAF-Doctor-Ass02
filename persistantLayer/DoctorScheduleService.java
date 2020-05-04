package persistantLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import models.DoctorScheduleModel;

public class DoctorScheduleService {
	Connection conn;
	
	public DoctorScheduleService(Connection conn) {
		this.conn = conn;
	}
	
	public boolean createSchedule(DoctorScheduleModel dsm) {
		try {
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            dsm.setSchedule_date(formatter.format(date));
            
			PreparedStatement insertStmnt = conn.prepareStatement("INSERT INTO doctor_schedule(schedule_date, doctor_id, time_slot) VALUES (?, ?, ?)");
			insertStmnt.setString(1, dsm.getSchedule_date().toString());
			insertStmnt.setString(2, dsm.getDoctor_id());
			insertStmnt.setString(3, dsm.getTime_slot());
			insertStmnt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public boolean checkTimeSlotExists(DoctorScheduleModel dsm) {
		boolean isRecordExists = false;
		try {
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            dsm.setSchedule_date(formatter.format(date));
            
			PreparedStatement insertStmnt = conn.prepareStatement("SELECT * FROM doctor_schedule WHERE schedule_date=? AND doctor_id=? AND time_slot=?");
			insertStmnt.setString(1, dsm.getSchedule_date().toString());
			insertStmnt.setString(2, dsm.getDoctor_id());
			insertStmnt.setString(3, dsm.getTime_slot());
			
			ResultSet result = insertStmnt.executeQuery();
			System.out.println(result);
	        while(result.next()){
	        	isRecordExists = true;
	        	System.out.println("true");
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isRecordExists;
	}
	
	public ArrayList<DoctorScheduleModel> getScheduleList(String schedule_date, String doctor_id) {
		ArrayList<DoctorScheduleModel> scheduleList = new ArrayList<DoctorScheduleModel>();
		
		try {
			if (schedule_date.equals("")) {
				PreparedStatement selectMultiple = conn.prepareStatement("SELECT * FROM doctor_schedule WHERE doctor_id=?");
				selectMultiple.setString(1, doctor_id);
				
				ResultSet multipleResult = selectMultiple.executeQuery();
				
				while (multipleResult.next()) {
					DoctorScheduleModel dsm = new DoctorScheduleModel();
					dsm.setSchedule_id(multipleResult.getString("schedule_id"));
					dsm.setSchedule_date(multipleResult.getString("schedule_date"));
					dsm.setTime_slot(multipleResult.getString("time_slot"));
					scheduleList.add(dsm);
				}
			} else {
				PreparedStatement selectSingle = conn.prepareStatement("SELECT * FROM doctor_schedule WHERE doctor_id=? AND schedule_date=?");
				selectSingle.setString(1, doctor_id);
				selectSingle.setString(2, schedule_date);
				
				
				ResultSet singleResult = selectSingle.executeQuery();
				
				while (singleResult.next()) {
					DoctorScheduleModel dsm = new DoctorScheduleModel();
					dsm.setSchedule_id(singleResult.getString("schedule_id"));
					dsm.setSchedule_date(singleResult.getString("schedule_date"));
					dsm.setTime_slot(singleResult.getString("time_slot"));
					scheduleList.add(dsm);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		return scheduleList;
	}
	
	public boolean updateSchedule(DoctorScheduleModel dsm) {
		try {        
			PreparedStatement insertStmnt = conn.prepareStatement("UPDATE doctor_schedule SET time_slot=? WHERE schedule_date=? AND doctor_id=? AND schedule_id=?");
			insertStmnt.setString(1, dsm.getTime_slot());
			insertStmnt.setString(2, dsm.getSchedule_date());
			insertStmnt.setString(3, dsm.getDoctor_id());
			insertStmnt.setString(4, dsm.getSchedule_id());
			insertStmnt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public boolean deleteSchedule(String scheduleId) {
		try {        
			PreparedStatement insertStmnt = conn.prepareStatement("DELETE FROM doctor_schedule WHERE schedule_id=?");
			insertStmnt.setString(1, scheduleId);
			insertStmnt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}
}
