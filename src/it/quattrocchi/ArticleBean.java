package it.quattrocchi;

import com.mysql.jdbc.Blob;

public class ArticleBean {
	
	public ArticleBean(){
		codice="";
		tipo="";
		marca="";
		prezzo=-1;
		gradazione=0;
		numeroPezziDisponibili=-1;
		img1=null;
		img2=null;
		img3=null;
	}
	
	public String getCodice(){
		return codice;
	}
	
	public void setCodice(String codice){
		this.codice=codice;
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
	
	public float getPrezzo(){
		return prezzo;
	}
	
	public void setPrezzo(float prezzo){
		this.prezzo=prezzo;
	}
	
	public float getGradazione(){
		return gradazione;
	}
	
	public void setGradazione(float gradazione){
		this.gradazione=gradazione;
	}
	
	public int getNumeroPezziDisponibili(){
		return numeroPezziDisponibili;
	}
	
	public void setNumeroPezziDisponibili(int numeroPezziDisponibili){
		this.numeroPezziDisponibili=numeroPezziDisponibili;
	}
	
	public Blob getImg1(){
		return img1;
	}
	
	public void setImg1(Blob img1){
		this.img1=img1;
	}
	
	public Blob getImg2(){
		return img2;
	}
	
	public void setImg2(Blob img2){
		this.img2=img2;
	}
	
	public Blob getImg3(){
		return img3;
	}
	
	public void setImg3(Blob img3){
		this.img3=img3;
	}
	
	String codice;
	String tipo;
	String marca;
	float prezzo;
	float gradazione;
	int numeroPezziDisponibili;
	Blob img1;
	Blob img2;
	Blob img3;
}
