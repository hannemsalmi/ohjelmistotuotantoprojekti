package model;

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
	
	public Kategoria(String nimi) {
		this.nimi = nimi;
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

}