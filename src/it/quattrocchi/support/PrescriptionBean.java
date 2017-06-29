package it.quattrocchi.support;

public class PrescriptionBean {
	public PrescriptionBean(){
		codice = "";
		tipo = "";
		sferaSx = 0;
		cilindroSx = 0;
		asseSx = 0;
		sferaDx = 0;
		cilindroDx = 0;
		asseDx = 0;
		addVicinanza = 0;
		prismaOrizSx = 0;
		prismaOrizSxBd = "";
		prismaOrizDx = 0;
		prismaOrizDxBd = "";
		prismaVertSx = 0;
		prismaVertSxBd = "";
		prismaVertDx = 0;
		prismaVertDxBd = "";
		pupillarDistanceSx = 0;
		pupillarDistanceDx = 0;
	}
	
	public String getCodice() {
		return this.codice;
	}
	
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public float getSferaSX() {
		return this.sferaSx;
	}
	
	public void setSferaSX(float sferaSx) {
		this.sferaSx = sferaSx;
	}
	
	public float getCilindroSX() {
		return this.cilindroSx;
	}
	
	public void setCilindroSX(float cilindroSx) {
		this.cilindroSx = cilindroSx;
	}
	
	public float getAsseSX() {
		return this.asseSx;
	}
	
	public void setAsseSX(float asseSx) {
		this.asseSx = asseSx;
	}
	public float getSferaDX() {
		return this.sferaDx;
	}
	
	public void setSferaDX(float sferaDx) {
		this.sferaDx = sferaDx;
	}
	
	public float getCilindroDX() {
		return this.cilindroDx;
	}
	
	public void setCilindroDX(float cilindroDx) {
		this.cilindroDx = cilindroDx;
	}
	
	public float getAsseDX() {
		return this.asseDx;
	}
	
	public void setAsseDX(float asseDx) {
		this.asseDx = asseDx;
	}
	
	public float getAddVicinanza() {
		return this.addVicinanza;
	}
	
	public void setAddVicinanza(float addVicinanza) {
		this.addVicinanza = addVicinanza;
	}
	
	public float getPrismaOrizSX() {
		return this.prismaOrizSx;
	}
	
	public void setPrismaOrizSX(float prismaOrizSx) {
		this.prismaOrizSx = prismaOrizSx;
	}
	
	public String getPrismaOrizSXBD() {
		return this.prismaOrizSxBd;
	}
	
	public void setPrismaOrizSXBD(String prismaOrizSxBd) {
		this.prismaOrizSxBd = prismaOrizSxBd;
	}
	
	public float getPrismaOrizDX() {
		return this.prismaOrizDx;
	}
	
	public void setPrismaOrizDX(float prismaOrizDx) {
		this.prismaOrizDx = prismaOrizDx;
	}
	
	public String getPrismaOrizDXBD() {
		return this.prismaOrizDxBd;
	}
	
	public void setPrismaOrizDXBD(String prismaOrizDxBd) {
		this.prismaOrizDxBd = prismaOrizDxBd;
	}
	
	public float getPrismaVertSX() {
		return this.prismaVertSx;
	}
	
	public void setPrismaVertSX(float prismaVertSx) {
		this.prismaVertSx = prismaVertSx;
	}
	
	public String getPrismaVertSXBD() {
		return this.prismaVertSxBd;
	}
	
	public void setPrismaVertSXBD(String prismaVertSxBd) {
		this.prismaVertSxBd = prismaVertSxBd;
	}
	
	public float getPrismaVertDX() {
		return this.prismaVertDx;
	}
	
	public void setPrismaVertDX(float prismaVertDx) {
		this.prismaVertDx = prismaVertDx;
	}
	
	public String getPrismaVertDXBD() {
		return this.prismaVertDxBd;
	}
	
	public void setPrismaVertDXBD(String prismaVertDxBd) {
		this.prismaVertDxBd = prismaVertDxBd;
	}
	
	public float getPupillarDistanceSX() {
		return this.pupillarDistanceSx;
	}
	
	public void setPupillarDistanceSX(float pupillarDistanceSx) {
		this.pupillarDistanceSx = pupillarDistanceSx;
	}
	
	public float getPupillarDistanceDX() {
		return this.pupillarDistanceDx;
	}
	
	public void setPupillarDistanceDX(float pupillarDistanceDx) {
		this.pupillarDistanceDx = pupillarDistanceDx;
	}
	
	String codice, tipo, prismaOrizSxBd, prismaOrizDxBd, prismaVertSxBd, prismaVertDxBd;
	float sferaSx, cilindroSx, asseSx, sferaDx, cilindroDx, asseDx, addVicinanza, prismaOrizSx, prismaOrizDx, prismaVertSx, prismaVertDx, pupillarDistanceSx, pupillarDistanceDx;
}
