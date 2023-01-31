package controller;

import java.time.LocalDate;

import dataAccessObjects.KategoriaDao;
import dataAccessObjects.KayttajaDao;
import dataAccessObjects.KuluDao;
import model.Budjettilaskuri;
import model.IBudjettilaskuri;
import model.Kategoria;
import model.Kayttaja;
import model.Kulu;
import model.Kulut;
import view.IGUI;

public class Kontrolleri implements IKontrolleriVtoM, IKontrolleriMtoV{
	private IGUI gui;
	private IBudjettilaskuri model = new Budjettilaskuri();
	private KategoriaDao kategoriaDao = new KategoriaDao();
	private KayttajaDao kayttajaDao = new KayttajaDao();
	private KuluDao kuluDao = new KuluDao();
	private Kategoria kategoria;
	private Kayttaja kayttaja;
	private Kulu kulu;
	private Kulut kulut = new Kulut();
	public Kontrolleri(IGUI gui) {
		this.gui = gui;
		testi();
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
	
	public void testi() {
		kulut.lisaaKulut(kuluDao.haeKulut(1));
		System.out.println(kulut.toString());
	}
	
}