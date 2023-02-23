package model;

import java.util.ArrayList;
import java.util.List;

public class Kategoriat {
	private List<Kategoria> kategoriat;
	
	public Kategoriat() {
		kategoriat = new ArrayList<>();
	}
	
	public void lisääKategoria(Kategoria kategoria) {
		kategoriat.add(kategoria);
	}
	
	public List<Kategoria> getKategoriat() {
		return kategoriat;
	}
}