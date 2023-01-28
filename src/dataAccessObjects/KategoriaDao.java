package dataAccessObjects;

import jakarta.persistence.EntityManager;
import model.Kategoria;

public class KategoriaDao {
	public void lisaaKategoria(Kategoria kategoria) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		em.persist(kategoria);
        em.getTransaction().commit();
	}
	
	public Kategoria haeKategoriat(int id) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kategoria kategoria = em.find(Kategoria.class, id);
        em.getTransaction().commit();
        return kategoria;
	}
	
	
	public void poistaKategoria(int id) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kategoria kategoria = em.find(Kategoria.class, id);
		if (kategoria!=null) {
			em.remove(kategoria);
		}
        em.getTransaction().commit();
	}
}
