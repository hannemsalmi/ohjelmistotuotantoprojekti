package dataAccessObjects;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Kategoria;
import model.Kulu;
import model.Kulut;

public class KuluDao {
	public boolean lisaaKulu(Kulu kulu) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		em.persist(kulu);
        em.getTransaction().commit();
        return true;
	}
	
	public List<Kulu> haeKulut(int kayttajaId) {
	    EntityManager em = datasource.MariaDbJpaConn.getInstance();
	    em.getTransaction().begin();
	    TypedQuery<Kulu> query = em.createQuery("SELECT k FROM Kulu k WHERE k.kayttaja.id = :kayttajaId", Kulu.class);
	    query.setParameter("kayttajaId", kayttajaId);
	    List<Kulu> kulut = query.getResultList();
	    em.getTransaction().commit();
	    return kulut;
	}
	
	public Kulu haeKulu(int kuluId) { 
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kulu kulu = em.find(Kulu.class, kuluId);
        em.getTransaction().commit();
        return kulu;
	}
	
	public boolean muutaKulu(int id, Double summa, String nimi, String kuvaus) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kulu kulu = em.find(Kulu.class, id);
		kulu.setNimi(nimi);
		kulu.setSumma(summa);
		kulu.setKuvaus(kuvaus);
        em.getTransaction().commit();
        return true;
	}
	
	public boolean muutaKulunKategoria(int kuluId, Kategoria uusiKategoria) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kulu kulu = em.find(Kulu.class, kuluId);
		kulu.setKategoria(uusiKategoria);
		em.getTransaction().commit();
		return true;
	}
	
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
