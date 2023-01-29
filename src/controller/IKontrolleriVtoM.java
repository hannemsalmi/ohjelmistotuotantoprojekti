package controller;

import java.time.LocalDate;

import model.Kategoria;
import model.Kayttaja;
import model.Kulu;

public interface IKontrolleriVtoM {
	public abstract void lisaaKulu(String nimi, double hinta, LocalDate paivamaara, Kategoria kategoria, Kayttaja kayttaja, String kuvaus);
	public abstract void lisaaKategoria(String nimi);
}