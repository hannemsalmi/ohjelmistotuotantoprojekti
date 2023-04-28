package dataAccessObjects;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import model.Kayttaja;

/**
 * A class which is responsible for getting user data from database and saving user data into database.
 * @authors hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
public class KayttajaDao {
	
	/**
	 * Adding a new user profile into database.
	 * @param kayttaja User data.
	 */
	public void lisaaKayttaja(Kayttaja kayttaja) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		em.persist(kayttaja);
        em.getTransaction().commit();
	}
	
	/**
	 * Retrieves a profile that matches the id provided.
	 * @param id User id.
	 * @return The user profile.
	 */
	public Kayttaja haeKayttajat(int id) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kayttaja kayttaja = em.find(Kayttaja.class, id);
        em.getTransaction().commit();
        return kayttaja;
	}
	
	/**
	 * Updates the max budget of a user.
	 * @param id Id of the user.
	 * @param budjetti New max budget value.
	 * @return True if the update is successful, false if not.
	 */
	public boolean paivitaBudjetti(int id, double budjetti) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kayttaja kayttaja = em.find(Kayttaja.class, id);
		if (kayttaja != null) {
			kayttaja.setMaksimibudjetti(budjetti);
			em.merge(kayttaja);
		}
		em.getTransaction().commit();
		return true;
	}
	
	/**
	 * Retrieves a list of every user in the database.
	 * @return A list of all of the users.
	 */
	public List<Kayttaja> haeKayttajaLista() {
	    EntityManager em = datasource.MariaDbJpaConn.getInstance();
	    em.getTransaction().begin();
	    TypedQuery<Kayttaja> query = em.createQuery("SELECT k FROM Kayttaja k ORDER BY k.id ASC", Kayttaja.class);
	    List<Kayttaja> kayttajat = query.getResultList();
	    em.getTransaction().commit();
	    return kayttajat;
	}
	
	/**
	 * Removes all the data from selected profile except the name.
	 * @param id Id of the profile which will undergo the data deletion.
	 * @return True if the method is successful, false if it is not.
	 */
	public boolean poistaKayttajanTiedot(int id) {
	    EntityManager em = datasource.MariaDbJpaConn.getInstance();
	    em.getTransaction().begin();
	    Kayttaja kayttaja = em.find(Kayttaja.class, id);
	    String nimimerkki = kayttaja.getNimimerkki();
	    if (kayttaja != null) {
	        Query kuluQuery = em.createQuery("DELETE FROM Kulu k WHERE k.kayttaja = :kayttaja");
	        kuluQuery.setParameter("kayttaja", kayttaja);
	        kuluQuery.executeUpdate();

	        Query kategoriaQuery = em.createQuery("DELETE FROM Kategoria k WHERE k.omistaja = :nimimerkki");
	        kategoriaQuery.setParameter("nimimerkki", nimimerkki);
	        kategoriaQuery.executeUpdate();
	    }
	    em.getTransaction().commit();
	    return true;
	}
	
	/**
	 * Deletes the user. Is not implemented yet anywhere in the program.
	 * @param id The id of the user being deleted.
	 */
	public void poistaKayttaja(int id) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kayttaja kayttaja = em.find(Kayttaja.class, id);
		if (kayttaja!=null) {
			em.remove(kayttaja);
		}
        em.getTransaction().commit();
	}
}
