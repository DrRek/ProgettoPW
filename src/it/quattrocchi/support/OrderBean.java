package it.quattrocchi.support;

import java.sql.Date;
import java.util.ArrayList;

public class OrderBean {
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	//Verra' cambiato quando ci sara' il bean della carta di credito
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public Date getDataEsecuzione() {
		return dataEsecuzione;
	}
	public void setDataEsecuzione(Date dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public UserBean getCliente() {
		return cliente;
	}
	public void setCliente(UserBean cliente) {
		this.cliente = cliente;
	}
	public ArrayList<CartArticle> getOrdinati() {
		return ordinati;
	}
	public void setOrdinati(ArrayList<CartArticle> ordinati) {
		this.ordinati = ordinati;
	}
	private String codice, cc;
	private Date dataEsecuzione;
	private double costo;
	private UserBean cliente;
	private ArrayList<CartArticle> ordinati;
}
