package it.quattrocchi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.quattrocchi.support.UserBean;

public class AccessModel {

	private static final String TABLE_NAME = "quattrocchidb.cliente";
	
public void doSave(UserBean user) throws SQLException{
		
		Connection conn = null;
		PreparedStatement stm = null;
		
		String query = "INSERT INTO " + TABLE_NAME + " values (?,?,?,?,?,?,?,?,?)";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, user.getUser());
			stm.setString(2, user.getPassword());
			stm.setString(3, user.getNome());
			stm.setString(4, user.getCognome());
			stm.setDate(5, user.getDataDiNascita());
			stm.setString(6, user.getStato());
			stm.setString(7,user.getCap());
			stm.setString(8, user.getIndirizzo());
			stm.setString(9, user.getEmail());
			
			stm.executeUpdate();
			
			conn.commit();
		}
		
		finally {
			try {
				if (stm != null)
					stm.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(conn);
			}
		}
	}
	
	public UserBean doRetrieveByKey(String userid, String passid) throws SQLException {

		Connection conn = null;
		PreparedStatement stm  = null;
		
		UserBean bean = null;
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE User = ? and Pwd = ?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, userid);
			stm.setString(2, passid);
			
			ResultSet rs = stm.executeQuery();
			
			while(rs.next()) {
				bean = new UserBean();
				bean.setUser(rs.getString("User"));
				bean.setNome(rs.getString("Nome"));
				bean.setCognome(rs.getString("Cognome"));
				bean.setDataDiNascita(rs.getDate("DataNascita"));
				bean.setStato(rs.getString("Stato"));
				bean.setCap(rs.getString("Cap"));
				bean.setIndirizzo(rs.getString("indirizzo"));
				bean.setEmail(rs.getString("email"));
			}
		}
		finally {
			try {
				if (stm != null)
					stm.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(conn);
			}
		}
		
		return bean;
	}

}
