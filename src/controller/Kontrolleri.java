package controller;

import java.time.LocalDate;

import dataAccessObjects.KategoriaDao;
import dataAccessObjects.KayttajaDao;
import dataAccessObjects.KuluDao;
import model.Budjettilaskuri;
import model.IBudjettilaskuri;
import model.Kategoria;
import model.Kayttaja;
import model.Kulu;
import model.Kulut;
import view.GUI;
import view.IGUI;

public class Kontrolleri implements IKontrolleriVtoM, IKontrolleriMtoV{
	private IGUI gui;
	private IBudjettilaskuri model = new Budjettilaskuri();
	private KategoriaDao kategoriaDao = new KategoriaDao();
	private KayttajaDao kayttajaDao = new KayttajaDao();
	private KuluDao kuluDao = new KuluDao();
	private Kategoria kategoria;
	private Kayttaja kayttaja;
	private Kulut kulut = new Kulut();
	public Kontrolleri(IGUI gui) {
		this.gui = gui;
	}

}