package it.quattrocchi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import it.quattrocchi.DriverManagerConnectionPool;

public class ArticleModel {
	
	private static final String TABLE_NAME = "Articolo";
	
	public void doSave(ArticleBean product) throws SQLException{
		
		Connection conn = null;
		PreparedStatement stm = null;
		
		String query = "INSERT INTO" + TABLE_NAME + "(?,?,?,?,?,?,?,?,?)";
		
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

	public boolean doDelete(int code) throws SQLException{
		return false;
	}

	public ArticleBean doRetrieveByKey(String nome) throws SQLException{
		return null;
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
