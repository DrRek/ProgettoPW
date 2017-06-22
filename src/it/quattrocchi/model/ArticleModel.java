package it.quattrocchi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import it.quattrocchi.support.ArticleBean;
import it.quattrocchi.support.ContactLensesBean;
import it.quattrocchi.support.GlassesBean;

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
					GlassesBean bean = (GlassesBean) art;
					query = "update occhiale set Descrizione=?, Sesso=?, NumeroPezziDisponibili=?, img1=?, img2=? where Nome=? and Marca=?;";
					stm = conn.prepareStatement(query);
					stm.setString(1, bean.getDescrizione());
					stm.setString(2, bean.getSesso());
					stm.setInt(3, bean.getDisponibilita());
					stm.setString(4, bean.getImg2());
					stm.setString(5, bean.getImg3());
					stm.setString(6, bean.getNome());
					stm.setString(7, bean.getMarca());
					stm.executeUpdate();
					stm.close();
				} else {
					ContactLensesBean bean = (ContactLensesBean) art;
					query = "update lentine set Tipologia=?, NumeroPezziNelPacco=?, Raggio=?, Diametro=?, Colore=? where Nome=? and Marca=?;";
					stm = conn.prepareStatement(query);
					stm.setString(1, bean.getTipologia());
					stm.setInt(2, bean.getNumeroPezziNelPacco());
					stm.setDouble(3, bean.getRaggio());
					stm.setDouble(4, bean.getDiametro());
					stm.setString(5, bean.getColore());
					stm.setString(6, bean.getNome());
					stm.setString(7, bean.getMarca());
					stm.executeUpdate();
					stm.close();
					query = "update disponibilita set NumeroPezziDisponibili=? where Nome=? and Marca=? and Gradazione=?;";
					stm = conn.prepareStatement(query);
					stm.setInt(1, bean.getDisponibilita());
					stm.setString(2, bean.getNome());
					stm.setString(3, bean.getMarca());
					stm.setDouble(4, bean.getGradazione());
					stm.executeUpdate();
					stm.close();
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
					GlassesBean bean = (GlassesBean) art;
					query = "insert into occhiale values(?,?,?,?,?,?,?);";
					stm = conn.prepareStatement(query);
					stm.setString(1, bean.getNome());
					stm.setString(2, bean.getMarca());
					stm.setString(3, bean.getDescrizione());
					stm.setString(4, bean.getSesso());
					stm.setInt(5, bean.getDisponibilita());
					stm.setString(6, bean.getImg2());
					stm.setString(7, bean.getImg3());
					stm.executeUpdate();
					stm.close();
				} else {
					ContactLensesBean bean = (ContactLensesBean) art;
					query = "insert into lentine values(?,?,?,?,?,?,?);";
					stm = conn.prepareStatement(query);
					stm.setString(1, bean.getNome());
					stm.setString(2, bean.getMarca());
					stm.setString(3, bean.getTipologia());
					stm.setInt(4, bean.getNumeroPezziNelPacco());
					stm.setDouble(5, bean.getRaggio());
					stm.setDouble(6, bean.getDiametro());
					stm.setString(7, bean.getColore());
					stm.executeUpdate();
					stm.close();

					query = "insert into disponibilita values(?,?,?,?);";
					stm = conn.prepareStatement(query);
					stm.setString(1, bean.getNome());
					stm.setString(2, bean.getMarca());
					stm.setInt(3, bean.getDisponibilita());
					stm.setDouble(4, bean.getGradazione());
					stm.executeUpdate();
					stm.close();
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

	public ArticleBean doRetrieveGlasses(String nome, String marca) throws SQLException{
		Connection conn = null;
		PreparedStatement stm = null;
		GlassesBean bean = null;

		String query = "SELECT * FROM " + TABLE_NAME + " WHERE Nome = ? AND Marca = ?;";

		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, nome);
			stm.setString(2, marca);

			ResultSet rs = stm.executeQuery();

			if(rs.next()) {
				bean = new GlassesBean();
				bean.setNome(rs.getString("Nome"));
				bean.setMarca(rs.getString("Marca"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzo(rs.getDouble("Prezzo"));
				bean.setImg1(rs.getString("Img1"));

				stm.close();
				rs.close();

				query = "SELECT * FROM occhiale WHERE Nome = ? AND Marca = ?;";
				stm = conn.prepareStatement(query);
				stm.setString(1, nome);
				stm.setString(2, marca);
				rs = stm.executeQuery();
				if (rs.next()) {
					bean.setDescrizione(rs.getString("Descrizione"));
					bean.setSesso(rs.getString("Sesso"));
					bean.setDisponibilita(rs.getInt("NumeroPezziDisponibili"));
					bean.setImg2(rs.getString("Img1"));
					bean.setImg3(rs.getString("Img2"));
				}
			}
			stm.close();
			rs.close();

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

	public ArticleBean doRetrieveContactLenses(String nome, String marca, double gradazione) throws SQLException{
		Connection conn = null;
		PreparedStatement stm = null;
		ContactLensesBean bean = null;

		String query = "SELECT * FROM " + TABLE_NAME + " WHERE Nome = ? AND Marca = ?;";

		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, nome);
			stm.setString(2, marca);

			ResultSet rs = stm.executeQuery();

			if(rs.next()) {
				bean = new ContactLensesBean();
				bean.setNome(rs.getString("Nome"));
				bean.setMarca(rs.getString("Marca"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzo(rs.getDouble("Prezzo"));
				bean.setImg1(rs.getString("Img1"));

				stm.close();
				rs.close();

				query = "SELECT * "
						+ "FROM lentine l join disponibilita d on l.nome = d.nome and l.marca = d.marca"
						+ " WHERE d.nome = ? AND d.marca = ? AND d.gradazione = ?;";
				stm = conn.prepareStatement(query);
				stm.setString(1, nome);
				stm.setString(2, marca);
				stm.setDouble(3, gradazione);
				rs = stm.executeQuery();
				if (rs.next()) {
					bean.setGradazione(rs.getDouble("Gradazione"));
					bean.setColore(rs.getString("Colore"));
					bean.setRaggio(rs.getDouble("Raggio"));
					bean.setDiametro(rs.getDouble("Diametro"));
					bean.setNumeroPezziNelPacco(rs.getInt("NumeroPezziNelPacco"));
					bean.setTipologia(rs.getString("Tipologia"));
					bean.setDisponibilita(rs.getInt("NumeroPezziDisponibili"));

				}
			}
			stm.close();
			rs.close();

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
	
	
	//da rivedere, deve comprendere disponibilità
	public Collection<ArticleBean> doRetrieveAll(String order) throws SQLException{
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ArticleBean bean = null;
		Collection<ArticleBean> products = new LinkedList<ArticleBean>();

		String selectSQL = "SELECT * FROM  quattrocchidb.articolo ";
		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}


		try {
			conn = DriverManagerConnectionPool.getConnection();
			preparedStatement = conn.prepareStatement(selectSQL);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
					bean = new ArticleBean();
					bean.setNome(rs.getString("Nome"));
					bean.setMarca(rs.getString("Marca"));
					bean.setTipo(rs.getString("Tipo"));
					bean.setImg1(rs.getString("Img1"));
					products.add(bean);
			}
			rs.close();
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

	//perché cerca solo tra gli occhiali?
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
				bean.setDisponibilita(rs.getInt("Disponibilita"));
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
				bean.setDisponibilita(rs.getInt("Disponibilita"));
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
		String sql = "select distinct articolo.nome, articolo.marca, tipo, prezzo, img1  "
				+ "from articolo "
				+ "right join lentine on articolo.nome=lentine.nome "
					+ "and articolo.marca=lentine.marca"
				+ " join disponibilita on lentine.nome = disponibilita.nome "
					+ "and lentine.marca = disponibilita.marca;";
		
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
			sql+=" and disponibilita.gradazione="+gradazione;
		}
		if(tipologia!=null&&!tipologia.equalsIgnoreCase("")){
			sql+=" and disponibilita.gradazione='"+tipologia+"'";
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
				bean.setDisponibilita(rs.getInt("Disponibilita"));
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
