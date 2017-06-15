package it.quattrocchi.support;

public class SingleContactLenseBean {
	public SingleContactLenseBean (){
		nome=null;
		marca=null;
		modello=null;
		colore=null;
		tipologia=null;
		gradazione=0;
		raggio=0;
		diametro=0;
		disponibilita=0;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	public double getGradazione() {
		return gradazione;
	}
	public void setGradazione(double gradazione) {
		this.gradazione = gradazione;
	}
	public double getRaggio() {
		return raggio;
	}
	public void setRaggio(double raggio) {
		this.raggio = raggio;
	}
	public double getDiametro() {
		return diametro;
	}
	public void setDiametro(double diametro) {
		this.diametro = diametro;
	}
	public int getDisponibilita() {
		return disponibilita;
	}
	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}
	
	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	private String nome, marca, modello, colore, tipologia;
	private double gradazione, raggio, diametro;
	private int disponibilita;
}
