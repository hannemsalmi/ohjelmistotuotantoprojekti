package controller;

import model.Budjettilaskuri;
import model.IBudjettilaskuri;
import view.GUI;
import view.IGUI;

public class Kontrolleri implements IKontrolleriVtoM, IKontrolleriMtoV{
	IGUI gui;
	IBudjettilaskuri model = new Budjettilaskuri();

	public Kontrolleri(IGUI gui) {
		this.gui = gui;
	}
	
}