package dataAccessObjects;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Kategoria;

/**
 * A class which is responsible for getting category data from database and saving category data into database.
 * @authors hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
public class KategoriaDao {
	
	/**
	 * Adding a category into database.
	 * @param kategoria The category which will be added.
	 * @return Boolean value true if adding is successful and false if not.
	 */
	public boolean lisaaKategoria(Kategoria kategoria) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		em.persist(kategoria);
        em.getTransaction().commit();
        return true;
	}
	
	/**
	 * Getting a category from database.
	 * @param id Id value which identifies the category.
	 * @return The category retrieved.
	 */
	public Kategoria haeKategoriat(int id) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kategoria kategoria = em.find(Kategoria.class, id);
        em.getTransaction().commit();
        return kategoria;
	}
	
	/**
	 * Getting every category from every user from database.
	 * @return List of the categories retrieved.
	 */
	public List<Kategoria> haeKategoriaLista(){
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		TypedQuery<Kategoria> query = em.createQuery("SELECT k FROM Kategoria k ORDER BY k.id ASC", Kategoria.class);
		List<Kategoria> kategoriaLista = query.getResultList();
		em.getTransaction().commit();
		return kategoriaLista;
	}
	
	/**
	 * Changes the name of the category.
	 * @param id The id of the category which is changed.
	 * @param nimi The new name of the category.
	 * @return Boolean value true is everything works, false if not.
	 */
	public boolean muutaKategoria(int id, String nimi) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kategoria kategoria = em.find(Kategoria.class, id);
		kategoria.setNimi(nimi);
		em.getTransaction().commit();
		return true;
	}
	
	/**
	 * Deletes a category from database.
	 * @param id Id value of the category.
	 * @return True if everything goes well, false if not.
	 */
	public boolean poistaKategoria(int id) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kategoria kategoria = em.find(Kategoria.class, id);
		if (kategoria!=null) {
			em.remove(kategoria);
		}
        em.getTransaction().commit();
        return true;
	}
}
