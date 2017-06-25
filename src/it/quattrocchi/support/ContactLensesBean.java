package it.quattrocchi.support;

public class ContactLensesBean extends ArticleBean {
	public ContactLensesBean (){
		super();
		colore=null;
		tipologia=null;
		gradazione=0;
		raggio=0;
		diametro=0;
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
	
	public int getNumeroPezziNelPacco() {
		return numeroPezziNelPacco;
	}

	public void setNumeroPezziNelPacco(int numeroPezziNelPacco) {
		this.numeroPezziNelPacco = numeroPezziNelPacco;
	}

	public boolean equals(Object o)
	{
		ContactLensesBean c = (ContactLensesBean) o;
		if(c.getNome().equals(nome) && c.getMarca().equals(marca) && c.getGradazione() == (gradazione))
			return true;
		else
			return false;
	}
	
	private String nome, marca, colore, tipologia;
	private double gradazione, raggio, diametro;
	private int numeroPezziNelPacco;
}
