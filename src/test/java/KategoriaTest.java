package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
    public void testGetKategoriat() {
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
        assertTrue("Kategorialistasta ei löydy yleiskategoriaa 'Yleinen'", loytyiYleinen);
    }

}