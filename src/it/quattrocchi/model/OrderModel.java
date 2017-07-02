package it.quattrocchi.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import it.quattrocchi.support.CartArticle;
import it.quattrocchi.support.OrderBean;
import it.quattrocchi.support.UserBean;


public class OrderModel {
	private static ArticleModel aModel = new ArticleModel();
	private static UserModel uModel = new UserModel();
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

	
	public ArrayList<OrderBean> doRetrieveByCliente(UserBean user) throws SQLException {
		ArrayList<OrderBean> o = new ArrayList<OrderBean>();
		ArrayList<CartArticle> ordinati = new ArrayList<CartArticle>();
		String codPrecedente = "";
		OrderBean bean = null;
		
		Connection conn = null;
		PreparedStatement stm  = null;
		
		String query = "SELECT * FROM " 
				+ "ORDINE O JOIN APPARTENENZA A ON O.CODICE = A.ORDINE"
				+ " WHERE CLIENTE = ?"
				+ " ORDER BY O.CODICE;" ;
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, user.getUser());
			
			ResultSet rs = stm.executeQuery();
			
			while(rs.next()) {
				
				if(!(codPrecedente.equals(rs.getString("o.Codice")))){
					codPrecedente = rs.getString("o.Codice");
					if(bean!=null){
						bean.setOrdinati(ordinati);
						ordinati = new ArrayList<CartArticle>();
						o.add(bean);
					}
					bean = new OrderBean();
					bean.setCodice(rs.getString("o.Codice"));
					bean.setCc(rs.getString("o.CartaCredito"));
					bean.setCosto(rs.getDouble("o.Costo"));
					bean.setDataEsecuzione(rs.getDate("o.DataEsecuzione"));
					bean.setCliente(uModel.doRetrieveByKey(rs.getString("o.Cliente")));
					CartArticle aBean = null;
					if(aModel.doRetrieveGlasses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo")) == null)
						aBean = new CartArticle(aModel.doRetrieveContactLenses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo"), rs.getDouble("a.Gradazione")), rs.getInt("a.Quantita"));
					else
						aBean = new CartArticle(aModel.doRetrieveGlasses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo")), rs.getInt("a.Quantita"));
					ordinati.add(aBean);
				}
				
				else{	
					CartArticle aBean = null;
					if(aModel.doRetrieveGlasses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo")) == null)
						aBean = new CartArticle(aModel.doRetrieveContactLenses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo"), rs.getDouble("a.Gradazione")), rs.getInt("a.Quantita"));
					else
						aBean = new CartArticle(aModel.doRetrieveGlasses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo")), rs.getInt("a.Quantita"));
					ordinati.add(aBean);
				}
				
			} 
			if(ordinati.size() != 0){
				bean.setOrdinati(ordinati);
				o.add(bean);
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

		
		return o;
	}



	public ArrayList<OrderBean> doRetrieveAll() throws SQLException {
		ArrayList<OrderBean> o = new ArrayList<OrderBean>();
		ArrayList<CartArticle> ordinati = new ArrayList<CartArticle>();
		String codPrecedente = "";
		OrderBean bean = null;
		
		Connection conn = null;
		PreparedStatement stm  = null;
		
		String query = "SELECT * FROM" 
				+ " ORDINE O JOIN APPARTENENZA A ON O.CODICE = A.ORDINE"
				+ " ORDER BY O.DATAESECUZIONE;" ;
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			
			ResultSet rs = stm.executeQuery();
			
			while(rs.next()) {
				
				if(!(codPrecedente.equals(rs.getString("o.Codice")))){
					codPrecedente = rs.getString("o.Codice");
					if(bean!=null){
						bean.setOrdinati(ordinati);
						ordinati = new ArrayList<CartArticle>();
						o.add(bean);
					}
					bean = new OrderBean();
					bean.setCodice(rs.getString("o.Codice"));
					bean.setCc(rs.getString("o.CartaCredito"));
					bean.setCosto(rs.getDouble("o.Costo"));
					bean.setDataEsecuzione(rs.getDate("o.DataEsecuzione"));
					bean.setCliente(uModel.doRetrieveByKey(rs.getString("o.Cliente")));
					CartArticle aBean = null;
					if(aModel.doRetrieveGlasses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo")) == null)
						aBean = new CartArticle(aModel.doRetrieveContactLenses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo"), rs.getDouble("a.Gradazione")), rs.getInt("a.Quantita"));
					else
						aBean = new CartArticle(aModel.doRetrieveGlasses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo")), rs.getInt("a.Quantita"));
					ordinati.add(aBean);
				}
				
				else{
					CartArticle aBean = null;
					if(aModel.doRetrieveGlasses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo")) == null)
						aBean = new CartArticle(aModel.doRetrieveContactLenses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo"), rs.getDouble("a.Gradazione")), rs.getInt("a.Quantita"));
					else
						aBean = new CartArticle(aModel.doRetrieveGlasses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo")), rs.getInt("a.Quantita"));
					ordinati.add(aBean);
				}
				
			}
			if(bean!=null){
				bean.setOrdinati(ordinati);
				o.add(bean);
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

		return o;
	}
	
	public OrderBean doRetrieveByKey(String codice) throws SQLException {
		OrderBean o = new OrderBean();
		ArrayList<CartArticle> ordinati = new ArrayList<CartArticle>();
		String codPrecedente = "";
		
		Connection conn = null;
		PreparedStatement stm  = null;
		
		String query = "SELECT * FROM " 
				+ "ORDINE O JOIN APPARTENENZA A ON O.CODICE = A.ORDINE"
				+ " WHERE O.CODICE = ?" ;
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, codice);
			
			ResultSet rs = stm.executeQuery();
			
			while(rs.next()) {
				
				if(!(codPrecedente.equals(rs.getString("o.Codice")))){
					codPrecedente = rs.getString("o.Codice");
					o.setCodice(rs.getString("o.Codice"));
					o.setCc(rs.getString("o.CartaCredito"));
					o.setCosto(rs.getDouble("o.Costo"));
					o.setDataEsecuzione(rs.getDate("o.DataEsecuzione"));
					o.setCliente(uModel.doRetrieveByKey(rs.getString("o.Cliente")));
					CartArticle aBean = null;
					if(aModel.doRetrieveGlasses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo")) == null)
						aBean = new CartArticle(aModel.doRetrieveContactLenses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo"), rs.getDouble("a.Gradazione")), rs.getInt("a.Quantita"));
					else
						aBean = new CartArticle(aModel.doRetrieveGlasses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo")), rs.getInt("a.Quantita"));
					ordinati.add(aBean);
				}
				
				else{	
					CartArticle aBean = null;
					if(aModel.doRetrieveGlasses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo")) == null)
						aBean = new CartArticle(aModel.doRetrieveContactLenses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo"), rs.getDouble("a.Gradazione")), rs.getInt("a.Quantita"));
					else
						aBean = new CartArticle(aModel.doRetrieveGlasses(rs.getString("a.NomeArticolo"), rs.getString("a.MarcaArticolo")), rs.getInt("a.Quantita"));
					ordinati.add(aBean);
				}
				
			} 
			o.setOrdinati(ordinati);
			
		}
		
		finally {
			try {
				if (stm != null)
					stm.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(conn);
			}
		}

		
		return o;
	}

}
