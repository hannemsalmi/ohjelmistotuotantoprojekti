package dataAccessObjects;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Kayttaja;
import model.Kulu;

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
	
	public List<Kayttaja> haeKayttajaLista() {
	    EntityManager em = datasource.MariaDbJpaConn.getInstance();
	    em.getTransaction().begin();
	    TypedQuery<Kayttaja> query = em.createQuery("SELECT k FROM Kayttaja k ORDER BY k.id ASC", Kayttaja.class);
	    List<Kayttaja> kayttajat = query.getResultList();
	    em.getTransaction().commit();
	    return kayttajat;
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
