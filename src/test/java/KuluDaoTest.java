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

public class KuluDaoTest {
	
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
	
	@Test
	public void testHaeKulu() {

		Kayttaja kayttaja = new Kayttaja("Testi", 1000.0);
	    KayttajaDao kayttajaDao = new KayttajaDao();
	    kayttajaDao.lisaaKayttaja(kayttaja);
	    Kategoria kategoria = new Kategoria("Ruoka", "Testi");
	    KategoriaDao kategoriaDao = new KategoriaDao();
	    kategoriaDao.lisaaKategoria(kategoria);
	    Kulu kulu = new Kulu("Pizza", 10.0, LocalDate.now(), kategoria, kayttaja, "testi kulu");
	    kuluDao.lisaaKulu(kulu);

	    Kulu haeKulu = kuluDao.haeKulu(kulu.getKuluID());

	    assertEquals(kulu.getNimi(), haeKulu.getNimi());
	    assertEquals(kulu.getSumma(), haeKulu.getSumma(), 0.001);
	    assertEquals(kulu.getPaivamaara(), haeKulu.getPaivamaara());
	    assertEquals(kulu.getKategoria().getKategoriaID(), haeKulu.getKategoria().getKategoriaID());
	    assertEquals(kulu.getKayttaja().getKayttajaID(), haeKulu.getKayttaja().getKayttajaID());

	    kuluDao.poistaKulu(kulu.getKuluID());
	    kategoriaDao.poistaKategoria(kategoria.getKategoriaID());
	    kayttajaDao.poistaKayttaja(kayttaja.getKayttajaID());
	}

	@Test
	public void testMuutaKulu() {
	    Kayttaja kayttaja = new Kayttaja("Testi", 1000.0);
	    KayttajaDao kayttajaDao = new KayttajaDao();
	    kayttajaDao.lisaaKayttaja(kayttaja);

	    Kategoria kategoria = new Kategoria("Ruoka", "Testi");
	    KategoriaDao kategoriaDao = new KategoriaDao();
	    kategoriaDao.lisaaKategoria(kategoria);

	    Kulu kulu = new Kulu("Pizza", 10.0, LocalDate.now(), kategoria, kayttaja, "testi kulu");
	    kuluDao.lisaaKulu(kulu);
	    int kuluId = kulu.getKuluID();

	    boolean muokattu = kuluDao.muutaKulu(kuluId, 20.0, "Hampurilainen", "muokattu kulu");
	    assertTrue(muokattu);

	    Kulu muokattuKulu = kuluDao.haeKulu(kuluId);
	    assertNotNull(muokattuKulu);
	    assertEquals("Hampurilainen", muokattuKulu.getNimi());
	    assertEquals(20.0, muokattuKulu.getSumma(), 0.001);
	    assertEquals("muokattu kulu", muokattuKulu.getKuvaus());

	    kuluDao.poistaKulu(kuluId);
	    kayttajaDao.poistaKayttaja(kayttaja.getKayttajaID());
	    kategoriaDao.poistaKategoria(kategoria.getKategoriaID());
	}
	
	@Test
	public void testMuutaKulunKategoria() {
	    Kayttaja kayttaja = new Kayttaja("Testi", 1000.0);
	    KayttajaDao kayttajaDao = new KayttajaDao();
	    kayttajaDao.lisaaKayttaja(kayttaja);

	    Kategoria kategoria1 = new Kategoria("Ruoka", "Testi");
	    KategoriaDao kategoriaDao = new KategoriaDao();
	    kategoriaDao.lisaaKategoria(kategoria1);

	    Kategoria kategoria2 = new Kategoria("Vaatteet", "Testi");
	    kategoriaDao.lisaaKategoria(kategoria2);

	    Kulu kulu = new Kulu("Pizza", 10.0, LocalDate.now(), kategoria1, kayttaja, "testi kulu");
	    kuluDao.lisaaKulu(kulu);

	    boolean result = kuluDao.muutaKulunKategoria(kulu.getKuluID(), kategoria2);
	    assertTrue(result);

	    Kulu updatedKulu = kuluDao.haeKulu(kulu.getKuluID());
	    assertEquals(kategoria2.getKategoriaID(), updatedKulu.getKategoria().getKategoriaID());

	    kuluDao.poistaKulu(kulu.getKuluID());
	    kayttajaDao.poistaKayttaja(kayttaja.getKayttajaID());
	    kategoriaDao.poistaKategoria(kategoria1.getKategoriaID());
	    kategoriaDao.poistaKategoria(kategoria2.getKategoriaID());
	}
}