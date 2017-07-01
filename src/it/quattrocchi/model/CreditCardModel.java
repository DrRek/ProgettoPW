package it.quattrocchi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.quattrocchi.support.CreditCardBean;
import it.quattrocchi.support.UserBean;


public class CreditCardModel {

	private static final String TABLE_NAME = "quattrocchidb.CartaCredito";

	public void doSave(CreditCardBean creditCard) throws SQLException{

		Connection conn = null;
		PreparedStatement stm = null;

		String query = "INSERT INTO " + TABLE_NAME + " values (?,?,?,?,?,?,?)";

		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, creditCard.getNumeroCC());
			stm.setString(2, creditCard.getIntestatario());
			stm.setString(3, creditCard.getCircuito());
			stm.setDate(4, creditCard.getDataScadenza());
			stm.setString(5, creditCard.getCvcCvv());
			stm.setString(6, creditCard.getCliente().getUser());
			stm.setString(7, creditCard.getStato());

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
	
	public ArrayList<CreditCardBean> doRetrieveByCliente(UserBean user) throws SQLException{
		
		ArrayList<CreditCardBean> cc = new ArrayList<CreditCardBean>();
		
		Connection conn = null;
		PreparedStatement stm  = null;
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE cliente = ? and stato = 'attiva';";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, user.getUser());
			
			ResultSet rs = stm.executeQuery();
			
			while(rs.next()) {
				CreditCardBean bean = new CreditCardBean();
				bean.setNumeroCC(rs.getString("NumeroCC"));
				bean.setCircuito(rs.getString("Circuito"));
				bean.setIntestatario(rs.getString("Intestatario"));
				bean.setDataScadenza(rs.getDate("DataScadenza"));
				bean.setCvcCvv(rs.getString("CvcCvv"));
				bean.setCliente(null);
				
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
	
	public CreditCardBean doRetrieveByKey(String numeroCC) throws SQLException {
		
		Connection conn = null;
		PreparedStatement stm  = null;
		
		CreditCardBean bean = null;
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE numerocc = ?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, numeroCC);
			
			ResultSet rs = stm.executeQuery();
			
			while(rs.next()) {
				bean = new CreditCardBean();
				bean.setNumeroCC(rs.getString("NumeroCC"));
				bean.setIntestatario(rs.getString("Intestatario"));
				bean.setCircuito(rs.getString("Circuito"));
				bean.setDataScadenza(rs.getDate("DataScadenza"));
				bean.setCvcCvv(rs.getString("CvcCvv"));
				//bean.setCliente(rs.getString("Cliente"));
				bean.setStato(rs.getString("Stato"));
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
	
	public void doUpdate(CreditCardBean card) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stm = null;
		
		String query = "update " + TABLE_NAME + " set Intestatario=?, Circuito=?, DataScadenza=?, CvcCvv=?, Cliente=?, Stato=? where NumeroCC=?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, card.getIntestatario());
			stm.setString(2, card.getCircuito());
			stm.setDate(3, card.getDataScadenza());
			stm.setString(4, card.getCvcCvv());
			stm.setString(5, card.getCliente().getUser());
			stm.setString(6,card.getStato());
			stm.setString(7, card.getNumeroCC());

			
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
	
	public void doDelete(String numeroCC) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stm = null;
		String stato = "cancellata";
		String query = "update " + TABLE_NAME + " set Stato=? where NumeroCC=?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, stato);
			stm.setString(2, numeroCC);

			
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
}
