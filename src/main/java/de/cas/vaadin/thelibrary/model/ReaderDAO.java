package de.cas.vaadin.thelibrary.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;

import de.cas.vaadin.thelibrary.model.bean.Reader;

public class ReaderDAO implements DaoInterface<Reader> {
	
	private final String CONN = DaoInterface.connectionString() + Reader.DBname;
	private final String UPDATE = "UPDATE READER SET NAME=?, ADDRESS=?, EMAIL=?, PHONE=? WHERE ID=?";
	private final String ADD = "INSERT INTO READER(NAME, ADDRESS, EMAIL, PHONE) VALUES(?,?,?,?)";
	private final String DEL = "DELETE FROM READER WHERE ID=?";
	private final String LIST = "SELECT * FROM READER";

	
	public ReaderDAO() {
		try {
			Class.forName("org.sqlite.JDBC");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean update(Reader bean) {
		try(Connection conn = DriverManager.getConnection(CONN);
				PreparedStatement pst = conn.prepareStatement(UPDATE)	
					){
				pst.setString(1, bean.getName());
				pst.setString(2, bean.getAddress());
				pst.setString(3, bean.getEmail());
				pst.setLong(4, bean.getPhoneNumber());
				pst.setInt(5, bean.getId());
				
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
	public boolean add(Reader bean) {
		try(Connection conn = DriverManager.getConnection(CONN);
				PreparedStatement pst = conn.prepareStatement(ADD)	
					){
				pst.setString(1, bean.getName());
				pst.setString(2, bean.getAddress());
				pst.setString(3, bean.getEmail());
				pst.setLong(4, bean.getPhoneNumber());
				
				
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
	public boolean delete(Set<Reader> readers) {
		try(Connection conn = DriverManager.getConnection(CONN);
				PreparedStatement pst = conn.prepareStatement(DEL)
				){
				for(Reader bean : readers) {
					pst.setInt(1, bean.getId());
			
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
	public ArrayList<Reader> getItems() {
		ArrayList<Reader> result = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(CONN);
			Statement st = conn.createStatement()
				){
			ResultSet rs = st.executeQuery(LIST);
			while(rs.next()) {
				Reader r = new Reader(rs.getString("name"), rs.getString("address"), 
									rs.getString("email"), rs.getInt("id"), rs.getLong("phone"));
				result.add(r);
			}
			return result;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}

}
