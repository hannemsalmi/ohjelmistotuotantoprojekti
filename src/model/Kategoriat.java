package model;

public class Kategoriat {
	private List<Kategoria> kategoriat;
	
	public Kategoriat() {
		kategoriat = new ArrayList<>();

		// Esimerkki aloituskategoriat:
		kategoriat.add(Ruoka);
		kategoriat.add(Viihde);
		kategoriat.add(Harrastukset);
		kategoriat.add(Talouslaskut);
	}
	
	public void lisääKategoria(Kategoria kategoria) {
		kategoriat.add(kategoria);
	}
	
	public List<Kategoria> getKategoriat() {
		return kategoriat;
	}
}