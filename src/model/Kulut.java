package model;

import java.util.ArrayList;
import java.util.List;
import model.Kulu;

public class Kulut {
	
    private List<Kulu> kulut;

    public Kulut() {
        kulut = new ArrayList<>();
    }

    public void lisaaKulu(Kulu kulu) {
        kulut.add(kulu);
    }

    public List<Kulu> getKulut() {
        return kulut;
    }

}
