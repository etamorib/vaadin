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

import de.cas.vaadin.thelibrary.model.bean.Rent;

public class RentDAO implements DaoInterface<Rent> {
	
	private final String CONN = DaoInterface.connectionString() + Rent.DBname;
	private final String ADD = "INSERT INTO RENT (BOOKID, READERID, RENTDATE, RETURNDATE) VALUES(?,?,?,?)";
	private final String DEL = "DELETE FROM RENT WHERE BOOKID =? AND READERID=?";
	private final String LIST = "SELECT * FROM RENT";
	
	public RentDAO() {
		try {
			Class.forName("org.sqlite.JDBC");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean add(Rent bean) {
		try(Connection conn = DriverManager.getConnection(CONN);
				PreparedStatement pst = conn.prepareStatement(ADD)	
					){
				pst.setInt(1, bean.getBookId());
				pst.setInt(2, bean.getReaderId());
				pst.setString(3, bean.getRentTime().toString());
				pst.setString(4, bean.getReturnTime().toString());
				
				
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

	@Override
	public boolean delete(Set<Rent> rents) {
		try(Connection conn = DriverManager.getConnection(CONN);
				PreparedStatement pst = conn.prepareStatement(DEL)
				){
				for(Rent bean : rents) {
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

	@Override
	public ArrayList<Rent> getItems() {
		ArrayList<Rent> result = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(CONN);
			Statement st = conn.createStatement()
				){
			ResultSet rs = st.executeQuery(LIST);
			while(rs.next()) {
				Rent r = new Rent(LocalDate.parse(rs.getString("rentdate")), LocalDate.parse(rs.getString("returndate")), 
						rs.getInt("bookid"), rs.getInt("readerid"));
				result.add(r);
			}
			return result;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	

	

}
