package test.java;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controller.Kontrolleri;
import dataAccessObjects.KategoriaDao;
import dataAccessObjects.KayttajaDao;
import dataAccessObjects.KuluDao;
import model.Kategoria;
import model.Kayttaja;
import model.Kulu;
import view.IGUI;

public class KontrolleriTest {

    private Kontrolleri kontrolleri;
    private IGUI gui;
    private KategoriaDao kategoriaDao;
    private KayttajaDao kayttajaDao;
    private KuluDao kuluDao;

    @Before
    public void setUp() {
        gui = mock(IGUI.class);
        kategoriaDao = mock(KategoriaDao.class);
        kayttajaDao = mock(KayttajaDao.class);
        kuluDao = mock(KuluDao.class);
        kontrolleri = new Kontrolleri(gui);
        kontrolleri.setKategoriaDao(kategoriaDao);
        kontrolleri.setKayttajaDao(kayttajaDao);
        kontrolleri.setKuluDao(kuluDao);
    }

    @Test
    public void testLisaaKategoria() {
        String nimi = "Ruoka";
        String omistaja = "Matti";
        Kategoria kategoria = new Kategoria(nimi, omistaja);
        when(kategoriaDao.lisaaKategoria(kategoria)).thenReturn(true);
        kontrolleri.lisaaKategoria(nimi, omistaja);
        verify(kategoriaDao).lisaaKategoria(kategoria);
    }
    
    @Test
    public void testLisaaKulu() {
        String nimi = "Ruokaostokset";
        double hinta = 20.0;
        LocalDate paivamaara = LocalDate.now();
        Kategoria kategoria = new Kategoria("Ruoka", "Matti");
        Kayttaja kayttaja = new Kayttaja("Matti", 500.0);
        String kuvaus = "Ostettu kaupasta";
        Kulu kulu = new Kulu(nimi, hinta, paivamaara, kategoria, kayttaja, kuvaus);
        when(kuluDao.lisaaKulu(kulu)).thenReturn(true);
        kontrolleri.lisaaKulu(nimi, hinta, paivamaara, kategoria, kayttaja, kuvaus);
        verify(kuluDao).lisaaKulu(kulu);
    }
    
    @Test
    public void testPaivitaBudjetti() {
        int kayttajaID = 1;
        double budjetti = 500.0;
        when(kayttajaDao.paivitaBudjetti(kayttajaID, budjetti)).thenReturn(true);
        kontrolleri.paivitaBudjetti(kayttajaID, budjetti);
        verify(kayttajaDao).paivitaBudjetti(kayttajaID, budjetti);
    }
}