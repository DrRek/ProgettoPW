package it.quattrocchi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import it.quattrocchi.support.ArticleBean;
import it.quattrocchi.support.SingleContactLenseBean;

public class ArticleModel {
	
	private static final String TABLE_NAME = "quattrocchidb.articolo";
	
	public void doSave(ArticleBean art) throws SQLException{
		Connection conn = null;
		PreparedStatement stm = null;
		String query = "Select * from articolo where Nome=? and Marca=?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, art.getNome());
			stm.setString(2, art.getMarca());

			ResultSet rs = stm.executeQuery();
			
			if(rs.next()){ //In questo caso bisogna fare l'update
				rs.close();
				stm.close();
				query = "update articolo set Tipo=?, Prezzo=?, img1=? where Nome=? and Marca=?;";
				stm = conn.prepareStatement(query);
				stm.setString(1, art.getTipo());
				stm.setDouble(2, art.getPrezzo());
				stm.setString(3, art.getImg1());
				stm.setString(4, art.getNome());
				stm.setString(5, art.getMarca());
				stm.executeUpdate();
				stm.close();
				
				if(art.getTipo().equalsIgnoreCase("O")){
					query = "update occhiale set Descrizione=?, Sesso=?, NumeroPezziDisponibili=?, img1=?, img2=? where Nome=? and Marca=?;";
					stm = conn.prepareStatement(query);
					stm.setString(1, art.getDescrizione());
					stm.setString(2, art.getSesso());
					stm.setInt(3, art.getNumeroPezziDisponibili());
					stm.setString(4, art.getImg2());
					stm.setString(5, art.getImg3());
					stm.setString(6, art.getNome());
					stm.setString(7, art.getMarca());
					stm.executeUpdate();
					stm.close();
				} else {
					for(SingleContactLenseBean l : art.getLentine()){
						query = "update lentine set NumeroPezziDisponibili=?, Gradazione=?, Tipologia=?, NumeroPezziNelPacco=?, Raggio=?, Diametro=?, Colore=? where Nome=? and Marca=? and Modello=?;";
						stm = conn.prepareStatement(query);
						stm.setInt(1, l.getDisponibilita());
						stm.setDouble(2, l.getGradazione());
						stm.setString(3, art.getTipologia());
						stm.setInt(4, art.getNumeroPezziNelPacco());
						stm.setDouble(5, l.getRaggio());
						stm.setDouble(6, l.getDiametro());
						stm.setString(7, l.getColore());
						stm.setString(8, art.getNome());
						stm.setString(9, art.getMarca());
						stm.setString(10, l.getModello());
						stm.executeUpdate();
						stm.close();
					}
				}
			} else{ //Se bisogna crearlo
				rs.close();
				stm.close();
				query = "insert into articolo values(?,?,?,?,?);";
				stm = conn.prepareStatement(query);
				stm.setString(1, art.getNome());
				stm.setString(2, art.getMarca());
				stm.setString(3, art.getTipo());
				stm.setDouble(4, art.getPrezzo());
				stm.setString(5, art.getImg1());
				stm.executeUpdate();
				stm.close();
				
				if(art.getTipo().equalsIgnoreCase("O")){
					query = "insert into occhiale values(?,?,?,?,?,?,?);";
					stm = conn.prepareStatement(query);
					stm.setString(1, art.getNome());
					stm.setString(2, art.getMarca());
					stm.setString(3, art.getDescrizione());
					stm.setString(4, art.getSesso());
					stm.setInt(5, art.getNumeroPezziDisponibili());
					stm.setString(6, art.getImg2());
					stm.setString(7, art.getImg3());
					stm.executeUpdate();
					stm.close();
				} else {
					for(SingleContactLenseBean l : art.getLentine()){
						boolean isUnique=false;
						while(!isUnique){
							String toTest = UUID.randomUUID().toString().substring(0, 20);
							query="select * from lentine where modello=?;";
							stm = conn.prepareStatement(query);
							stm.setString(1, toTest);
							rs = stm.executeQuery();
							if(!rs.next()){
								isUnique=true;
								l.setModello(toTest);
							}
							rs.close();
							stm.close();
						}
						query = "insert into lentine values(?,?,?,?,?,?,?,?,?,?);";
						stm = conn.prepareStatement(query);
						stm.setString(1, art.getNome());
						stm.setString(2, art.getMarca());
						stm.setString(3, l.getModello());
						stm.setInt(4, l.getDisponibilita());
						stm.setDouble(5, l.getGradazione());
						stm.setString(6, art.getTipologia());
						stm.setInt(7, art.getNumeroPezziNelPacco());
						stm.setDouble(8, l.getRaggio());
						stm.setDouble(9, l.getDiametro());
						stm.setString(10, l.getColore());
						stm.executeUpdate();
						stm.close();
					}
				}
			}
			
		} finally {
			try {
				if (stm != null)
					stm.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(conn);
			}
		}
	}
	
	public ArticleBean doRetrieveByKey(String nome, String marca) throws SQLException{
		Connection conn = null;
		PreparedStatement stm = null;
		ArticleBean bean = new ArticleBean();
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE Nome = ? AND Marca = ?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, nome);
			stm.setString(2, marca);

			ResultSet rs = stm.executeQuery();
			
			while (rs.next()) {
				bean.setNome(rs.getString("Nome"));
				bean.setMarca(rs.getString("Marca"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzo(rs.getDouble("Prezzo"));
				bean.setImg1(rs.getString("Img1"));
			}
			stm.close();
			rs.close();
			
			if(bean.getTipo().equalsIgnoreCase("O")){
				query = "SELECT * FROM occhiale WHERE Nome = ? AND Marca = ?;";
				stm = conn.prepareStatement(query);
				stm.setString(1, nome);
				stm.setString(2, marca);
				rs = stm.executeQuery();
				while (rs.next()) {
					bean.setDescrizione(rs.getString("Descrizione"));
					bean.setSesso(rs.getString("Sesso"));
					bean.setNumeroPezziDisponibili(rs.getInt("NumeroPezziDisponibili"));
					bean.setImg2(rs.getString("Img1"));
					bean.setImg3(rs.getString("Img2"));
				}
				stm.close();
				rs.close();
			} else {
				query = "SELECT * FROM lentine WHERE Nome = ? AND Marca = ?;";
				stm = conn.prepareStatement(query);
				stm.setString(1, nome);
				stm.setString(2, marca);
				rs.close();
				rs = stm.executeQuery();
				boolean isFirst=true;
				while (rs.next()) {
					if(isFirst){
						bean.setNumeroPezziNelPacco(rs.getInt("NumeroPezziNelPacco"));
						bean.setRaggio(rs.getDouble("Raggio"));
						bean.setDiametro(rs.getDouble("Diametro"));
						bean.setLentine(rs.getString("Modello"), rs.getDouble("Gradazione"), rs.getDouble("Raggio"), rs.getDouble("Diametro"), rs.getInt("NumeroPezziDisponibili"), rs.getString("Colore"), rs.getString("tipologia"));
						isFirst=false;
					} else {
						bean.setLentine(rs.getString("Modello"), rs.getDouble("Gradazione"), rs.getDouble("Raggio"), rs.getDouble("Diametro"), rs.getInt("NumeroPezziDisponibili"), rs.getString("Colore"), rs.getString("tipologia"));
					}
				}
				stm.close();
				rs.close();
			}

			conn.commit();
		} finally {
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
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		Collection<ArticleBean> products = new LinkedList<ArticleBean>();

		String selectSQL = "SELECT * FROM " + ArticleModel.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}
		
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			preparedStatement = conn.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ArticleBean bean = new ArticleBean();
				
				bean.setNome(rs.getString("Nome"));
				bean.setMarca(rs.getString("Marca"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzo(rs.getFloat("Prezzo"));
				bean.setImg1(rs.getString("Img1"));
				products.add(bean);
			}
			conn.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(conn);
			}
		}
		return products;
	}

	public Collection<ArticleBean> doRetrieve(String daCercare) throws SQLException{
		Connection conn = null;
		PreparedStatement stm = null;
		Collection<ArticleBean> products = new LinkedList<ArticleBean>();
		daCercare = "%"+daCercare+"%";
		String sql = "select * from articolo left join occhiale on articolo.nome = occhiale.nome and articolo.marca = occhiale.marca where (articolo.Nome LIKE ?) or (occhiale.Descrizione LIKE ?) or (articolo.Marca LIKE ?)";

		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(sql);
			stm.setString(1, daCercare);
			stm.setString(2, daCercare);
			stm.setString(3, daCercare);
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				ArticleBean bean = new ArticleBean();
				
				bean.setNome(rs.getString("Nome"));
				bean.setMarca(rs.getString("Marca"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzo(rs.getFloat("Prezzo"));
				bean.setImg1(rs.getString("Img1"));
				products.add(bean);
			}
			conn.commit();
		} finally {
			try {
				if (stm != null)
					stm.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(conn);
			}
		}
			
		return products;
	}

	public Collection<ArticleBean> doRetrieveGlasses(String daCercare, String marca, String prezzoMin, String prezzoMax, String sesso, String colore, String sort) throws SQLException{
		Connection conn = null;
		PreparedStatement stm = null;
		Collection<ArticleBean> products = new LinkedList<ArticleBean>();
		String sql = "select * from articolo right join occhiale on articolo.nome=occhiale.nome and articolo.marca=occhiale.marca";
		sql+= " where ((articolo.Nome LIKE '%"+daCercare+"%') or (occhiale.Descrizione LIKE '%"+daCercare+"%') or (articolo.Marca LIKE '%"+daCercare+"%'))";
		if(marca!=null&&!marca.equalsIgnoreCase("")){
			sql+=" and articolo.marca='"+marca+"'";
		}
		if(prezzoMin!=null&&!prezzoMin.equalsIgnoreCase("0")&&!prezzoMin.equalsIgnoreCase("")){
			sql+=" and articolo.prezzo>="+prezzoMin;
		}
		if(prezzoMax!=null&&!prezzoMax.equalsIgnoreCase("Max")&&!prezzoMax.equalsIgnoreCase("")){
			sql+=" and articolo.prezzo<="+prezzoMax;
		}
		if(sesso!=null&&!sesso.equalsIgnoreCase("")&&!sesso.equalsIgnoreCase("any")){
			sql+=" and (occhiale.sesso=U or occhiale.sesso='"+sesso+"')";
		}
		if(colore!=null&&!colore.equalsIgnoreCase("")){ //cerca nella descrizione riferimenti al colore
			sql+=" and occhiale.descrizione='%"+colore+"'%";
		}
		if(sort!=null&&!sort.equalsIgnoreCase("")){
			sql+=" order by '%"+sort+"'";
		}
		sql+=";";
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				ArticleBean bean = new ArticleBean();
				
				bean.setNome(rs.getString("Nome"));
				bean.setMarca(rs.getString("Marca"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzo(rs.getFloat("Prezzo"));
				bean.setImg1(rs.getString("Img1"));
				products.add(bean);
			}
			conn.commit();
		} finally {
			try {
				if (stm != null)
					stm.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(conn);
			}
		}
			
		return products;
	}

	public Object doRetrieveContactLenses(String daCercare, String marca, String prezzoMin, String prezzoMax, String gradazione, String tipologia, String raggio, String diametro, String colore, String sort) throws SQLException{
		Connection conn = null;
		PreparedStatement stm = null;
		Collection<ArticleBean> products = new LinkedList<ArticleBean>();
		String sql = "select distinct articolo.nome, articolo.marca, tipo, prezzo, img1  from articolo right join lentine on articolo.nome=lentine.nome and articolo.marca=lentine.marca";
		if(daCercare.equalsIgnoreCase("null"))daCercare=null;
		sql+= " where ((articolo.Nome LIKE '%"+daCercare+"%') or (articolo.Marca LIKE '%"+daCercare+"%'))";
		if(marca!=null&&!marca.equalsIgnoreCase("")){
			sql+=" and articolo.marca='"+marca+"'";
		}
		if(prezzoMin!=null&&!prezzoMin.equalsIgnoreCase("0")&&!prezzoMin.equalsIgnoreCase("")){
			sql+=" and articolo.prezzo>="+prezzoMin;
		}
		if(prezzoMax!=null&&!prezzoMax.equalsIgnoreCase("Max")&&!prezzoMax.equalsIgnoreCase("")){
			sql+=" and articolo.prezzo<="+prezzoMax;
		}
		if(gradazione!=null&&!gradazione.equalsIgnoreCase("")){
			sql+=" and lentine.gradazione="+gradazione;
		}
		if(tipologia!=null&&!tipologia.equalsIgnoreCase("")){
			sql+=" and lentine.gradazione='"+tipologia+"'";
		}
		if(raggio!=null&&!raggio.equalsIgnoreCase("")){
			sql+=" and lentine.raggio="+raggio;
		}
		if(diametro!=null&&!diametro.equalsIgnoreCase("")){
			sql+=" and lentine.diametro="+diametro;
		}
		if(colore!=null&&!colore.equalsIgnoreCase("")){
			sql+=" and lentine.colore='"+colore+"'";
		}
		if(sort!=null&&!sort.equalsIgnoreCase("")){
			sql+=" order by '%"+sort+"'";
		}
		sql+=";";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				ArticleBean bean = new ArticleBean();
				
				bean.setNome(rs.getString("Nome"));
				bean.setMarca(rs.getString("Marca"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzo(rs.getFloat("Prezzo"));
				bean.setImg1(rs.getString("Img1"));
				products.add(bean);
			}
			conn.commit();
		} finally {
			try {
				if (stm != null)
					stm.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(conn);
			}
		}
			
		return products;
	}
}
