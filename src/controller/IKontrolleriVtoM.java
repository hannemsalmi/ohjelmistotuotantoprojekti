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
	public Kayttaja getKayttaja(int kayttajaid);
}