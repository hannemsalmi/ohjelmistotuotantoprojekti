package test.java;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccessObjects.KategoriaDao;
import dataAccessObjects.KayttajaDao;
import dataAccessObjects.KuluDao;
import model.Kulu;
import model.Kategoria;
import model.Kayttaja;

public class KuluTest {
	
	private KuluDao kuluDao;
	
	@Before
	public void setUp() {
		kuluDao = new KuluDao();
	}

	@After
	public void tearDown() {
		kuluDao = null;
	}
	
	@Test
	public void testLisaaKulu() {
		
		Kayttaja kayttaja = new Kayttaja("Testi", 1000.0);
		KayttajaDao kayttajaDao = new KayttajaDao();
		kayttajaDao.lisaaKayttaja(kayttaja);
		
		Kategoria kategoria = new Kategoria("Ruoka", "Testi");
		KategoriaDao kategoriaDao = new KategoriaDao();
		kategoriaDao.lisaaKategoria(kategoria);
	
		Kulu kulu = new Kulu("Pizza", 10.0, LocalDate.now(), kategoria, kayttaja, "testi kulu");
		kuluDao.lisaaKulu(kulu);
		
		List<Kulu> kulut = kuluDao.haeKulut(kayttaja.getKayttajaID());
		assertEquals(1, kulut.size());
		Kulu addedKulu = kulut.get(0);
		assertEquals(10.0, addedKulu.getSumma(), 0.001);
		assertEquals("Pizza", addedKulu.getNimi());
		assertEquals(LocalDate.now(), addedKulu.getPaivamaara());
		assertEquals(kayttaja.getKayttajaID(), addedKulu.getKayttaja().getKayttajaID());
		assertEquals(kategoria.getKategoriaID(), addedKulu.getKategoria().getKategoriaID());
		
		kuluDao.poistaKulu(addedKulu.getKuluID());
		kayttajaDao.poistaKayttaja(kayttaja.getKayttajaID());
		kategoriaDao.poistaKategoria(kategoria.getKategoriaID());
	}
}