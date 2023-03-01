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
import model.Kayttaja;
import model.Kulu;
import model.Kulut;
import view.IGUI;

public class Kontrolleri implements IKontrolleri {
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
	public void lisaaKategoria(String nimi, String omistaja) {
		kategoria = new Kategoria(nimi, omistaja);
		System.out.println(kategoria.getNimi());
		kategoriaDao.lisaaKategoria(kategoria);
	}
	
	@Override
	public void paivitaBudjetti(int kayttajaID, double budjetti) {
		kayttajaDao.paivitaBudjetti(kayttajaID, budjetti);
	}

	@Override
	public void lisaaKulu(String nimi, double hinta, LocalDate paivamaara, Kategoria kategoria, Kayttaja kayttaja, String kuvaus) {
		kulu = new Kulu(nimi, hinta, paivamaara, kategoria, kayttaja, kuvaus);
		kulut.lisaaKulu(kulu);
		if(kayttaja.getMaksimibudjetti() >= kulu.getSumma()) {
			System.out.println(kulu);
			kuluDao.lisaaKulu(kulu);
			paivitaBudjetti(kayttaja.getKayttajaID(),model.laskeBudjetti(kayttaja.getMaksimibudjetti(), hinta));
		} else {
    		System.out.println("Kulun summa on liian suuri budjettiin nähden.");
    	}
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
	
	public List<String> getKategorianimet(String omistaja) {
		List<String> kategoriaNimet = new ArrayList<String>();
		List<Kategoria> kategoriaObjektit = kategoriaDao.haeKategoriaLista();
		for(Kategoria k : kategoriaObjektit) {
			if(k.getOmistaja().equals(omistaja)) {
				kategoriaNimet.add(k.getNimi());
			}
		}
		return kategoriaNimet;
	}
	
	public Kategoria getKategoria(String nimi, String omistaja) {
		List<Kategoria> kategoriaObjektit2 = kategoriaDao.haeKategoriaLista();
		Kategoria etsitty = null;
		for(Kategoria k : kategoriaObjektit2) {
			if(k.getNimi().equals(nimi) && k.getOmistaja().equals(omistaja)) {
				etsitty = k;
			}
		}
		return etsitty;
	}
	
	public void muokkaaKulua(int id, Double summa, String nimi, String kuvaus) {
		kuluDao.muutaKulu(id, summa, nimi, kuvaus);
	}
	
	public void muokkaaKategoriaa(int id, String nimi) {
		kategoriaDao.muutaKategoria(id, nimi);
	}
	
	public void poistaKulu(int id) {
		kuluDao.poistaKulu(id);
	}
	
	public void poistaKategoria(int id, Kayttaja kayttaja) {
		Kategoria poistettava = kategoriaDao.haeKategoriat(id);
		Kategoria yleinen = null;
		List<Kulu> kulut = kuluDao.haeKulut(kayttaja.getKayttajaID());
		List<Kategoria> kategoriat = kategoriaDao.haeKategoriaLista();
		
		for(Kategoria kategoria : kategoriat) {
			if(kategoria.getNimi().equals("Yleinen")) {
				yleinen = kategoria;
			}
		}
				
		for(Kulu kulu : kulut) {
			if(kulu.getKategoria().equals(poistettava)) {
				kuluDao.muutaKulunKategoria(kulu.getKuluID(), yleinen);
			}
		}
		
		kategoriaDao.poistaKategoria(id);
	}
}