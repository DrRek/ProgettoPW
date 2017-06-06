package it.quattrocchi.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import it.quattrocchi.support.ArticleBean;
import it.quattrocchi.support.CartArticle;
import it.quattrocchi.support.OrderBean;


public class CheckoutModel {
	private static final String TABLE_NAME = "quattrocchidb.ordine";
	private static final String TABLE_NAME_USER = "quattrocchidb.esecuzione";
	private static final String TABLE_NAME_CREDIT = "quattrocchidb.utilizzo";
	private static final String TABLE_NAME_ARTICLE = "quattrocchidb.appartenenza";
	
	public void doSave(OrderBean ordine) throws SQLException{
	
		Connection conn;
		PreparedStatement stm;
		String query;
		
		//inserire in ordine
		conn=null; stm=null; query=null;
		query = "INSERT INTO " + TABLE_NAME + " values (?,?,?)";
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, ordine.getCodice());
			stm.setDate(2, ordine.getDataEsecuzione());
			stm.setDouble(3, ordine.getCosto());
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
		
		//inserire in esecuzione
		conn=null; stm=null; query=null;
		query = "INSERT INTO " + TABLE_NAME_USER + " values (?,?)";
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, ordine.getCodice());
			stm.setString(2, ordine.getCliente().getUser());
	
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
		
		//inserire in utilizzo
		conn=null; stm=null; query=null;
		query = "INSERT INTO " + TABLE_NAME_CREDIT + " values (?,?)";
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, ordine.getCodice());
			//verra' cambiato quando ci sara' il bean della carta di credito
			stm.setBigDecimal(2, new BigDecimal(ordine.getCc()));
	
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
		
		//inserire in appartenenza
		for(CartArticle ca : ordine.getOrdinati()){
			conn=null; stm=null; query=null;
			query = "INSERT INTO " + TABLE_NAME_ARTICLE + " values (?,?,?,?)";
			try {
				conn = DriverManagerConnectionPool.getConnection();
				stm = conn.prepareStatement(query);
				stm.setString(1, ordine.getCodice());
				stm.setString(2, ca.getArticle().getNome());
				//da modificare quando ci sara' prescrizione
				stm.setString(3, null);
				stm.setDouble(4, ca.getPrezzo());
	
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
	
	//Questo metodo dovr� essere usato per verificare che i codici scelti automaticamente non siano gi� presenti nel database
	public static boolean isUniqueCod(String codice) {
		
		Connection conn = null;
		PreparedStatement stm  = null;
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE codice = ?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, codice);
			
			ResultSet rs = stm.executeQuery();
			
			if (!rs.next()){
			    return true;
			}
			return false;
		} catch (Exception e){
			e.printStackTrace();
		} 
		return true;
	}

}
