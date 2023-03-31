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
import model.Kategoria;
import model.Kayttaja;
import model.Kulu; 

public class KayttajaTest {
    private KayttajaDao dao;
    private KuluDao kuluDao;
    private KategoriaDao kategoriaDao;
    private Kayttaja kayttaja;

    @Before
    public void setUp() {
        dao = new KayttajaDao();
        kuluDao = new KuluDao();
        kategoriaDao = new KategoriaDao();
        kayttaja = new Kayttaja();
        kayttaja.setNimimerkki("Testikayttaja");
        kayttaja.setMaksimibudjetti(1000.0);
    }

    @After
    public void cleanUp() {
        dao.poistaKayttaja(kayttaja.getKayttajaID());
    }

    @Test
    public void testLisaaKayttaja() {
        dao.lisaaKayttaja(kayttaja);
        Kayttaja haettuKayttaja = dao.haeKayttajat(kayttaja.getKayttajaID());
        
        assertEquals("Testikayttaja", haettuKayttaja.getNimimerkki());
        assertEquals(1000.0, haettuKayttaja.getMaksimibudjetti(), 0.01);
        
        dao.poistaKayttaja(kayttaja.getKayttajaID());
    }
    
    @Test
    public void testPaivitaBudjetti() {
    	dao.lisaaKayttaja(kayttaja);

        double uusiBudjetti = 2000.0;
        dao.paivitaBudjetti(kayttaja.getKayttajaID(), uusiBudjetti);

        Kayttaja paivitettyKayttaja = dao.haeKayttajat(kayttaja.getKayttajaID());

        assertEquals(uusiBudjetti, paivitettyKayttaja.getMaksimibudjetti(), 0.01);
        
        dao.poistaKayttaja(kayttaja.getKayttajaID());
    }
    
    @Test
    public void testHaeKayttajaLista() {

        dao.lisaaKayttaja(kayttaja);
        
        Kayttaja kayttaja2 = new Kayttaja();
        kayttaja2.setNimimerkki("Testikayttaja2");
        kayttaja2.setMaksimibudjetti(750.0);
        dao.lisaaKayttaja(kayttaja2);
        
        List<Kayttaja> kayttajat = dao.haeKayttajaLista();
        assertTrue(kayttajat.contains(kayttaja));
        assertTrue(kayttajat.contains(kayttaja2));
        
        dao.poistaKayttaja(kayttaja.getKayttajaID());
        dao.poistaKayttaja(kayttaja2.getKayttajaID());
    }
    
    @Test
    public void testPoistaKayttajanTiedot() {
        dao.lisaaKayttaja(kayttaja);

        Kategoria kategoria = new Kategoria();
        kategoria.setNimi("Ruoka");
        kategoria.setOmistaja(kayttaja.getNimimerkki());
        kategoriaDao.lisaaKategoria(kategoria);
        int kategoriaID = kategoria.getKategoriaID();

        Kulu kulu = new Kulu();
        kulu.setKategoria(kategoria);
        kulu.setSumma(10.0);
        kulu.setPaivamaara(LocalDate.now());
        kulu.setKuvaus("Testikulu");
        kulu.setKayttaja(kayttaja);
        kuluDao.lisaaKulu(kulu);

        dao.poistaKayttajanTiedot(kayttaja.getKayttajaID());

        List<Kategoria> kategoriat = kategoriaDao.haeKategoriaLista();
        assertTrue(kategoriat.stream().noneMatch(k -> k.getOmistaja().equals(kayttaja.getNimimerkki())));
        assertTrue(kuluDao.haeKulut(kayttaja.getKayttajaID()).isEmpty());
    }
    
    @Test
    public void testPoistaKayttaja() {
        dao.lisaaKayttaja(kayttaja);

        dao.poistaKayttaja(kayttaja.getKayttajaID());

        assertNull(dao.haeKayttajat(kayttaja.getKayttajaID()));
    }
}