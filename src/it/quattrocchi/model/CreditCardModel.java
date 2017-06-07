package it.quattrocchi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.quattrocchi.support.CreditCardBean;


public class CreditCardModel {

	private static final String TABLE_NAME = "quattrocchidb.CartaCredito";

	public void doSave(CreditCardBean creditCard) throws SQLException{

		Connection conn = null;
		PreparedStatement stm = null;

		String query = "INSERT INTO " + TABLE_NAME + " values (?,?,?,?,?,?)";

		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, creditCard.getNumeroCC());
			stm.setString(2, creditCard.getIntestatario());
			stm.setString(3, creditCard.getCircuito());
			stm.setDate(4, creditCard.getDataScadenza());
			stm.setString(5, creditCard.getCvcCvv());
			stm.setString(6, creditCard.getCliente());

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
	
	public ArrayList<CreditCardBean> doRetrieveByCliente(String user) throws SQLException{
		
		ArrayList<CreditCardBean> cc = new ArrayList<CreditCardBean>();
		
		Connection conn = null;
		PreparedStatement stm  = null;
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE cliente = ?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, user);
			
			ResultSet rs = stm.executeQuery();
			
			while(rs.next()) {
				CreditCardBean bean = new CreditCardBean();
				bean.setNumeroCC(rs.getString("NumeroCC"));
				bean.setCircuito(rs.getString("Circuito"));
				bean.setIntestatario(rs.getString("Intestatario"));
				bean.setDataScadenza(rs.getDate("DataScadenza"));
				bean.setCvcCvv(rs.getString("CvcCvv"));
				bean.setCliente(rs.getString("Cliente"));
				
				cc.add(bean);
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
		
		return cc;
	}
}
