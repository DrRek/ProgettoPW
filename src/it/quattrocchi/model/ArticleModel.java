package it.quattrocchi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import it.quattrocchi.support.ArticleBean;
import it.quattrocchi.support.ContactLensesBean;
import it.quattrocchi.support.GlassesBean;

public class ArticleModel {

	private static final String TABLE_NAME = "quattrocchidb.articolo";

	public boolean isGlasses(String nome, String marca) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stm = null;
		boolean found = false;

		String query = "SELECT * FROM " + " quattrocchidb.occhiale " + " WHERE Nome = ? AND Marca = ?;";

		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, nome);
			stm.setString(2, marca);

			ResultSet rs = stm.executeQuery();

			if(rs.next()) 
				found = true;
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
		return found;
	}

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

					if(esisteGradazione(bean.getNome(), bean.getMarca(),bean.getGradazione()))
						query = "update disponibilita set NumeroPezziDisponibili=? where Nome=? and Marca=? and Gradazione=?;";
					else
						query = "insert into disponibilita(NumeroPezziDisponibili, Nome, Marca, Gradazione) values (?,?,?,?);";
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
				conn.commit();

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
					conn.commit();
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
					conn.commit();

					query = "insert into disponibilita values(?,?,?,?);";
					stm = conn.prepareStatement(query);
					stm.setString(1, bean.getNome());
					stm.setString(2, bean.getMarca());
					stm.setInt(3, bean.getDisponibilita());
					stm.setDouble(4, bean.getGradazione());
					stm.executeUpdate();
					stm.close();
					conn.commit();
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

	private boolean esisteGradazione(String nome, String marca, double gradazione) throws SQLException {
		Connection conn = null;
		PreparedStatement stm = null;
		String query = "Select * from disponibilita where Nome=? and Marca=? and Gradazione=?;";
		boolean found = false;
		try
		{
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, nome);
			stm.setString(2, marca);
			stm.setDouble(3, gradazione);
			ResultSet rs = stm.executeQuery();
			if(rs.next())
				found = true;
			rs.close();
			stm.close();
		} finally {
			try {
				if (stm != null)
					stm.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(conn);
			}
		}
		return found;
	}

	//si mette la disponibilità a zero, per non perdere la tracciabilità degli ordini passati
	public void doDelete(String nome, String marca) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stm = null;
		String query = null;

		if(isGlasses(nome,marca))
		{
			query = "UPDATE " + "quattrocchidb.occhiale"
					+ " set NumeroPezziDisponibili = 0" 
					+ " WHERE Nome = ? AND Marca = ?;";
		}
		else
		{
			query = "UPDATE " + "quattrocchidb.disponibilita"
					+ " set NumeroPezziDisponibili = 0" 
					+ " WHERE Nome = ? AND Marca = ?;";
		}

		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, nome);
			stm.setString(2, marca);

			stm.executeUpdate();
			stm.close();
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
						+ " WHERE d.nome = ? AND d.marca = ? AND d.gradazione = ?"
						+ "AND d.NumeroPezziDisponibili>0;";
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

	public ArrayList<ContactLensesBean> doRetrieveAllContactLenses(String nome, String marca) throws SQLException{
		Connection conn = null;
		PreparedStatement stm = null;
		ContactLensesBean bean = null;
		ArrayList<ContactLensesBean> lentine = null;

		String query = "SELECT * FROM " + TABLE_NAME + " WHERE Nome = ? AND Marca = ?;";

		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, nome);
			stm.setString(2, marca);

			ResultSet rs = stm.executeQuery();

			if(rs.next()) {
				lentine = new ArrayList<ContactLensesBean>();
				String lNome = rs.getString("Nome");
				String lMarca = rs.getString("Marca");
				String lTipo = rs.getString("Tipo");
				Double lPrezzo = rs.getDouble("Prezzo");
				String lImg1 = rs.getString("Img1");

				stm.close();
				rs.close();

				query = "SELECT * "
						+ "FROM lentine l join disponibilita d on l.nome = d.nome and l.marca = d.marca"
						+ " WHERE d.nome = ? AND d.marca = ? ";

				stm = conn.prepareStatement(query);
				stm.setString(1, nome);
				stm.setString(2, marca);
				rs = stm.executeQuery();
				while(rs.next()){
					bean = new ContactLensesBean();
					bean.setNome(lNome);
					bean.setMarca(lMarca);
					bean.setTipo(lTipo);
					bean.setPrezzo(lPrezzo);
					bean.setImg1(lImg1);

					bean.setGradazione(rs.getDouble("Gradazione"));
					bean.setColore(rs.getString("Colore"));
					bean.setRaggio(rs.getDouble("Raggio"));
					bean.setDiametro(rs.getDouble("Diametro"));
					bean.setNumeroPezziNelPacco(rs.getInt("NumeroPezziNelPacco"));
					bean.setTipologia(rs.getString("Tipologia"));
					bean.setDisponibilita(rs.getInt("NumeroPezziDisponibili"));

					lentine.add(bean);
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
		return lentine;
	}

	//prende qualsiasi prodotto con disponibilità cumulativa nel caso delle lenti a contatto
	public Collection<ArticleBean> doRetrieveAll(String order) throws SQLException{
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ArticleBean bean = null;
		Collection<ArticleBean> products = new LinkedList<ArticleBean>();

		String selectSQL = 
				"select distinct a.nome, a.marca, a.tipo, a.prezzo, a.img1, sum(d.NumeroPezziDisponibili) as NumeroPezziDisponibili "
						+ "from articolo a right join lentine l "
						+ "on a.nome=l.nome and a.marca=l.marca "
						+ "join disponibilita d "
						+ "on l.nome = d.nome and l.marca = d.marca "
						+ "group by a.nome, a.marca "
						+ "union "
						+ "select distinct a.nome, a.marca, a.tipo, a.prezzo, a.img1, o.NumeroPezziDisponibili "
						+ "from articolo a right join occhiale o "
						+ "on a.nome=o.nome and a.marca=o.marca ";
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
				bean.setPrezzo(rs.getDouble("prezzo"));
				bean.setDisponibilita(rs.getInt("NumeroPezziDisponibili"));
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

	//cerca in base a stringa
	public Collection<ArticleBean> doRetrieve(String daCercare, String order) throws SQLException{
		Connection conn = null;
		PreparedStatement stm = null;
		Collection<ArticleBean> products = new LinkedList<ArticleBean>();
		daCercare = "%"+daCercare+"%";
		String sql = 
				"select distinct a.nome, a.marca, a.tipo, a.prezzo, a.img1, sum(d.NumeroPezziDisponibili) as NumeroPezziDisponibili "
						+ "from articolo a right join lentine l "
						+ "on a.nome=l.nome and a.marca=l.marca "
						+ "join disponibilita d "
						+ "on l.nome = d.nome and l.marca = d.marca "
						+ "where (a.nome LIKE ?) or (a.marca LIKE ?)"
						+ "group by a.nome, a.marca "
						+ "union "
						+ "select distinct a.nome, a.marca, a.tipo, a.prezzo, a.img1, o.NumeroPezziDisponibili "
						+ "from articolo a right join occhiale o "
						+ "on a.nome=o.nome and a.marca=o.marca "
						+ "where (a.Nome LIKE ?) or (o.Descrizione LIKE ?) or (a.Marca LIKE ?) ";
		if (order != null && !order.equals(""))
			sql += "order by " + order;
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(sql);
			stm.setString(1, daCercare);
			stm.setString(2, daCercare);
			stm.setString(3, daCercare);
			stm.setString(4, daCercare);
			stm.setString(5, daCercare);
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				ArticleBean bean = new ArticleBean();

				bean.setNome(rs.getString("Nome"));
				bean.setMarca(rs.getString("Marca"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzo(rs.getDouble("Prezzo"));
				bean.setImg1(rs.getString("Img1"));
				bean.setDisponibilita(rs.getInt("NumeroPezziDisponibili"));
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
		String sql = "select a.nome, a.marca, a.tipo, a.prezzo, a.img1, o.NumeroPezziDisponibili "
				+ "from articolo a right join occhiale o "
				+ "on a.nome=o.nome and a.marca=o.marca";
		sql+= " where ((a.Nome LIKE '%"+daCercare+"%') or (o.Descrizione LIKE '%"+daCercare+"%') or (a.Marca LIKE '%"+daCercare+"%'))";
		if(marca!=null&&!marca.equalsIgnoreCase("")){
			sql+=" and a.marca='"+marca+"'";
		}
		if(prezzoMin!=null&&!prezzoMin.equalsIgnoreCase("0")&&!prezzoMin.equalsIgnoreCase("")){
			sql+=" and a.prezzo>="+prezzoMin;
		}
		if(prezzoMax!=null&&!prezzoMax.equalsIgnoreCase("Max")&&!prezzoMax.equalsIgnoreCase("")){
			sql+=" and a.prezzo<="+prezzoMax;
		}
		if(sesso!=null&&!sesso.equalsIgnoreCase("")&&!sesso.equalsIgnoreCase("any")){
			sql+=" and (o.sesso='U' or o.sesso='"+sesso+"')";
		}
		if(colore!=null&&!colore.equalsIgnoreCase("")){ //cerca nella descrizione riferimenti al colore
			sql+=" and o.descrizione LIKE '%"+colore+"%'";
		}
		if(sort!=null&&!sort.equalsIgnoreCase("")){
			sql+=" order by "+sort;
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
				bean.setDisponibilita(rs.getInt("NumeroPezziDisponibili"));
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

	public Collection<ArticleBean> doRetrieveContactLenses(String daCercare, String marca, String prezzoMin, String prezzoMax, String gradazione, String tipologia, String raggio, String diametro, String colore, String sort) throws SQLException{
		Connection conn = null;
		PreparedStatement stm = null;
		Collection<ArticleBean> products = new LinkedList<ArticleBean>();
		String sql = "select distinct a.nome, a.marca, a.tipo, a.prezzo, a.img1, ld.NumeroPezziDisponibili "
				+ "from articolo a join ("
						+ "select l.nome, l.marca, l.tipologia, l.numeroPezziNelPacco, l.raggio, l.diametro, l.colore, sum(NumeroPezziDisponibili) as NumeroPezziDisponibili "
						+ "from lentine l right join disponibilita d on l.nome=d.nome and l.marca=d.marca ";
		if(gradazione!=null&&!gradazione.equalsIgnoreCase("")){
			sql+=" where d.gradazione="+gradazione;
		}
		sql += "group by l.nome, l.marca";
		sql+=") as ld on a.nome=ld.nome and a.marca=ld.marca";
		
		sql+= " where ((a.Nome LIKE '%"+daCercare+"%') or (a.Marca LIKE '%"+daCercare+"%'))";
		
		if(marca!=null&&!marca.equalsIgnoreCase("")){
			sql+=" and a.marca='"+marca+"'";
		}
		if(prezzoMin!=null&&!prezzoMin.equalsIgnoreCase("0")&&!prezzoMin.equalsIgnoreCase("")){
			sql+=" and a.prezzo>="+prezzoMin;
		}
		if(prezzoMax!=null&&!prezzoMax.equalsIgnoreCase("Max")&&!prezzoMax.equalsIgnoreCase("")){
			sql+=" and a.prezzo<="+prezzoMax;
		}
		if(tipologia!=null&&!tipologia.equalsIgnoreCase("")){
			sql+=" and ld.tipologia='"+tipologia+"'";
		}
		if(raggio!=null&&!raggio.equalsIgnoreCase("")){
			sql+=" and ld.raggio="+raggio;
		}
		if(diametro!=null&&!diametro.equalsIgnoreCase("")){
			sql+=" and ld.diametro="+diametro;
		}
		if(colore!=null&&!colore.equalsIgnoreCase("")){
			sql+=" and ld.colore='"+colore+"'";
		}
		if(sort!=null&&!sort.equalsIgnoreCase("")){
			sql+=" order by "+sort;
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
				bean.setDisponibilita(rs.getInt("NumeroPezziDisponibili"));
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
