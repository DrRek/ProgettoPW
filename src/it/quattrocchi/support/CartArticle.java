package it.quattrocchi.support;

public class CartArticle{
	
	public CartArticle(ArticleBean a, int n){
		numero = n;
		articolo = a;
		gradazione=0.0;
		prescrizione=null;
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
		return (float)Math.floor((articolo.getRealPrezzo()*numero) * 100) / 100;
	}
	
	public Double getGradazione() {
		return gradazione;
	}

	public void setGradazione(Double gradazione) {
		this.gradazione = gradazione;
	}

	public PrescriptionBean getPrescrizione() {
		return prescrizione;
	}

	public void setPrescrizione(PrescriptionBean prescrizione) {
		this.prescrizione = prescrizione;
	}

	private int numero;
	private ArticleBean articolo;
	private Double gradazione;
	private PrescriptionBean prescrizione;
}
