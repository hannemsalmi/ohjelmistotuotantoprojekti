package test.java;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccessObjects.KayttajaDao;
import model.Kayttaja;

public class KayttajaTest {
    private KayttajaDao dao;
    private Kayttaja kayttaja;

    @Before
    public void setUp() {
        dao = new KayttajaDao();
        kayttaja = new Kayttaja();
        kayttaja.setNimimerkki("Testi Kayttaja");
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
        assertEquals("Testi Kayttaja", haettuKayttaja.getNimimerkki());
        assertEquals(1000.0, haettuKayttaja.getMaksimibudjetti(), 0.01);
    }
}