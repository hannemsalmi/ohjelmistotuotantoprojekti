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

/**
 * A class that includes the methods which are used to control the views, their alternation and their cooperation.
 * @author hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
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

	/**
	 * Constructor for class ViewHandler
	 * @param stage A stage which is showing the graphical user interface.
	 */
	public ViewHandler(Stage stage) {
		this.stage = stage;
		kontrolleri = new Kontrolleri(this);
	}
	
	/**
	 * A method for starting the program.
	 * Sets the language as default(suomi) and checks if there is a user profile in the database.
	 * If not, the method opens a view where you can create a new profile.
	 */
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
	
	/**
	 * Opens Etusivu.fxml for the central part of the root pane.
	 */
	public void naytaEtusivu() {
		avaaSisalto("../view/Etusivu.fxml");
	}
	
	/**
	 * Opens Kulut.fxml for the central part of the root pane.
	 */
	public void naytaKulut() {
		avaaSisalto("../view/Kulut.fxml");
	}
	
	/**
	 * Opens the Diagrammi.fxml for the central part of the root pane.
	 */
	public void naytaDiagrammi() {
		avaaSisalto("../view/Diagrammi.fxml");
	}
	
	/**
	 * Opens the Ennuste.fxml for the central part of the root pane.
	 */
	public void naytaEnnuste() {
		avaaSisalto("../view/Ennuste.fxml");
	}

	/**
	 * Opens the Asetukset.fxml for the central part of the root pane.
	 */
	public void naytaAsetukset() {
		avaaSisalto("../view/Asetukset.fxml");
	}
	
	/**
	 * Includes and performs the concrete commands for opening a particular .fxml file
	 * @param nimi String value which contains the package and name of the .fxml file that will be opened.
	 */
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
	
	/**
	 * Refreshes the content of the AnchorPane based on the current active controller.
	 * This method should be called whenever the language is changed to make the change
	 * immediately visible on the AnchorPane.
	 */
	public void paivitaSisalto() {
	    if (aktiivinen != null) {
	        String fxmlPath = null;
	        if (aktiivinen instanceof EtusivuController) {
	            fxmlPath = "../view/Etusivu.fxml";
	        } else if (aktiivinen instanceof KulutController) {
	            fxmlPath = "../view/Kulut.fxml";
	        } else if (aktiivinen instanceof DiagrammiController) {
	            fxmlPath = "../view/Diagrammi.fxml";
	        } else if (aktiivinen instanceof EnnusteController) {
	            fxmlPath = "../view/Ennuste.fxml";
	        } else if (aktiivinen instanceof AsetuksetController) {
	            fxmlPath = "../view/Asetukset.fxml";
	        }
	        if (fxmlPath != null) {
	            avaaSisalto(fxmlPath);
	        }
	    }
	}

	
	/**
	 * Checks if the language is Finnish.
	 * @return A boolean value which is true if the language is Finnish and false if it is English.
	 */
	public boolean getKieli() {
		List<Kulu> kulut = kontrolleri.getKulut((kayttajanhallinta.lueKayttajaID()));
		for(Kulu kulu: kulut) {
			if(kulu.getKieli() == !suomi) {
				suomi = !suomi;
			}
		}
		return suomi;
	}
		
	/**
	 * Sets the selected language as the language of the program.
	 * @param kieli Boolean value which is true if the language is Finnish and false if the language is English.
	 */
	public void setKieli(boolean kieli) {
		List<Kulu> kulut = kontrolleri.getKulut((kayttajanhallinta.lueKayttajaID()));
		for(Kulu kulu: kulut) {
			kulu.setKieli(kieli);
		}
		suomi = kieli;
		paivitaSisalto();
	}
	
	/**
	 * A method which checks if there is a user profile in database.
	 * If not, it opens a view where a user can create a new profile.
	 * Sets the newly created user as active user or keeps the active user as same if no new user is created.
	 */
	public void onkoKayttajaa() {
		if (kontrolleri.getKayttaja(1) == null) {
			luoUusiKayttaja();
		}
		paivitaKayttaja();
	}
	
	/**
	 * A method which creates a stage where the user can create a new user profile.
	 */
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
	
	/**
	 * Updates the current user as logged in user.
	 */
	public void paivitaKayttaja() {
		kayttajanhallinta.setKirjautunutKayttaja(kontrolleri.getKayttaja(kayttajanhallinta.lueKayttajaID()));
	}
	
	/**
	 * A method which creates a stage where the user can modify an already created expense.
	 */
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

	/**
	 * Forwards KayttajanHallinta to another classes
	 * @return Instance of kayttajanHallinta.
	 */
	public KayttajanHallinta getKayttajanhallinta() {
		return kayttajanhallinta;
	}

	/**
	 * Forwards controller to another classes
	 * @return IKontrolleri which all the graphical user interface classes are using.
	 */
	public IKontrolleri getKontrolleri() {
		return kontrolleri;
	}
	
	/**
	 * Forwards kayttajaStage to KayttajanLuontiController where it is used to 
	 * close the window after a new user has been created.
	 * @return Stage kayttajaStage
	 */
	public Stage getKayttajaStage() {
		return kayttajaStage;
	}
	
	/**
	 * Forwards muokkaaKuluaStage to KulunMuokkausController where it is used to 
	 * close the window after an expense has been modified.
	 * @return Stage kayttajaStage
	 */
	public Stage getMuokkaaKuluaStage() {
		return muokkaaKuluaStage;
	}

	/**
	 * Forwards the now active controller to another class.
	 * Used only for forwarding KulutController to KulunMuokkausController where it is used to
	 * update expense list to reflect changes the user made.
	 * @return ViewCOntroller aktiivinen
	 */
	public ViewController getAktiivinen() {
		return aktiivinen;
	}
}
