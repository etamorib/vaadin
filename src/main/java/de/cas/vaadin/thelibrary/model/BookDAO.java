package de.cas.vaadin.thelibrary.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;

import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;

public class BookDAO implements DaoInterface<Book> {
	
	private final String CONN = DaoInterface.connectionString() + Book.DBname;
	private final String UPDATE = "UPDATE BOOK SET TITLE=?, AUTHOR=?, YEAR=?, STATE=? WHERE ID=?";
	private final String ADD = "INSERT INTO BOOK(ID,TITLE, AUTHOR, YEAR, STATE) VALUES(?,?,?,?,?)";
	private final String DEL = "UPDATE BOOK SET STATE = ? WHERE ID=?";
	private final String LIST = "SELECT * FROM BOOK";

	public BookDAO() {
		try {
			Class.forName("org.sqlite.JDBC");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean update(Book bean) {
		try(Connection conn = DriverManager.getConnection(CONN);
			PreparedStatement pst = conn.prepareStatement(UPDATE)	
				){
			pst.setString(1, bean.getTitle());
			pst.setString(2, bean.getAuthor());
			pst.setInt(3, bean.getYear());
			pst.setString(4, bean.getState().toString());
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
	public boolean add(Book bean) {
		try(Connection conn = DriverManager.getConnection(CONN);
				PreparedStatement pst = conn.prepareStatement(ADD)	
					){
				pst.setInt(1, bean.getId());
				pst.setString(2, bean.getTitle());
				pst.setString(3, bean.getAuthor());
				pst.setInt(4, bean.getYear());
				pst.setString(5, bean.getState().toString());
				
				
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
	public boolean delete(Set<Book> books) {
		try(Connection conn = DriverManager.getConnection(CONN);
				PreparedStatement pst = conn.prepareStatement(DEL)
				){
				for(Book bean : books) {
					System.out.println(bean);
					pst.setString(1, BookState.Deleted.toString());
					pst.setInt(2, bean.getId());
					
					
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
	public ArrayList<Book> getItems() {
		ArrayList<Book> result = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(CONN);
			Statement st = conn.createStatement()
				){
			ResultSet rs = st.executeQuery(LIST);
			while(rs.next()) {
				Book b = new Book(rs.getString(2), rs.getString(3), rs.getInt(1), rs.getInt(4), BookState.valueOf(rs.getString(5)));
				result.add(b);
			}
			return result;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	


	
	
}
