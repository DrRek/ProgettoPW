package it.quattrocchi.support;

public class CartArticle{
	
	public CartArticle(ArticleBean a, int n){
		numero = n;
		articolo = a;
	}
	
	public void aggiungi(){
		if(numero + 1 > articolo.getDisponibilita())
			//eccezione
			return;
		numero++;
	}
	
	public void aggiorna(int n){
		if((n < 0) || (n > articolo.getDisponibilita()))
			//dovrebbe lanciare eccezione
			return;
		else
			numero = n;
	}
	
	public ArticleBean getArticle(){
		return articolo;
	}
	
	public int getQuantity(){
		return numero;
	}
	
	public double getPrezzo(){
		return (float)Math.floor((articolo.getPrezzo()*numero) * 100) / 100;
	}
	
	private int numero;
	private ArticleBean articolo;
}
