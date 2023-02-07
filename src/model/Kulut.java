package model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import model.Kulu;

public class Kulut {
    private List<Kulu> kulut;

    public Kulut() {
        this.kulut = new ArrayList<>();
    }
    
    public void lisaaKulu(Kulu kulu) {
    	if(kulu.getSumma() <= kulu.getKayttaja().getMaksimibudjetti()) {
    		kulut.add(kulu);
    	}
    }
    
    public void lisaaKulut(List<Kulu> kulut) {
    	this.kulut = kulut;
    }

    public List<Kulu> getKulut() {
        return kulut;
    }
    
    public String toString() {
    	return kulut.toString();
    }

}
