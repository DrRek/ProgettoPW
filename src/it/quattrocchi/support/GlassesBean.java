package it.quattrocchi.support;

public class GlassesBean extends ArticleBean{

	public GlassesBean()
	{
		super();
		descrizione = null;
		sesso = null;
		img2 = null; 
		img3 = null;
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
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public boolean equals(Object o)
	{
		GlassesBean c = (GlassesBean) o;
		if(c.getNome().equals(super.getNome()) && c.getMarca().equals(super.getMarca()))
			return true;
		else
			return false;
	}

	private String descrizione, sesso, img2, img3;
}
