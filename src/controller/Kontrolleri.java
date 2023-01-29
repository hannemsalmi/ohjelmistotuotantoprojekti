package controller;

import java.time.LocalDate;

import model.Budjettilaskuri;
import model.IBudjettilaskuri;
import model.Kategoria;
import model.Kayttaja;
import model.Kulu;
import view.IGUI;

public class Kontrolleri implements IKontrolleriVtoM, IKontrolleriMtoV{
	IGUI gui;
	IBudjettilaskuri model = new Budjettilaskuri();
	Kulu kulu;
	Kategoria kategoria;

	public Kontrolleri(IGUI gui) {
		this.gui = gui;
	}

	@Override
	public void lisaaKategoria(String nimi) {
		kategoria = new Kategoria(nimi);
		System.out.println(kategoria);
	}

	@Override
	public void lisaaKulu(String nimi, double hinta, LocalDate paivamaara, Kategoria kategoria, Kayttaja kayttaja, String kuvaus) {
		kulu = new Kulu(nimi, hinta, paivamaara, kategoria, kayttaja, kuvaus);
		System.out.println(kulu);
	}
	
}