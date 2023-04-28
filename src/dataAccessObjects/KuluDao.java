package dataAccessObjects;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Kategoria;
import model.Kulu;

/**
 * A class which is responsible for getting expense data from database and saving expense data into database.
 * @authors hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
public class KuluDao {
	
	/**
	 * Adding a new expense into the database.
	 * @param kulu The expense being added.
	 * @return True if the method is successful, false if not.
	 */
	public boolean lisaaKulu(Kulu kulu) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		em.persist(kulu);
        em.getTransaction().commit();
        return true;
	}
	
	/**
	 * Retrieves the expenses of a user.
	 * @param kayttajaId Id of the user whose expenses are retrieved.
	 * @return A list of the expenses of the user.
	 */
	public List<Kulu> haeKulut(int kayttajaId) {
	    EntityManager em = datasource.MariaDbJpaConn.getInstance();
	    em.getTransaction().begin();
	    TypedQuery<Kulu> query = em.createQuery("SELECT k FROM Kulu k WHERE k.kayttaja.id = :kayttajaId", Kulu.class);
	    query.setParameter("kayttajaId", kayttajaId);
	    List<Kulu> kulut = query.getResultList();
	    em.getTransaction().commit();
	    return kulut;
	}
	
	/**
	 * Retrieves an expense from the database.
	 * @param kuluId Id of the expense being retrieved.
	 * @return The expense retrieved.
	 */
	public Kulu haeKulu(int kuluId) { 
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kulu kulu = em.find(Kulu.class, kuluId);
        em.getTransaction().commit();
        return kulu;
	}
	
	/**
	 * Modifies the expense which has been saved into database.
	 * @param id ID of the expense modified.
	 * @param summa New amount of money the expense took. 
	 * @param nimi New name of the expense.
	 * @param kuvaus New description o the expense.
	 * @return True if the method is successful, false if not.
	 */
	public boolean muutaKulu(int id, Double summa, String nimi, String kuvaus) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kulu kulu = em.find(Kulu.class, id);
		LocalDate pvm = kulu.getPaivamaara();
		kulu.setNimi(nimi);
		kulu.setSumma(summa);
		kulu.setKuvaus(kuvaus);
		kulu.setPaivamaara(pvm);
        em.getTransaction().commit();
        return true;
	}
	
	/**
	 * A method for changing the category of the expense.
	 * @param kuluId The id of the expense.
	 * @param uusiKategoria New category of the expense.
	 * @return True is the method runs successfully, false if not.
	 */
	public boolean muutaKulunKategoria(int kuluId, Kategoria uusiKategoria) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kulu kulu = em.find(Kulu.class, kuluId);
		kulu.setKategoria(uusiKategoria);
		em.getTransaction().commit();
		return true;
	}
	
	/**
	 * Deleting the expense.
	 * @param id The id of the expense being deleted.
	 * @return True if the expense is deleted, false if not.
	 */
	public boolean poistaKulu(int id) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kulu kulu = em.find(Kulu.class, id);
		if (kulu!=null) {
			em.remove(kulu);
		}
        em.getTransaction().commit();
        return true;
	}
}
