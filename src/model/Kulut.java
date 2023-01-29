package model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import model.Kulu;
@Entity
public class Kulut {
	@OneToMany(cascade = CascadeType.ALL)
    private List<Kulu> kulut;

    public Kulut() {
        this.kulut = new ArrayList<>();
    }
    
    public void lisaaKulu(Kulu kulu) {
        kulut.add(kulu);
    }

    public List<Kulu> getKulut() {
        return kulut;
    }

}
