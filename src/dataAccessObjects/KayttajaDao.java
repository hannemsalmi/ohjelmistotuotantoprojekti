package dataAccessObjects;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import model.Kategoria;
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
	
	public void paivitaBudjetti(int id, double budjetti) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kayttaja kayttaja = em.find(Kayttaja.class, id);
		if (kayttaja != null) {
			kayttaja.setMaksimibudjetti(budjetti);
			em.merge(kayttaja);
		}
		em.getTransaction().commit();
	}
	
	public List<Kayttaja> haeKayttajaLista() {
	    EntityManager em = datasource.MariaDbJpaConn.getInstance();
	    em.getTransaction().begin();
	    TypedQuery<Kayttaja> query = em.createQuery("SELECT k FROM Kayttaja k ORDER BY k.id ASC", Kayttaja.class);
	    List<Kayttaja> kayttajat = query.getResultList();
	    em.getTransaction().commit();
	    return kayttajat;
	}
	
	
	public void poistaKayttajanTiedot(int id) {
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
	}
}
