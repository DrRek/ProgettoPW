package it.quattrocchi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import com.mysql.jdbc.Statement;

import it.quattrocchi.support.ArticleBean;

public class ArticleModel {
	
	private static final String TABLE_NAME = "quattrocchidb.articolo";
	
	public void doSave(ArticleBean product) throws SQLException{
		
		Connection conn = null;
		PreparedStatement stm = null;
		
		String query = "INSERT INTO " + TABLE_NAME + " values (?,?,?,?,?,?,?,?,?)";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, product.getNome());
			stm.setString(2, product.getMarca());
			stm.setString(3, product.getTipo());
			stm.setInt(4, product.getNumeroPezziDisponibili());
			stm.setFloat(5, product.getPrezzo());
			stm.setFloat(6,product.getGradazione());
			stm.setString(7, product.getImg1());
			stm.setString(8, product.getImg2());
			stm.setString(9, product.getImg3());
			
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
		
		String query = "update " + TABLE_NAME + " set Tipo=?, NumeroPezziDisponibili=?, Prezzo=?, Gradazione=?, img1=?, img2=?, img3=? where Nome=? and Marca=?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, product.getTipo());
			stm.setInt(2, product.getNumeroPezziDisponibili());
			stm.setFloat(3, product.getPrezzo());
			stm.setFloat(4, product.getGradazione());
			stm.setString(5, product.getImg1());
			stm.setString(6,product.getImg2());
			stm.setString(7, product.getImg3());
			stm.setString(8, product.getNome());
			stm.setString(9, product.getMarca());
			
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
		
		ArticleBean bean = new ArticleBean();
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE nome = ? and marca = ?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, nome);
			stm.setString(2, marca);
			
			ResultSet rs = stm.executeQuery();
			
			while(rs.next()) {
				bean.setNome(rs.getString("nome"));
				bean.setMarca(rs.getString("marca"));
				bean.setTipo(rs.getString("tipo"));
				bean.setNumeroPezziDisponibili(rs.getInt("NumeroPezziDisponibili"));
				bean.setPrezzo(rs.getFloat("prezzo"));
				bean.setGradazione(rs.getFloat("gradazione"));
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
}