package model;

/**
 * 	This class handles the calculation of the remaining budget.
 *	@author hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
public class Budjettilaskuri implements IBudjettilaskuri {
	
	@Override
	public double laskeBudjetti(double budjetti, double kulutettu) {
		return budjetti-kulutettu;
	}
}
