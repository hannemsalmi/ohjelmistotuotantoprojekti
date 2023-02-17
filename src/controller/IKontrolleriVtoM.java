package controller;

import java.util.List;
import java.time.LocalDate;
import model.Kategoria;
import model.Kayttaja;
import model.Kulu;
import model.Kulut;

public interface IKontrolleriVtoM {
	public abstract List<Kulu> getKulut(int kayttajaid);
	public abstract Kulu getKulu(int kuluid);
	public abstract void lisaaKulu(String nimi, double hinta, LocalDate paivamaara, Kategoria kategoria, Kayttaja kayttaja, String kuvaus);
	public abstract void lisaaKategoria(String nimi);
	public abstract Kayttaja getKayttaja(int kayttajaid);
	public abstract void lisaaKayttaja(String nimi, double budjetti);
	public abstract List<String> getKayttajat();
	public abstract List<String> getKategorianimet();
	public abstract Kategoria getKategoria(String nimi);
	public abstract void paivitaBudjetti(int kayttajaID, double budjetti);
	public abstract void muokkaaKulua(int id, Double summa, String nimi, String kuvaus);
	public abstract void poistaKulu(int id);
}