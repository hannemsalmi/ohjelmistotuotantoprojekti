package model;

public interface IBudjettilaskuri {
	public abstract String[] getKulut();
	public abstract double laskeBudjetti(double budjetti, double kulutettu);
}
