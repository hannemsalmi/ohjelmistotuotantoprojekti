package model;

public class Kategoria {
	private Kulut kulut;
	private String nimi;
	
	public Kategoria(String nimi) {
		this.nimi = nimi;
		this.kulut = kulut.getKulut();
	}
	
	public String getNimi() {
		return nimi;
	}
	
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
}