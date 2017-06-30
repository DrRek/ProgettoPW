package it.quattrocchi.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.quattrocchi.support.CartArticle;
import it.quattrocchi.support.OrderBean;


public class OrderModel {
	private static final String TABLE_NAME = "quattrocchidb.ordine";
	private static final String TABLE_NAME_APPARTENENZA = "quattrocchidb.appartenenza";
	
	public void doSave(OrderBean ordine) throws SQLException{
	
		Connection conn=null;
		PreparedStatement stm=null;
		String query=null;
		
		//inserire in ordine
		conn=null; stm=null; query=null;
		query = "INSERT INTO " + TABLE_NAME + " values (?,?,?,?,?)";
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, ordine.getCodice());
			stm.setDate(2, ordine.getDataEsecuzione());
			stm.setDouble(3, ordine.getCosto());
			//verra' cambiato quando ci sara' il bean della carta di credito
			stm.setBigDecimal(4, new BigDecimal(ordine.getCc()));
			stm.setString(5, ordine.getCliente().getUser());
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
			query = "INSERT INTO " + TABLE_NAME_APPARTENENZA + " values (?,?,?,?,?,?,?)";
			try {
				conn = DriverManagerConnectionPool.getConnection();
				stm = conn.prepareStatement(query);
				stm.setString(1, ordine.getCodice());
				stm.setString(2, ca.getArticle().getNome());
				stm.setString(3, ca.getArticle().getMarca());
				if(ca.getPrescrizione()!=null){
					stm.setString(4, ca.getPrescrizione().toString());
				}else{
					stm.setString(4, null);
				}
				stm.setDouble(5, ca.getPrezzo());
				if(ca.getGradazione()!=null){
					stm.setDouble(6, ca.getGradazione());
				}else{
					stm.setString(6, null);
				}
				stm.setInt(7, ca.getQuantity());
	
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
	
	//Questo metodo dovrà essere usato per verificare che i codici scelti automaticamente non siano già presenti nel database
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
