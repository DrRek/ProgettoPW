package it.quattrocchi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import it.quattrocchi.support.ArticleBean;
import it.quattrocchi.support.PromotionBean;

public class PromotionModel {
	
	static ArticleModel model = new ArticleModel();
	
	public void doSave(PromotionBean pro) throws SQLException{
		boolean newP=false;
		Connection conn = null;
		PreparedStatement stm = null;
	
		String query = "SELECT * FROM Promozione WHERE nome = ?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, pro.getNome());
			
			ResultSet rs = stm.executeQuery();
			if (!rs.next()){
			    newP = true;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		if(newP){
			query = "INSERT INTO Promozione values (?,?,?,?,?,?,?)";
			try {
				conn = DriverManagerConnectionPool.getConnection();
				stm = conn.prepareStatement(query);
				stm.setString(1, pro.getNome());
				stm.setString(2, pro.getDescrizione());
				stm.setDouble(3, pro.getSconto());
				stm.setString(4, pro.getTipo());
				stm.setDate(5, pro.getDataInizio());
				stm.setDate(6, pro.getDataFine());
				stm.setBoolean(7,pro.isCumulabile());
		
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
			for(ArticleBean ab : pro.getValidi()){
				query = "INSERT INTO Validita values (?,?,?)";
				try {
					conn = DriverManagerConnectionPool.getConnection();
					stm = conn.prepareStatement(query);
					stm.setString(1, pro.getNome());
					stm.setString(2, ab.getNome());
					stm.setString(3, ab.getMarca());
			
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
		}else{
			query = "update promozione set Descrizione=?, Sconto=?, Tipo=?, DataInizio=?, DataFine=?, Cumulabile=? where Nome=?";
			try {
				conn.close();
				stm.close();
				conn = DriverManagerConnectionPool.getConnection();
				stm = conn.prepareStatement(query);
				stm.setString(1, pro.getDescrizione());
				stm.setDouble(2, pro.getSconto());
				stm.setString(3, pro.getTipo());
				stm.setDate(4, pro.getDataInizio());
				stm.setDate(5, pro.getDataFine());
				stm.setBoolean(6,pro.isCumulabile());
				stm.setString(7, pro.getNome());
		
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
			for(ArticleBean ab : pro.getValidi()){
				query = "SELECT * FROM Validita WHERE promozione = ? and nomeArticolo = ? and marcaArticolo = ?;";
				conn = DriverManagerConnectionPool.getConnection();
				stm = conn.prepareStatement(query);
				stm.setString(1, pro.getNome());
				stm.setString(2, ab.getNome());
				stm.setString(3, ab.getMarca());
				
				ResultSet rs = stm.executeQuery();
				newP=false;
				if (!rs.next()){
				    newP = true;
				}
				if(newP){
					query = "INSERT INTO Validita values (?,?,?)";
					try {
						conn = DriverManagerConnectionPool.getConnection();
						stm = conn.prepareStatement(query);
						stm.setString(1, pro.getNome());
						stm.setString(2, ab.getNome());
						stm.setString(3, ab.getMarca());
				
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
	}

	public ArrayList<PromotionBean> doRetriveAllValid() throws SQLException {
		Connection conn = null;
		PreparedStatement stm  = null;

		PromotionBean bean = null;
		ArrayList<PromotionBean> toReturn = new ArrayList<PromotionBean>();
		String query = "SELECT * FROM promozione WHERE DataFine >= ?;";

		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date data = new Date();
			stm.setString(1, sdf.format(data));

			ResultSet rs = stm.executeQuery();
					
			while(rs.next()) {
				bean = new PromotionBean();
				bean.setNome(rs.getString("nome"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setSconto(rs.getDouble("sconto"));
				bean.setTipo(rs.getString("tipo"));
				bean.setDataInizio(rs.getDate("dataInizio"));
				bean.setDataFine(rs.getDate("dataFine"));
				bean.setCumulabile(rs.getBoolean("cumulabile"));
				
				query = "SELECT * FROM validita WHERE promozione = ?;";
				stm.setString(1, bean.getNome());
				stm = conn.prepareStatement(query);
				stm.setString(1, bean.getNome());
				ResultSet rs1 = stm.executeQuery();
				while(rs1.next()){
					ArticleBean toadd = new ArticleBean();
					toadd.setNome(rs1.getString("NomeArticolo"));
					toadd.setMarca(rs1.getString("MarcaArticolo"));
					bean.addToValidi(toadd);
				}
				toReturn.add(bean);
			}
			stm.close();
			rs.close();
		}
		finally {
			try {
				if (stm != null)
					stm.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(conn);
			}
		}

		return toReturn;
	}

	public PromotionBean doRetriveByKey(String name) throws SQLException {
		Connection conn = null;
		PreparedStatement stm  = null;

		PromotionBean bean = null;
		String query = "SELECT * FROM promozione WHERE nome = ?;";

		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, name);

			ResultSet rs = stm.executeQuery();
					
			if(rs.next()) {
				bean = new PromotionBean();
				bean.setNome(name);
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setSconto(rs.getDouble("sconto"));
				bean.setTipo(rs.getString("tipo"));
				bean.setDataInizio(rs.getDate("dataInizio"));
				bean.setDataFine(rs.getDate("dataFine"));
				bean.setCumulabile(rs.getBoolean("cumulabile"));
				
				query = "SELECT * FROM validita WHERE promozione = ?;";
				stm.setString(1, bean.getNome());
				stm = conn.prepareStatement(query);
				stm.setString(1, bean.getNome());
				ResultSet rs1 = stm.executeQuery();
				while(rs1.next()){
					ArticleBean toadd = new ArticleBean();
					toadd.setNome(rs1.getString("NomeArticolo"));
					toadd.setMarca(rs1.getString("MarcaArticolo"));
					bean.addToValidi(toadd);
				}
			}
			stm.close();
			rs.close();
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
}
