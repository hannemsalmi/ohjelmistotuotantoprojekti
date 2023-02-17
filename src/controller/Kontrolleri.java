package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dataAccessObjects.KategoriaDao;
import dataAccessObjects.KayttajaDao;
import dataAccessObjects.KuluDao;
import model.Budjettilaskuri;
import model.IBudjettilaskuri;
import model.Kategoria;
import model.Kategoriat;
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
	}
	
	public List<Kulu> getKulut(int kayttajaid) {
		return kuluDao.haeKulut(kayttajaid);
	}
	
	public Kulu getKulu(int kuluId) {
		return kuluDao.haeKulu(kuluId);
	}

	@Override
	public void lisaaKategoria(String nimi) {
		kategoria = new Kategoria(nimi);
		System.out.println(kategoria);
		kategoriaDao.lisaaKategoria(kategoria);
	}

	@Override
	public void lisaaKulu(String nimi, double hinta, LocalDate paivamaara, Kategoria kategoria, Kayttaja kayttaja, String kuvaus) {
		kulu = new Kulu(nimi, hinta, paivamaara, kategoria, kayttaja, kuvaus);
		System.out.println(kulu);
		kuluDao.lisaaKulu(kulu);
	}
	
	public Kayttaja getKayttaja(int kayttajaid) {
		return kayttajaDao.haeKayttajat(kayttajaid);
	}
	public void lisaaKayttaja(String nimi, double budjetti) {
		kayttaja = new Kayttaja(nimi, budjetti);
		System.out.println(kayttaja);
		kayttajaDao.lisaaKayttaja(kayttaja);
	}
	
	public List<String> getKayttajat(){
		List<String> kayttajaNimet = new ArrayList<String>();
		List<Kayttaja> kayttajaObjektit = kayttajaDao.haeKayttajaLista();
		for(Kayttaja k : kayttajaObjektit) {
			kayttajaNimet.add(k.getNimimerkki());
		}
		return kayttajaNimet;
	}
	
	public List<String> getKategorianimet() {
		List<String> kategoriaNimet = new ArrayList<String>();
		List<Kategoria> kategoriaObjektit = kategoriaDao.haeKategoriaLista();
		for(Kategoria k : kategoriaObjektit) {
			kategoriaNimet.add(k.getNimi());
		}
		return kategoriaNimet;
	}
	
	public Kategoria getKategoria(String nimi) {
		List<Kategoria> kategoriaObjektit2 = kategoriaDao.haeKategoriaLista();
		Kategoria etsitty = null;
		for(Kategoria k : kategoriaObjektit2) {
			if(k.getNimi() == nimi) {
				etsitty = k;
			}
		}
		if (etsitty == null) {
			etsitty = new Kategoria(nimi);
		}
		return etsitty;
	}

	@Override
	public void paivitaBudjetti(int kayttajaID, double budjetti) {
		kayttajaDao.paivitaBudjetti(kayttajaID, budjetti);
	}
	
	public void muokkaaKulua(int id, Double summa, String nimi, String kuvaus) {
		kuluDao.muutaKulu(id, summa, nimi, kuvaus);
	}
	
	public void poistaKulu(int id) {
		kuluDao.poistaKulu(id);
	}
}