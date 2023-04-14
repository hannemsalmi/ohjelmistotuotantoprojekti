package view;

import java.io.IOException;
import java.util.List;

import controller.IKontrolleri;
import controller.Kontrolleri;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kayttajanHallinta.KayttajanHallinta;

//Täältä löytyvät kaikki metodit, joita kutsutaan eri controllereista
public class ViewHandler implements IGUI{
	private IKontrolleri kontrolleri;
	private Stage stage;
	private ViewController aktiivinen;
	private BorderPane root;
	private AnchorPane sisalto;
	private boolean suomi = true;
	
	private KayttajanHallinta kayttajanhallinta = KayttajanHallinta.getInstance();
	private Stage kayttajaStage;
	private Stage muokkaaKuluaStage;

	public ViewHandler(Stage stage) {
		this.stage = stage;
		kontrolleri = new Kontrolleri(this);
	}
	
	public void start() {
		try {
		Scene scene;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../view/Ulkoasu.fxml"));
		
		root = loader.load();
		System.out.println(root);
		
		ViewController controller = loader.getController();
		controller.init(this);
		
		scene = new Scene(root);
		stage.setTitle("Budjettisovellus");
		stage.setScene(scene);
		stage.show();
		
		} catch (IOException e) {
			System.out.println("Ei onnistunut rootin avaus");
		}
		
		naytaEtusivu();
		onkoKayttajaa();
	}
	
	public void naytaEtusivu() {
		avaaSisalto("../view/Etusivu.fxml");
	}
	
	public void naytaKulut() {
		avaaSisalto("../view/Kulut.fxml");
	}
	
	public void naytaDiagrammi() {
		avaaSisalto("../view/Diagrammi.fxml");
	}
	
	public void naytaEnnuste() {
		avaaSisalto("../view/Ennuste.fxml");
	}

	public void naytaAsetukset() {
		avaaSisalto("../view/Asetukset.fxml");
	}
	
	private void avaaSisalto(String nimi) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(nimi));
			sisalto = (AnchorPane)loader.load();
			root.setCenter(sisalto);
			ViewController controller = loader.getController();
			aktiivinen = controller;
			controller.init(this);
		} catch (IOException e) {
			System.out.println("Ei onnistunut sisällön avaus");
		}
	}
	
	public boolean getKieli() {
		return suomi;
	}
		
	public void setKieli(boolean kieli) {
		suomi = kieli;
	}
	
	public void onkoKayttajaa() {
		if (kontrolleri.getKayttaja(1) == null) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../view/KayttajanLuonti.fxml"));
				Parent sisalto = loader.load();
				ViewController controller = loader.getController();
				controller.init(this);
				
				kayttajaStage = new Stage();
				kayttajaStage.setScene(new Scene(sisalto));
	            kayttajaStage.setTitle("Luo uusi käyttäjä");
	    	    kayttajaStage.show();
			} catch (IOException e) {
				System.out.println("Ei onnistu uuden käyttäjän luonti");
			}
		}
		kayttajanhallinta.setKirjautunutKayttaja(kontrolleri.getKayttaja(kayttajanhallinta.lueKayttajaID()));
	}
	
	public void avaaKulunMuokkaus(int kuluId) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/KulunMuokkaus.fxml"));
			Parent sisalto = loader.load();
			ViewController controller = loader.getController();
			controller.init(this);
			//downcastaus controllerista
			((KulunMuokkausController)controller).setKuluId(kuluId);
			
			muokkaaKuluaStage = new Stage();
			muokkaaKuluaStage.setScene(new Scene(sisalto));
			muokkaaKuluaStage.setTitle("Muokkaa kulua");
			muokkaaKuluaStage.show();
		} catch (IOException e) {
			System.out.println("Ei onnistu kulun muokkaus");
		}
	}

	public KayttajanHallinta getKayttajanhallinta() {
		return kayttajanhallinta;
	}

	public IKontrolleri getKontrolleri() {
		return kontrolleri;
	}
	
	public Stage getKayttajaStage() {
		return kayttajaStage;
	}
	
	public Stage getMuokkaaKuluaStage() {
		return muokkaaKuluaStage;
	}

	public ViewController getAktiivinen() {
		return aktiivinen;
	}
}
