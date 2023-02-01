package model;

public class Testi {
	
	public static void main(String[] args) {
		
		Budjettilaskuri laskuri = new Budjettilaskuri();
		Kayttaja kayttaja = new Kayttaja();
		kayttaja.setMaksimibudjetti(800);
		System.out.println(laskuri.laskeBudjetti(kayttaja.getMaksimibudjetti(), 500));
		
	}

}
