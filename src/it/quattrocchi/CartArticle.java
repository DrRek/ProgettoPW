package it.quattrocchi;

public class CartArticle extends ArticleBean{
	
	public CartArticle(ArticleBean a, int n){
		numero = n;
		articolo = a;
	}
	
	public void aggiungi(){
		if(numero + 1 > articolo.getNumeroPezziDisponibili())
			//eccezione
			return;
		numero++;
	}
	
	public void rimuovi(){
		if(numero - 1 < 0)
			//dovrebbe lanciare eccezione
			return;
		numero--;
	}
	
	public void aggiorna(int n){
		if((n < 0) || (n > articolo.getNumeroPezziDisponibili()))
			//dovrebbe lanciare eccezione
			return;
		else
			numero = n;
	}
	
	public ArticleBean getArticle()
	{
		return articolo;
	}
	
	public int getQuantity(){
		return numero;
	}
	
	
	private int numero;
	private ArticleBean articolo;
}