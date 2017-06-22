package it.quattrocchi.support;

public class ArticleBean {
	
	public ArticleBean(){
		nome=null;
		marca=null;
		tipo=null;
		img1=null;
		prezzo = 0;
		disponibilita = 0;
	}
	
	public String getNome(){
		return nome;
	}
	
	public void setNome(String nome){
		this.nome=nome;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	
	public String getMarca(){
		return marca;
	}
	
	public void setMarca(String marca){
		this.marca=marca;
	}
	
	public double getPrezzo(){
		return prezzo;
	}
	
	public void setPrezzo(double prezzo){
		this.prezzo=prezzo;
	}
	
	public String getImg1(){
		return img1;
	}
	
	public void setImg1(String img1){
		this.img1=img1;
	}
	
	public int getDisponibilita() {
		return disponibilita;
	}
	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}
/*
	public void setLentine(String modello, double gradazione, double raggio, double diametro, int disponibilita, String colore, String tipologia) {
		SingleContactLenseBean trovata = new SingleContactLenseBean();
		trovata.setNome(nome);
		trovata.setMarca(marca);
		trovata.setModello(modello);
		trovata.setGradazione(gradazione);
		trovata.setRaggio(raggio);
		trovata.setDiametro(diametro);
		trovata.setDisponibilita(disponibilita);
		trovata.setColore(colore);
		trovata.setTipologia(tipologia);
		lentine.add(trovata);
	}
*/
	private String nome, marca, tipo, img1;
	private double prezzo;
	private int disponibilita;
}
