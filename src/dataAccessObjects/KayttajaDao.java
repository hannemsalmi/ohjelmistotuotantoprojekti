package dataAccessObjects;

import jakarta.persistence.EntityManager;
import model.Kayttaja;

public class KayttajaDao {
	public void lisaaKayttaja(Kayttaja kayttaja) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		em.persist(kayttaja);
        em.getTransaction().commit();
	}
	
	public Kayttaja haeKayttajat(int id) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kayttaja kayttaja = em.find(Kayttaja.class, id);
        em.getTransaction().commit();
        return kayttaja;
	}
	
	
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
