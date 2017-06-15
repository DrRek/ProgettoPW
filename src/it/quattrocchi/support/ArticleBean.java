package it.quattrocchi.support;

import java.util.ArrayList;

public class ArticleBean {
	
	public ArticleBean(){
		nome=null;
		marca=null;
		tipo=null;
		img1=null;
		img2=null;
		img3=null;
		descrizione=null;
		sesso=null;
		prezzo=0;
		raggio=0;
		diametro=0;
		numeroPezziNelPacco=0;
		numeroPezziDisponibili=0;
		lentine=new ArrayList<SingleContactLenseBean>();
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
	
	public String getImg2(){
		return img2;
	}
	
	public void setImg2(String img2){
		this.img2=img2;
	}
	
	public String getImg3(){
		return img3;
	}
	
	public void setImg3(String img3){
		this.img3=img3;
	}
	
	public String getTipologia() {
		return lentine.get(0).getTipologia();
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

	public int getNumeroPezziNelPacco() {
		return numeroPezziNelPacco;
	}

	public void setNumeroPezziNelPacco(int numeroPezziNelPacco) {
		this.numeroPezziNelPacco = numeroPezziNelPacco;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public boolean equals(Object o){
		ArticleBean a = (ArticleBean) o;
		if((a.getNome().equals(nome)) && (a.getMarca().equals(marca)))
			return true;
		return false;
	}

	public int getNumeroPezziDisponibili() {
		return numeroPezziDisponibili;
	}

	public void setNumeroPezziDisponibili(int numeroPezziDisponibili) {
		this.numeroPezziDisponibili = numeroPezziDisponibili;
	}

	public ArrayList<SingleContactLenseBean> getLentine() {
		return lentine;
	}

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

	private String nome, marca, tipo, img1, img2, img3, descrizione, sesso;
	private double prezzo, raggio, diametro;
	private int numeroPezziNelPacco, numeroPezziDisponibili;
	private ArrayList<SingleContactLenseBean> lentine;
}
