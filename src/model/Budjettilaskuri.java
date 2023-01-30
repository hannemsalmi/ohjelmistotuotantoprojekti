package model;

import java.util.List;

public class Budjettilaskuri implements IBudjettilaskuri {
	Kulut kulut = new Kulut();
	
	@Override
	public List<Kulu> getKulut() {
		return kulut.getKulut();
	}
	
	public void lisaaKulu(Kulu kulu) {
		kulut.lisaaKulu(kulu);
	}

	@Override
	public double laskeBudjetti(double budjetti, double kulutettu) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
