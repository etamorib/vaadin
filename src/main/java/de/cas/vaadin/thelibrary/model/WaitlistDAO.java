package de.cas.vaadin.thelibrary.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

import de.cas.vaadin.thelibrary.model.bean.Waitlist;

public class WaitlistDAO implements DaoInterface<Waitlist> {
	
	private final String CONN = DaoInterface.connectionString() + Waitlist.DBname;
	private static final String ADD = "INSERT INTO WAITLIST (BOOKID, READERID, REQUESTDATE, WAITDATE) VALUES(?,?,?,?)";
	private static final String DEL = "DELETE FROM WAITLIST WHERE BOOKID =? AND READERID =?";
	private static final String LIST = "SELECT * FROM WAITLIST";
	
	public WaitlistDAO() {
		try {
			Class.forName("org.sqlite.JDBC");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/** 
	 * Adds a new waitlist the the Waitlist database.
	 * @return true if adding was successful <br>
	 * false it adding was unsuccessful
	 * @param Waitlist object
	 */
	@Override
	public boolean add(Waitlist bean) {
		try(Connection conn = DriverManager.getConnection(CONN);
				PreparedStatement pst = conn.prepareStatement(ADD)	
					){
				pst.setInt(1, bean.getBookId());
				pst.setInt(2, bean.getReaderId());
				pst.setString(3, bean.getRequestDate().toString());
				pst.setString(4, bean.getWaitDate().toString());
				
				
				int affected = pst.executeUpdate();
				if(affected!=1) {
					return false;
				}else {
					return true;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	}
	/**
	 * Deletes one or more row from the waitlist database.
	 * @return true if deleting was successful <br>
	 * false if deleting was unsuccessful
	 * @param A set of Waitlist objects
	 */
	@Override
	public boolean delete(Set<Waitlist> waitlists) {
		try(Connection conn = DriverManager.getConnection(CONN);
				PreparedStatement pst = conn.prepareStatement(DEL)
				){
				for(Waitlist bean : waitlists) {
					pst.setInt(1, bean.getBookId());
					pst.setInt(2, bean.getReaderId());
			
					int affected = pst.executeUpdate();
					if(affected!=1) {
						return false;
					}
				}
				return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Return every data from the Waitlist database
	 * @return ArrayList of Waitlist objects
	 */
	@Override
	public ArrayList<Waitlist> getItems() {
		ArrayList<Waitlist> result = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(CONN);
			Statement st = conn.createStatement()
				){
			ResultSet rs = st.executeQuery(LIST);
			while(rs.next()) {
				Waitlist w = new Waitlist(rs.getInt("bookid"), rs.getInt("readerid"), 
						LocalDate.parse(rs.getString("requestdate")), LocalDate.parse(rs.getString("waitdate")));
				result.add(w);
			}
			return result;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}

}
