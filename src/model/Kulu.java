package model;

import java.time.LocalDate;

public class Kulu {
    private String nimi;
    private double summa;
    private LocalDate paivamaara;

    public Kulu(String nimi, double summa, LocalDate paivamaara) {
        this.nimi = nimi;
        this.summa = summa;
        this.paivamaara = paivamaara;
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
    
    
    
}