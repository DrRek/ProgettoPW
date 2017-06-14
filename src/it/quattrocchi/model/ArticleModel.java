package it.quattrocchi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import it.quattrocchi.support.ArticleBean;

public class ArticleModel {
	
	private static final String TABLE_NAME = "quattrocchidb.articolo";
	
	public void doSave(ArticleBean product) throws SQLException{
		
		Connection conn = null;
		PreparedStatement stm = null;
		
		String query = "INSERT INTO " + TABLE_NAME + " values (?,?,?,?,?,?,?,?)";
		
		try {
			System.out.println("gg");
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, product.getNome());
			stm.setString(2, product.getMarca());
			stm.setString(3, product.getTipo());
			stm.setInt(4, product.getNumeroPezziDisponibili());
			stm.setFloat(5, product.getPrezzo());
			stm.setString(6, product.getImg1());
			stm.setString(7, product.getImg2());
			stm.setString(8, product.getImg3());
			
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

	public void doUpdate(ArticleBean product) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stm = null;
		
		String query = "update " + TABLE_NAME + " set Tipo=?, NumeroPezziDisponibili=?, Prezzo=?, img1=?, img2=?, img3=? where Nome=? and Marca=?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, product.getTipo());
			stm.setInt(2, product.getNumeroPezziDisponibili());
			stm.setFloat(3, product.getPrezzo());
			stm.setString(4, product.getImg1());
			stm.setString(5,product.getImg2());
			stm.setString(6, product.getImg3());
			stm.setString(7, product.getNome());
			stm.setString(8, product.getMarca());
			
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
	
	public boolean doDelete(String nome, String marca) throws SQLException{
		
		Connection conn = null;
		PreparedStatement stm = null;
		
		String query = "DELETE FROM " + TABLE_NAME + " WHERE Nome = ? AND Marca = ?;";
		
		int result = 0;
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, nome);
			stm.setString(2, marca);
			
			result = stm.executeUpdate();
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
		return (result != 0);
	}

	public ArticleBean doRetrieveByKey(String nome, String marca) throws SQLException{
		
		Connection conn = null;
		PreparedStatement stm  = null;
		
		ArticleBean bean = null;
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE nome = ? and marca = ?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, nome);
			stm.setString(2, marca);
			
			ResultSet rs = stm.executeQuery();
			
			while(rs.next()) {
				bean = new ArticleBean();
				bean.setNome(rs.getString("nome"));
				bean.setMarca(rs.getString("marca"));
				bean.setTipo(rs.getString("tipo"));
				bean.setNumeroPezziDisponibili(rs.getInt("NumeroPezziDisponibili"));
				bean.setPrezzo(rs.getFloat("prezzo"));
				bean.setImg1(rs.getString("img1"));
				bean.setImg2(rs.getString("img2"));
				bean.setImg3(rs.getString("img3"));	
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
	
	public Collection<ArticleBean> doRetrieveAll(String order) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<ArticleBean> products = new LinkedList<ArticleBean>();

		String selectSQL = "SELECT * FROM " + ArticleModel.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}
		
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ArticleBean bean = new ArticleBean();
				
				bean.setNome(rs.getString("Nome"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setMarca(rs.getString("Marca"));
				bean.setPrezzo(rs.getFloat("Prezzo"));
				bean.setGradazione(rs.getFloat("Gradazione"));
				bean.setNumeroPezziDisponibili(rs.getInt("NumeroPezziDisponibili"));
				bean.setImg1(rs.getString("Img1"));
				bean.setImg2(rs.getString("Img2"));
				bean.setImg3(rs.getString("Img3"));
				products.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return products;
	}

	public Collection<ArticleBean> doRetrieve(String search, String tipo, String marca, double da, double a, String order) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<ArticleBean> products = new LinkedList<ArticleBean>();

		boolean isFirst=true;
		String selectSQL = "SELECT * FROM " + ArticleModel.TABLE_NAME;
		if(search!=null && !search.equalsIgnoreCase("")){
			if(isFirst){
				selectSQL+=" WHERE";
				isFirst=false;
			} else {
				selectSQL+=" AND";
			}
			selectSQL+=" Nome LIKE '%"+search+"%'";
		}
		if(tipo!=null && !tipo.equalsIgnoreCase("")){
			if(isFirst){
				selectSQL+=" WHERE";
				isFirst=false;
			} else {
				selectSQL+=" AND";
			}
			selectSQL+=" Tipo = '"+tipo+"'";
		}
		if(marca!=null && !marca.equalsIgnoreCase("")){
			if(isFirst){
				selectSQL+=" WHERE";
				isFirst=false;
			} else {
				selectSQL+=" AND";
			}
			selectSQL+=" Marca = '"+marca+"'";
		}
		if(da!= 0){
			if(isFirst){
				selectSQL+=" WHERE";
				isFirst=false;
			} else {
				selectSQL+=" AND";
			}
			selectSQL+=" Prezzo > "+da;
		}
		if(a!= 0){
			if(isFirst){
				selectSQL+=" WHERE";
				isFirst=false;
			} else {
				selectSQL+=" AND";
			}
			selectSQL+=" Prezzo < "+a;
		}
		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}
		selectSQL+=";";
		
		
		try{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ArticleBean bean = new ArticleBean();
				
				bean.setNome(rs.getString("Nome"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setMarca(rs.getString("Marca"));
				bean.setPrezzo(rs.getFloat("Prezzo"));
				bean.setNumeroPezziDisponibili(rs.getInt("NumeroPezziDisponibili"));
				bean.setImg1(rs.getString("Img1"));
				bean.setImg2(rs.getString("Img2"));
				bean.setImg3(rs.getString("Img3"));
				products.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return products;
	}
}
