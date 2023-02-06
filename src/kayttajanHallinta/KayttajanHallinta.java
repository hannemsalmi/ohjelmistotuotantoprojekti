package kayttajanHallinta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import model.Kayttaja;

public class KayttajanHallinta {
	private static  KayttajanHallinta INSTANCE = null;
	private Kayttaja kirjautunutKayttaja = null;
	
	private KayttajanHallinta() {
	
	}
	
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
	
	public void kirjoitaKayttajaID(int kayttajaID) {
		  try {
		    BufferedWriter writer = new BufferedWriter(new FileWriter("edellisenKayttajanID.txt"));
		    writer.write(Integer.toString(kayttajaID));
		    writer.close();
		  } catch (IOException e) {
		    e.printStackTrace();
		  }
		}
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
