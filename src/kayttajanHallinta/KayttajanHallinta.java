package kayttajanHallinta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import model.Kayttaja;

/**
 * This class is responsible for handling the user profiles and their changes.
 * @authors hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
public class KayttajanHallinta {
	private static  KayttajanHallinta INSTANCE = null;
	private Kayttaja kirjautunutKayttaja = null;
	
	private KayttajanHallinta() {
	}
	
	/**
	 * Returns the instance
	 * @return KayttajanHallinta instance
	 */
	public static KayttajanHallinta getInstance()
    {
        if (INSTANCE == null)
        	INSTANCE = new KayttajanHallinta();
  
        return INSTANCE;
    }

	public Kayttaja getKirjautunutKayttaja() {
		return kirjautunutKayttaja;
	}

	public void setKirjautunutKayttaja(Kayttaja kirjautunutKayttaja) {
		this.kirjautunutKayttaja = kirjautunutKayttaja;
	}
	
	/**
	 * Writes the ID of the current user into a separate file.
	 * @param kayttajaID The ID being written to the separate file.
	 */
	public void kirjoitaKayttajaID(int kayttajaID) {
		  try {
		    BufferedWriter writer = new BufferedWriter(new FileWriter("edellisenKayttajanID.txt"));
		    writer.write(Integer.toString(kayttajaID));
		    writer.close();
		  } catch (IOException e) {
		    e.printStackTrace();
		  }
		}
	
	/**
	 * Reads the ID of the previous user from a separate file.
	 * @return The ID which have been read from the file.
	 */
	public int lueKayttajaID() {
	  int kayttajaID = 0;
	  try {
	    BufferedReader reader = new BufferedReader(new FileReader("edellisenKayttajanID.txt"));
	    kayttajaID = Integer.parseInt(reader.readLine());
	    reader.close();
	  } catch (IOException e) {
	    e.printStackTrace();
	  }
	  return kayttajaID;
	}
}
