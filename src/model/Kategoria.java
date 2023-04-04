package model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="kategoriat")
public class Kategoria{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int kategoriaID;
	private String nimi;
	private String omistaja;
	
	public Kategoria(String nimi, String omistaja) {
		this.nimi = nimi;
		this.omistaja = omistaja;
	}
	public Kategoria() {
	}
	public int getKategoriaID() {
		return kategoriaID;
	}

	public void setKategoriaID(int kategoriaID) {
		this.kategoriaID = kategoriaID;
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
	
	public String getOmistaja() {
		return omistaja;
	}
	public void setOmistaja(String omistaja) {
		this.omistaja = omistaja;
	}
	@Override
	public String toString() {
		return "Kategoria [kategoriaID=" + kategoriaID + ", nimi=" + nimi + ", omistaja=" + omistaja + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == this) {
	        return true;
	    }
	    if (!(obj instanceof Kategoria)) {
	        return false;
	    }
	    Kategoria other = (Kategoria) obj;
	    return Objects.equals(this.nimi, other.nimi) &&
	            Objects.equals(this.omistaja, other.omistaja) &&
	            Objects.equals(this.kategoriaID, other.kategoriaID);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(nimi, omistaja, kategoriaID);
	}
}