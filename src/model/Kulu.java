package model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    @ManyToOne (cascade=CascadeType.ALL)
    private Kategoria kategoria;
    @ManyToOne (cascade=CascadeType.ALL)
    private Kayttaja kayttaja;
    private String kuvaus;

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

	@Override
	public String toString() {
		return  "Kulu: " + nimi + ", Summa: " + String.format("%.2f",summa) + " e, Päivämäärä: " + paivamaara
				+ ", Kategoria: " + kategoria.getNimi() + ", Kuvaus: " + kuvaus;
	}
    
    
    
}