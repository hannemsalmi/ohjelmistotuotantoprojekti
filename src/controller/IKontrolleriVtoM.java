package controller;

import java.util.List;

import model.Kulu;

public interface IKontrolleriVtoM {

	public abstract List<Kulu> getKulut();

	public abstract void lisaaKulu();
	
}