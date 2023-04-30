package model;

/**
 * Interface for class Budjettilaskuri. Calculates the remaining budget for a month.
 * @author hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
public interface IBudjettilaskuri {
	/**
	 * Calculates the remaining budget for a month.
	 */
	public abstract double laskeBudjetti(double budjetti, double kulutettu);
}
