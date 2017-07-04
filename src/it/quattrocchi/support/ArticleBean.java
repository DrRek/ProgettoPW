package it.quattrocchi.support;

import java.util.ArrayList;

public class ArticleBean {

	public ArticleBean(){
		nome=null;
		marca=null;
		tipo=null;
		img1=null;
		prezzo = 0;
		disponibilita = 0;
		tipoSconto=null;
		sconto=0;
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
	public boolean equals(Object o)
	{
		if(o.getClass().getName().equals("it.quattrocchi.support.GlassesBean") || o.getClass().getName().equals("it.quattrocchi.support.ContactLensesBean") || o.getClass().getName().equals("it.quattrocchi.support.ArticleBean") )
		{
			if(((ArticleBean)o).getNome().equalsIgnoreCase(nome) && ((ArticleBean)o).getMarca().equalsIgnoreCase(marca))
			{
				return true;
			}
		}
		return false;
	}
	public void setSconto(ArrayList<PromotionBean> promozioni){
		Double scontoPercentualeTemp=0.0, scontoSubtractNONCumulabileTemp=0.0, scontoSubtractCumulabileTemp=0.0;
		for(PromotionBean p : promozioni){
			if(p.isCumulabile()){
				scontoSubtractCumulabileTemp+=p.getSconto();
			}else{
				if(p.getTipo().equalsIgnoreCase("s")){
					if(scontoSubtractNONCumulabileTemp<p.getSconto()){
						scontoSubtractNONCumulabileTemp = p.getSconto();
					}
				}else {
					if(scontoPercentualeTemp<p.getSconto()){
						scontoPercentualeTemp = p.getSconto();
					}
				}
			}
		}
		Double prezzoSubtractCumulabileTemp=prezzo-scontoSubtractCumulabileTemp;
		Double prezzoSubtractNONCumulabileTemp=prezzo-scontoSubtractNONCumulabileTemp;
		Double PrezzoPercentualeTemp=prezzo-(prezzo*scontoPercentualeTemp/100);
		if(prezzoSubtractCumulabileTemp<prezzoSubtractNONCumulabileTemp&&prezzoSubtractCumulabileTemp<PrezzoPercentualeTemp){
			this.sconto=scontoSubtractCumulabileTemp;
			this.tipoSconto="s";
		}else if(prezzoSubtractNONCumulabileTemp<prezzoSubtractCumulabileTemp&&prezzoSubtractNONCumulabileTemp<PrezzoPercentualeTemp){
			this.sconto=scontoSubtractNONCumulabileTemp;
			this.tipoSconto="s";
		}else{
			this.sconto=scontoPercentualeTemp;
			this.tipoSconto="%";
		}
	}
	public Double getSconto(){
		return sconto;
	}
	public String getTipoSconto(){
		return tipoSconto;
	}
	public Double getRealPrezzo() {
		if(tipoSconto.equalsIgnoreCase("s")){
			return prezzo-sconto;
		}
		else{
			return prezzo-(prezzo*sconto/100);
		}
	}
	private String nome, marca, tipo, img1, tipoSconto;
	private double prezzo, sconto;
	private int disponibilita;
}
