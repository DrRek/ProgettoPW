package it.quattrocchi.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.quattrocchi.support.CreditCardBean;
import it.quattrocchi.support.OrderBean;
import it.quattrocchi.support.PrescriptionBean;
import it.quattrocchi.support.UserBean;

public class PrescriptionModel {
	private static final String TABLE_NAME = "quattrocchidb.prescrizione";
	
	public void doSave(PrescriptionBean prescription) throws SQLException{
		
		Connection conn = null;
		PreparedStatement stm = null;
		
		String query = "INSERT INTO " + TABLE_NAME + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, prescription.getCodice());
			stm.setString(2, prescription.getCliente().getUser());
			stm.setString(3, prescription.getTipo());
			stm.setFloat(4, prescription.getSferaSX());
			stm.setFloat(5, prescription.getCilindroSX());
			stm.setFloat(6, prescription.getAsseSX());
			stm.setFloat(7, prescription.getSferaDX());
			stm.setFloat(8, prescription.getCilindroDX());
			stm.setFloat(9, prescription.getAsseDX());
			stm.setFloat(10, prescription.getAddVicinanza());
			stm.setFloat(11, prescription.getPrismaOrizSX());
			stm.setString(12, prescription.getPrismaOrizSXBD());
			stm.setFloat(13, prescription.getPrismaOrizDX());
			stm.setString(14, prescription.getPrismaOrizDXBD());
			stm.setFloat(15, prescription.getPrismaVertSX());
			stm.setString(16, prescription.getPrismaVertSXBD());
			stm.setFloat(17, prescription.getPrismaVertDX());
			stm.setString(18, prescription.getPrismaVertDXBD());
			stm.setFloat(19, prescription.getPupillarDistanceSX());
			stm.setFloat(20, prescription.getPupillarDistanceDX());
			
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
	
	public void doDelete(String codice) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stm = null;

		String query = "delete from " + TABLE_NAME + " where codice=?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, codice);

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
	
	public ArrayList<PrescriptionBean> doRetrieveByCliente(UserBean user) throws SQLException{
		
		ArrayList<PrescriptionBean> pres = new ArrayList<PrescriptionBean>();
		
		Connection conn = null;
		PreparedStatement stm  = null;
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE cliente = ?;";
		
		try {
			conn = DriverManagerConnectionPool.getConnection();
			stm = conn.prepareStatement(query);
			stm.setString(1, user.getUser());
			
			ResultSet rs = stm.executeQuery();
			
			while(rs.next()) {
				PrescriptionBean bean = new PrescriptionBean();
				bean.setCodice(rs.getString("Codice"));
				bean.setCliente(user);
				bean.setTipo(rs.getString("TipoPrescrizione"));
				bean.setSferaSX(rs.getFloat("SferaSinistra"));
				bean.setCilindroSX(rs.getFloat("CilindroSinistra"));
				bean.setAsseSX(rs.getFloat("AsseSinistra"));
				bean.setSferaDX(rs.getFloat("SferaDestra"));
				bean.setCilindroDX(rs.getFloat("CilindroDestra"));
				bean.setAsseDX(rs.getFloat("AsseDestra"));
				bean.setAddVicinanza(rs.getFloat("AddizioneVicinanza"));
				bean.setPrismaOrizSX(rs.getFloat("PrismaOrizSinistra"));
				bean.setPrismaOrizSXBD(rs.getString("PrismaOrizSinistraBaseDirection"));
				bean.setPrismaOrizDX(rs.getFloat("PrismaOrizDestra"));
				bean.setPrismaOrizDXBD(rs.getString("PrismaOrizDestraBaseDirection"));
				bean.setPrismaVertSX(rs.getFloat("PrismaVertSinistra"));
				bean.setPrismaVertSXBD(rs.getString("PrismaVertSinistraBaseDirection"));
				bean.setPrismaVertDX(rs.getFloat("PrismaVertDestra"));
				bean.setPrismaVertDXBD(rs.getString("PrismaVertDestraBaseDirection"));
				bean.setPupillarDistanceSX(rs.getFloat("PDSinistra"));
				bean.setPupillarDistanceDX(rs.getFloat("PDDestra"));
				
				pres.add(bean);
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
		
		return pres;
	}
}
