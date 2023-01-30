package model;

import java.util.List;

public interface IBudjettilaskuri {
	public abstract List<Kulu> getKulut();
	public abstract double laskeBudjetti(double budjetti, double kulutettu);
	public abstract void lisaaKulu(Kulu kulu);
}
