package it.quattrocchi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class ArticleModel {
	
	private static final String TABLE_NAME = "Articolo";
	
	public void doSave(ArticleBean product) throws SQLException{
		
	}

	public boolean doDelete(int code) throws SQLException{
		return false;
	}

	public ArticleBean doRetrieveByKey(int code) throws SQLException{
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
			connection = DriverMaagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ArticleBean bean = new ArticleBean();

				bean.setCodice(rs.getInt("CODE"));
				bean.setTipo(rs.getString("NAME"));
				bean.setMarca(rs.getString("DESCRIPTION"));
				bean.setPrezzo(rs.getInt("PRICE"));
				bean.setGradazione(rs.getInt("QUANTITY"));
				bean.setNumeroPezziDisponibili(rs.getInt("PRICE"));
				bean.setImg1(rs.getInt("QUANTITY"));
				bean.setImg2(rs.getInt("QUANTITY"));
				bean.setImg3(rs.getInt("QUANTITY"));
				products.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverMaagerConnectionPool.releaseConnection(connection);
			}
		}
		return products;
	}
}
