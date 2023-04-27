package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import dataAccessObjects.KategoriaDao;
import dataAccessObjects.KayttajaDao;
import dataAccessObjects.KuluDao;
import kayttajanHallinta.KayttajanHallinta;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Budjettilaskuri;
import model.IBudjettilaskuri;
import model.Kategoria;
import model.Kayttaja;
import model.Kulu;
import model.Kulut;
import view.EtusivuController;
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
		System.out.println(kulu);
		kuluDao.lisaaKulu(kulu);
	}
	
	public Kayttaja getKayttaja(int kayttajaid) {
		return kayttajaDao.haeKayttajat(kayttajaid);
	}
	

	public void poistaKayttajanTiedot(int kayttajaid) {
		kayttajaDao.poistaKayttajanTiedot(kayttajaid);
		
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
	
	public List<Kategoria> getKategoriat(String omistaja) {
		List<Kategoria> kategoriat = new ArrayList<Kategoria>();
		List<Kategoria> kaikkikategoriat = kategoriaDao.haeKategoriaLista();
		for(Kategoria k : kaikkikategoriat) {
			if(k.getOmistaja().equals(omistaja)) {
				kategoriat.add(k);
			}
		}
		return kategoriat;
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
	
	public boolean muokkaaKulua(int id, Double summa, String nimi, String kuvaus) {
		kuluDao.muutaKulu(id, summa, nimi, kuvaus);
		return true;
	}
	
	public boolean muokkaaKategoriaa(int id, String nimi) {
		kategoriaDao.muutaKategoria(id, nimi);
		return true;
	}
	
	public boolean muutaKulunKategoria(int kuluId, Kategoria uusiKategoria) {
		kuluDao.muutaKulunKategoria(kuluId, uusiKategoria);
		return true;
	}
	
	public boolean poistaKulu(int id) {
		kuluDao.poistaKulu(id);
		return true;
	}
	
	public boolean poistaKategoria(int id, Kayttaja kayttaja) {
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
		return true;
	}
	
	public String sendOstoslistaRequest() throws Exception {
	    URL url = new URL("https://budjettiserveri.eu.pythonanywhere.com/chatgpt"); // Replace with your deployed server's URL
	    //HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection(); 
	    String payload = "{You are a helpful assistant responding to users who are using their budget/expense tracking application."
	            + "You are to respond to this message only by filtering the given expenses into two lists: One shopping list of only food items and weekly consumables that you need to buy weekly from a store, no need to add prices to shopping list items."
	            + "And a second list which is a reminder list that helps the user to remember their regular bills before their due date and the due date should be included in the reminder list along with the price. "
	            + "Only put items to the reminder list that are in the provided expenses and are repeating static expences."
	            + "No need to include the categories for any of the expenses. "
	            + "Please provide me the shopping and reminder lists in exactly this format with no other symbols: (shopping list: item=itemName, item=itemName2),(reminder list: (item=itemName, price=price1, duedate=days.months.years),(item=itemName2, price=price2, duedate=days.months.years))"
	            + "You must only use these expenses to create the lists, if no expenses are available you can leave empty values to the provided format. Here are the expenses: "
	            + getKulut(KayttajanHallinta.getInstance().getKirjautunutKayttaja().getKayttajaID()).toString() + "}";
	    // Set up the connection properties
	    connection.setRequestProperty("Client-API-Key", "MahtiSalasanaKaikilleSovellusKayttajilleProxyServeriinJottaRandomitEivatVoiLahettaaPyyntojaServerilleIlmanSovellusta");
	    connection.setRequestMethod("POST");
	    connection.setRequestProperty("Content-Type", "application/json");
	    connection.setDoOutput(true);

	    // Write the payload to the request body
	    try (OutputStream outputStream = connection.getOutputStream()) {
	        outputStream.write(payload.getBytes(Charset.forName("UTF-8")));
	        outputStream.flush();
	    }

	    // Read the response
	    StringBuilder response = new StringBuilder();
	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            response.append(line);
	        }
	    }

	    // Close the connection and return the response
	    connection.disconnect();
	    System.out.println(response);
	    return response.toString();
	}

	
	public void setKategoriaDao(KategoriaDao kategoriaDao) {
		this.kategoriaDao = kategoriaDao;
	}
	
	public void setKayttajaDao(KayttajaDao kayttajaDao) {
		this.kayttajaDao = kayttajaDao;
	}
	
	public void setKuluDao(KuluDao kuluDao) {
		this.kuluDao = kuluDao;
	}

}