package controller;

import model.Budjettilaskuri;
import model.IBudjettilaskuri;
import model.Kategoria;
import model.Kulu;
import view.GUI;
import view.IGUI;

public class Kontrolleri implements IKontrolleriVtoM, IKontrolleriMtoV{
	IGUI gui;
	IBudjettilaskuri model = new Budjettilaskuri();
	Kulu kulu;
	Kategoria kategoria;

	public Kontrolleri(IGUI gui) {
		this.gui = gui;
	}

	@Override
	public Kulu lisaaKulu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Kategoria lisaaKategoria() {
		// TODO Auto-generated method stub
		return null;
	}
	
}