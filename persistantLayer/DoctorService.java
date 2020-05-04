package persistantLayer;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import models.DoctorModel;


public class DoctorService {
	Connection conn;
	
	public DoctorService(Connection conn) {
		this.conn = conn;
	}

	public String doctorLogin(String username, String password) throws SQLException {
		String hashString = "";

		PreparedStatement selectStmnt = conn.prepareStatement("SELECT doctor_id FROM doctor_logins WHERE BINARY doctor_username=? && BINARY doctor_password=?");
		selectStmnt.setString(1, username);
		selectStmnt.setString(2, password);
        
        ResultSet result = selectStmnt.executeQuery();
        String doctor_id = "";
        while(result.next()){
            doctor_id = result.getString(1);
        }
        
        if (!doctor_id.equals("")) {
            PreparedStatement updateStmnt = conn.prepareStatement("UPDATE doctor_logins SET doctor_last_login=?, docotor_session=? WHERE doctor_id=?");
            
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
                
            hashString = UUID.randomUUID().toString();
            hashString = hashString.replace("-", "");
            
            updateStmnt.setString(1, formatter.format(date));
            updateStmnt.setString(2, hashString);
            updateStmnt.setString(3, doctor_id);
            updateStmnt.execute();
        }
        
		return hashString;
	} 
	
	public String checkedLoggedIn(String doctor_id, String session_id) throws SQLException, ParseException {
		String msg = "";
		
		PreparedStatement selectStmnt = conn.prepareStatement("SELECT doctor_last_login FROM doctor_logins WHERE doctor_id=? AND docotor_session=?");
		selectStmnt.setString(1, doctor_id);
		selectStmnt.setString(2, session_id);
		
        ResultSet result = selectStmnt.executeQuery();
        String retrievedDate = "";
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        while(result.next()){
        	retrievedDate = result.getString(1);
        }
        
        if (retrievedDate.equals("")) {
        	msg = "Not logged In";
        	return msg;
        } else {
        	
        	Date now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStamp);
        	Date last_login = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(retrievedDate);
            long diff = (now.getTime() - last_login.getTime()) / (60 * 1000) % 60; 
            
            if (diff > 10) {
            	msg = "Session Expired";
            } else {
            	msg = "LoggedIn";
            	PreparedStatement updateStmnt = conn.prepareStatement("UPDATE doctor_logins SET doctor_last_login=? WHERE doctor_id=?");
            	updateStmnt.setString(1, timeStamp);
            	updateStmnt.setString(2, doctor_id);
            	updateStmnt.execute();
            }
            
    		return msg;
        }
	}
	
	public DoctorModel getDoctor(DoctorModel dm) throws SQLException {
		PreparedStatement selectStmnt = conn.prepareStatement("SELECT * FROM doctor WHERE doctor_id=?");
		selectStmnt.setString(1, dm.getDoctor_id());
		
        ResultSet result = selectStmnt.executeQuery();

        while(result.next()){
            dm.setDoctor_id(result.getString("doctor_id"));
            dm.setDoctor_license_no(result.getString("doctor_license_no"));
            dm.setDoctor_name(result.getString("doctor_name"));
            dm.setDoctor_address(result.getString("doctor_address"));
            dm.setDoctor_phone(result.getString("doctor_phone"));
            dm.setDoctor_email(result.getString("doctor_email"));
            dm.setDoctor_gender(result.getString("doctor_gender"));
            dm.setDoctor_specialization(result.getString("doctor_specialization"));     
        }
        
        return dm;
	}
	
	public boolean updateDoctor(DoctorModel dm) {
		try {
			PreparedStatement updateStmnt = conn.prepareStatement("UPDATE doctor SET doctor_license_no=?, doctor_name=?, doctor_address=?, doctor_phone=?, doctor_email=?, doctor_gender=?, doctor_specialization=? WHERE doctor_id=?");
			updateStmnt.setString(1, dm.getDoctor_license_no());
			updateStmnt.setString(2, dm.getDoctor_name());
			updateStmnt.setString(3, dm.getDoctor_address());
			updateStmnt.setString(4, dm.getDoctor_phone());
			updateStmnt.setString(5, dm.getDoctor_email());
			updateStmnt.setString(6, dm.getDoctor_gender());
			updateStmnt.setString(7, dm.getDoctor_specialization());
			updateStmnt.setString(8, dm.getDoctor_id());
			updateStmnt.execute();
			
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		
		return true;

	}
	
	public boolean doctorLogout(String doctor_id) {
		try {
			PreparedStatement updateStmnt = conn.prepareStatement("UPDATE doctor_logins SET docotor_session=? WHERE doctor_id=?");
			updateStmnt.setString(1, "");
			updateStmnt.setString(2, doctor_id);
			updateStmnt.execute();
			
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
		
		return true;
	}
}
