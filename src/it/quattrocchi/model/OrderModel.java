package it.quattrocchi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.quattrocchi.support.CartArticle;
import it.quattrocchi.support.OrderBean;

public class OrderModel {

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
		query = "INSERT INTO " + TABLE_NAME + " values (?,?,?,?)";
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, ordine.getCodice());
			stm.setDate(2, ordine.getDataEsecuzione());
			stm.setDouble(3, ordine.getCosto());
			stm.setString(4, ordine.getCc());

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
			stm.setString(2, ordine.getCc());

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
	
}
