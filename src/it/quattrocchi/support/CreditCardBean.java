package it.quattrocchi.support;

import java.sql.Date;

public class CreditCardBean {

	public CreditCardBean(){
		numeroCC = "";
		intestatario = "";
		circuito = "";
		cvcCvv = "";
		cliente = null;
		dataScadenza = null;
	}
	
	public String getNumeroCC() {
		return numeroCC;
	}
	
	public String getIntestatario() {
		return intestatario;
	}
	
	public String getCircuito() {
		return circuito;
	}
	
	public String getCvcCvv() {
		return cvcCvv;
	}
	
	public Date getDataScadenza() {
		return dataScadenza;
	}
	
	public UserBean getCliente() {
		return cliente;
	}
	
	public void setNumeroCC(String numeroCC) {
		this.numeroCC = numeroCC;
	}
	
	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}
	
	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}
	
	public void setCvcCvv(String cvcCvv) {
		this.cvcCvv = cvcCvv;
	}
	
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	
	public void setCliente(UserBean user) {
		this.cliente = user;
	}
	
	private String numeroCC, intestatario, circuito, cvcCvv;
	private UserBean cliente;
	private Date dataScadenza;
}
