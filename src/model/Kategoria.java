package model;

public class Kategoria{
	private int kategoriaID;
	private String nimi;
	
	public Kategoria(String nimi) {
		this.nimi = nimi;
	}
	
	public int getKategoriaID() {
		return kategoriaID;
	}

	public void setKategoriaID(int kategoriaID) {
		this.kategoriaID = kategoriaID;
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

}