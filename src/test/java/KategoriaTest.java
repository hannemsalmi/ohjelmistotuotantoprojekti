package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccessObjects.KategoriaDao;
import model.Kategoria;

public class KategoriaTest {

    private KategoriaDao kategoriaDao;
    private int kategoriaID;

    @Before
    public void setUp() throws Exception {
        kategoriaDao = new KategoriaDao();
    }

    @Test
    public void testHaeKategoriat() {
        List<Kategoria> kategoriat = kategoriaDao.haeKategoriaLista();
        
        //Testataan, ettei kategorialista ole null tai tyhjä.
        assertNotNull(kategoriat);
        assertFalse(kategoriat.isEmpty());

        //Testataan, että listalta löytyy "Yleinen" kategoria. 
        boolean loytyiYleinen = false;
        for (Kategoria k : kategoriat) {
            if (k.getNimi().equals("Yleinen")) {
                loytyiYleinen = true;
                break;
            }
        }
        assertTrue("Kategoria listasta ei löydy yleiskategoriaa 'Yleinen'", loytyiYleinen);
    }
    
    @Test
    public void testLisaaKategoria() {
        Kategoria kategoria = new Kategoria("Testi", "Testi");
        kategoriaDao.lisaaKategoria(kategoria);
        Kategoria retrievedKategoria = kategoriaDao.haeKategoriat(kategoria.getKategoriaID());

        assertNotNull(retrievedKategoria);
        assertEquals("Testi", retrievedKategoria.getNimi());
        
        kategoriaDao.poistaKategoria(kategoria.getKategoriaID());
    }
    
    @Test
    public void testMuutaKategoria() {
        Kategoria kategoria = new Kategoria("Testi", "Testi");
        kategoriaDao.lisaaKategoria(kategoria);

        kategoriaDao.muutaKategoria(kategoria.getKategoriaID(), "Uusi nimi");
        Kategoria updatedKategoria = kategoriaDao.haeKategoriat(kategoria.getKategoriaID());

        assertNotNull(updatedKategoria);
        assertEquals("Uusi nimi", updatedKategoria.getNimi());
        
        kategoriaDao.poistaKategoria(kategoria.getKategoriaID());
    }
    
    @Test
    public void testPoistaKategoria() {
        Kategoria kategoria = new Kategoria("Testi", "Testi");
        kategoriaDao.lisaaKategoria(kategoria);

        kategoriaDao.poistaKategoria(kategoria.getKategoriaID());
        Kategoria deletedKategoria = kategoriaDao.haeKategoriat(kategoria.getKategoriaID());

        assertNull(deletedKategoria);
    }
}

