package controller;

import java.util.List;
import java.time.LocalDate;
import model.Kategoria;
import model.Kayttaja;
import model.Kulu;
import model.Kulut;

public interface IKontrolleri {
	public abstract List<Kulu> getKulut(int kayttajaid);
	public abstract Kulu getKulu(int kuluid);
	public abstract void lisaaKulu(String nimi, double hinta, LocalDate paivamaara, Kategoria kategoria, Kayttaja kayttaja, String kuvaus);
	public abstract Kayttaja getKayttaja(int kayttajaid);
	public abstract void lisaaKayttaja(String nimi, double budjetti);
	public abstract List<String> getKayttajat();
	public abstract List<String> getKategorianimet(String omistaja);
	public abstract Kategoria getKategoria(String nimi, String omistaja);
	public abstract void paivitaBudjetti(int kayttajaID, double budjetti);
	public abstract boolean muokkaaKulua(int id, Double summa, String nimi, String kuvaus);
	public abstract boolean muokkaaKategoriaa(int id, String nimi);
	public abstract boolean muutaKulunKategoria(int kuluId, Kategoria uusiKategoria);
	public abstract boolean poistaKulu(int id);
	public abstract boolean poistaKategoria(int id, Kayttaja kayttaja);
	public abstract void lisaaKategoria(String nimi, String omistaja);
	public abstract List<Kategoria> getKategoriat(String omistaja);
	public abstract void poistaKayttajanTiedot(int kayttajanID);
}