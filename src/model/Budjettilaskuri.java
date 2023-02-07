package model;

import java.util.List;

public class Budjettilaskuri implements IBudjettilaskuri {
	Kulut kulut = new Kulut();
	
	@Override
	public List<Kulu> getKulut() {
		return kulut.getKulut();
	}

	@Override
	public double laskeBudjetti(double budjetti, double kulutettu) {
		return budjetti-kulutettu;
	}
	

}
