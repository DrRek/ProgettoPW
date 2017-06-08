package it.quattrocchi.support;

import java.sql.Date;

public class UserBean {
	//Importante modificare questo metodo quando si implementerà la scelta delle tante carte di credito
	public String getCard() {
		// TODO Auto-generated method stub
		return "1111222233334444";
	}
	
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



	private String user, nome, cognome, stato, cap, indirizzo, email, password;
	private Date dataDiNascita;
}
