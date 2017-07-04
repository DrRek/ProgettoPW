package it.quattrocchi.support;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

public class UserBean {
	public UserBean(){
		setUser(null);
		setNome(null);
		setCognome(null);
		setStato(null);
		setCap(null);
		setIndirizzo(null);
		setEmail(null);
		setDataDiNascita(null);
		setPassword(null);
		prescriptions=null;
	}
	
	public void setCards(ArrayList<CreditCardBean> cc) {
		this.cc = cc;
	}
	
	public ArrayList<CreditCardBean> getCards() {
		return cc;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public ArrayList<PrescriptionBean> getPrescriptions() {
		return this.prescriptions;
	}
	
	public void setPrescriptions(ArrayList<PrescriptionBean> prescriptions) {
		this.prescriptions = prescriptions;
	}
	
	public ArrayList<OrderBean> getOrders() {
		return this.orders;
	}
	
	public void setOrders(ArrayList<OrderBean> orders) {
		this.orders = orders;
	}

	private ArrayList<PrescriptionBean> prescriptions;
	private ArrayList<CreditCardBean> cc;
	private String user, nome, cognome, stato, cap, indirizzo, email, password;
	private Date dataDiNascita;
	private ArrayList<OrderBean> orders;
}
