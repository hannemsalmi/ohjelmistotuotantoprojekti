package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * This class holds the data of an expense.
 * @authors hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
@Entity
@Table(name="kulut")
public class Kulu {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int kuluID;
    private String nimi;
    private double summa;
    private LocalDate paivamaara;
    @ManyToOne (cascade=CascadeType.PERSIST)
    private Kategoria kategoria;
    @ManyToOne (cascade=CascadeType.PERSIST)
    private Kayttaja kayttaja;
    private String kuvaus;
    private boolean kieli = true;

    public Kulu(String nimi, double summa, LocalDate paivamaara, Kategoria kategoria, Kayttaja kayttaja, String kuvaus) {
    	this.nimi = nimi;
        this.summa = summa;
        this.paivamaara = paivamaara;
        this.kategoria = kategoria;
        this.kayttaja = kayttaja; 	
        this.kuvaus = kuvaus;
    }
    
    public Kulu() {
	}

	public String getKuvaus() {
		return kuvaus;
	}

	public void setKuvaus(String kuvaus) {
		this.kuvaus = kuvaus;
	}

	public Kategoria getKategoria() {
		return kategoria;
	}

	public void setKategoria(Kategoria kategoria) {
		this.kategoria = kategoria;
	}

	public Kayttaja getKayttaja() {
		return kayttaja;
	}

	public void setKayttaja(Kayttaja kayttaja) {
		this.kayttaja = kayttaja;
	}

	public int getKuluID() {
		return kuluID;
	}

	public void setKuluID(int kuluID) {
		this.kuluID = kuluID;
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public double getSumma() {
		return summa;
	}

	public void setSumma(double summa) {
		this.summa = summa;
	}

	public LocalDate getPaivamaara() {
		return paivamaara;
	}

	public void setPaivamaara(LocalDate paivamaara) {
		this.paivamaara = paivamaara;
	}
	
	public boolean getKieli() {
		return kieli;
	}
	
	public void setKieli(boolean kieli) {
		this.kieli = kieli;
	}

	@Override
	public String toString() {
		ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		if(kieli) {
			return finnish.getString("tkNimi") + nimi + finnish.getString("tkArvo") +
		            String.format("%.2f", summa) + finnish.getString("tkPvm") +
		            paivamaara.format(dateFormatter) + finnish.getString("tkKategoria") +
		            kategoria.getNimi() + finnish.getString("tkKuvaus") + kuvaus;
		} else {
			return english.getString("tkNimi") + nimi + english.getString("tkArvo") + 
					String.format("%.2f",summa) + english.getString("tkPvm") + paivamaara + 
					english.getString("tkKategoria") + kategoria.getNimi() + english.getString("tkKuvaus") + 
					kuvaus;
		}
	}
    
	@Override
	public boolean equals(Object obj) {
	    if (obj == this) {
	        return true;
	    }
	    if (!(obj instanceof Kulu)) {
	        return false;
	    }
	    Kulu other = (Kulu) obj;
	    return Objects.equals(this.nimi, other.nimi) &&
	            Objects.equals(this.summa, other.summa) &&
	            Objects.equals(this.paivamaara, other.paivamaara) &&
	            Objects.equals(this.kategoria, other.kategoria) &&
	            Objects.equals(this.kayttaja, other.kayttaja) &&
	            Objects.equals(this.kuvaus, other.kuvaus) &&
	            Objects.equals(this.kuluID, other.kuluID);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(nimi, summa, paivamaara, kategoria, kayttaja, kuvaus, kuluID);
	}
}