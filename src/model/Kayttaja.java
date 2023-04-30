package model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * This class holds the data of a user profile.
 * @author hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
@Entity
@Table(name="kayttajat")
public class Kayttaja {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int kayttajaID;
	private String nimimerkki;
	private double maksimibudjetti;
	
	public Kayttaja(String nimimerkki, double maksimibudjetti) {
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

	@Override
	public String toString() {
		return "Kayttaja [kayttajaID=" + kayttajaID + ", nimimerkki=" + nimimerkki + ", maksimibudjetti="
				+ maksimibudjetti + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == this) {
	        return true;
	    }
	    if (!(obj instanceof Kayttaja)) {
	        return false;
	    }
	    Kayttaja other = (Kayttaja) obj;
	    return Objects.equals(this.kayttajaID, other.kayttajaID) &&
	            Objects.equals(this.nimimerkki, other.nimimerkki) &&
	            Objects.equals(this.maksimibudjetti, other.maksimibudjetti);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(kayttajaID, nimimerkki, maksimibudjetti);
	}
}
