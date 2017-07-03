package it.quattrocchi.support;

import java.sql.Date;
import java.util.ArrayList;

public class PromotionBean {
	public PromotionBean(){
		nome=null;
		descrizione=null;
		tipo=null;
		sconto=0.0;
		dataInizio=null;
		dataFine=null;
		cumulabile=false;
		validi=new ArrayList<ArticleBean>();
	}
	public void setNome(String nome){
		this.nome=nome;
	}
	public String getNome(){
		return this.nome;
	}
	public void setDescrizione(String descrizione){
		this.descrizione=descrizione;
	}
	public String getDescrizione(){
		return this.descrizione;
	}
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	public String getTipo(){
		return this.tipo;
	}
	public void setSconto(Double sconto){
		this.sconto=sconto;
	}
	public Double getSconto(){
		return this.sconto;
	}
	public void setCumulabile(boolean cumulabile){
		this.cumulabile=cumulabile;
	}
	public boolean isCumulabile(){
		return cumulabile;
	}
	public void setValidi(ArrayList<ArticleBean> validi){
		this.validi=validi;
	}
	public ArrayList<ArticleBean> getValidi(){
		return validi;
	}
	public void addToValidi(ArticleBean add){
		validi.add(add);
	}
	public void removeFromValidi(ArticleBean add){
		validi.remove(add);
	}
	public void setDataInizio(Date inizio){
		this.dataInizio=inizio;
	}
	public Date getDataInizio(){
		return dataInizio;
	}
	public void setDataFine(Date fine){
		this.dataFine=fine;
	}
	public Date getDataFine(){
		return dataFine;
	}
	String nome, descrizione, tipo;
	Double sconto;
	Date dataInizio, dataFine;
	boolean cumulabile;
	ArrayList<ArticleBean> validi;
}
