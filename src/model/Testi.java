package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Testi {
	
	public static void main(String[] args) {
		
		Budjettilaskuri laskuri = new Budjettilaskuri();
		Kayttaja kayttaja = new Kayttaja("Maija",2.50);
		Kategoriat kategoriat = new Kategoriat();
		List<Kategoria> kategorialista = new ArrayList<>();
		kategorialista = kategoriat.getKategoriat();
		LocalDate tanaan = LocalDate.now();
		Kulut kulut = new Kulut();
		Kulu laku = new Kulu("Lakritsi",2.40,tanaan,kategorialista.get(0),kayttaja,"makeanhimo");
		kulut.lisaaKulu(laku);
		//kayttaja.setMaksimibudjetti(800);
		//System.out.println(String.format("%.2f",laskuri.laskeBudjetti(kayttaja.getMaksimibudjetti(), laku.getSumma())));
		for(Kulu kulu: kulut.getKulut()) {
			System.out.println(kulu);
		}
		
	}

}
