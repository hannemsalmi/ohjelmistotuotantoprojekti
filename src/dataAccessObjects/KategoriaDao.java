package dataAccessObjects;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
	
	public List<Kategoria> haeKategoriaLista(){
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		TypedQuery<Kategoria> query = em.createQuery("SELECT k FROM Kategoria k ORDER BY k.id ASC", Kategoria.class);
		List<Kategoria> kategoriaLista = query.getResultList();
		em.getTransaction().commit();
		return kategoriaLista;
	}
	
	public void poistaKategoria(int id) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kategoria kategoria = em.find(Kategoria.class, id);
		if (kategoria!=null && kategoria.getNimi() != "Yleinen") {
			em.remove(kategoria);
		}
        em.getTransaction().commit();
	}
}
