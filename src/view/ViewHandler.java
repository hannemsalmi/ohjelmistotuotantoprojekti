package view;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import controller.IKontrolleri;
import controller.Kontrolleri;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kayttajanHallinta.KayttajanHallinta;
import model.Kulu;

//Täältä löytyvät kaikki metodit, joita kutsutaan eri controllereista
public class ViewHandler implements IGUI{
	private IKontrolleri kontrolleri;
	private Stage stage;
	private ViewController aktiivinen;
	private BorderPane root;
	private AnchorPane sisalto;
	private boolean suomi;
	
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
		this.suomi = true;
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
		
		onkoKayttajaa();
		
		if(!(kontrolleri.getKayttajat().isEmpty())) {
			naytaEtusivu();
		}
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
		List<Kulu> kulut = kontrolleri.getKulut((kayttajanhallinta.lueKayttajaID()));
		for(Kulu kulu: kulut) {
			if(kulu.getKieli() == !suomi) {
				suomi = !suomi;
			}
		}
		return suomi;
	}
		
	public void setKieli(boolean kieli) {
		List<Kulu> kulut = kontrolleri.getKulut((kayttajanhallinta.lueKayttajaID()));
		for(Kulu kulu: kulut) {
			kulu.setKieli(kieli);
		}
		suomi = kieli;
	}
	
	public void onkoKayttajaa() {
		if (kontrolleri.getKayttaja(1) == null) {
			luoUusiKayttaja();
		}
		paivitaKayttaja();
	}
	
	public void luoUusiKayttaja() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/KayttajanLuonti.fxml"));
			Parent sisalto = loader.load();
			ViewController controller = loader.getController();
			controller.init(this);
			ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
			ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
			
			kayttajaStage = new Stage();
			kayttajaStage.setScene(new Scene(sisalto));
			if(suomi) {
				kayttajaStage.setTitle(finnish.getString("uusiKäyttäjä"));
			} else {
				kayttajaStage.setTitle(english.getString("uusiKäyttäjä"));
			}
    	    kayttajaStage.show();
		} catch (IOException e) {
			System.out.println("Ei onnistu uuden käyttäjän luonti");
		}
	}
	
	public void paivitaKayttaja() {
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
			
			ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
			ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
			
			muokkaaKuluaStage = new Stage();
			muokkaaKuluaStage.setScene(new Scene(sisalto));
			if(suomi) {
				muokkaaKuluaStage.setTitle(finnish.getString("muokkausTitle"));
			} else {
				muokkaaKuluaStage.setTitle(english.getString("muokkausTitle"));
			}
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
