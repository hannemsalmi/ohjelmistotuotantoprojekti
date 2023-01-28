package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="kayttajat")
public class Kayttaja {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int kayttajaID;
	private String nimimerkki;
	private double maksimibudjetti;
	
	public Kayttaja(int kayttajaID, String nimimerkki, double maksimibudjetti) {
		this.kayttajaID = kayttajaID;
		this.nimimerkki = nimimerkki;
		this.maksimibudjetti = maksimibudjetti;
	}
	
	public Kayttaja() {
	}
	
	public int getKayttajaID() {
		return kayttajaID;
	}
	public void setKayttajaID(int kayttajaID) {
		this.kayttajaID = kayttajaID;
	}
	public String getNimimerkki() {
		return nimimerkki;
	}
	public void setNimimerkki(String nimimerkki) {
		this.nimimerkki = nimimerkki;
	}
	public double getMaksimibudjetti() {
		return maksimibudjetti;
	}
	public void setMaksimibudjetti(double maksimibudjetti) {
		this.maksimibudjetti = maksimibudjetti;
	}
}
