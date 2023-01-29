package model;

import java.util.ArrayList;
import java.util.List;

public class Kategoriat {
	private List<Kategoria> kategoriat;
	private Kategoria ruoka;
	private Kategoria viihde;
	private Kategoria harrastukset;
	private Kategoria talouslaskut;
	
	public Kategoriat() {
		kategoriat = new ArrayList<>();

		// Esimerkki aloituskategoriat:
		kategoriat.add(ruoka);
		kategoriat.add(viihde);
		kategoriat.add(harrastukset);
		kategoriat.add(talouslaskut);
	}
	
	public void lisääKategoria(Kategoria kategoria) {
		kategoriat.add(kategoria);
	}
	
	public List<Kategoria> getKategoriat() {
		return kategoriat;
	}
}