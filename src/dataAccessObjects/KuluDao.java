package dataAccessObjects;

import jakarta.persistence.EntityManager;
import model.Kulu;
import model.Kulut;

public class KuluDao {
	public void lisaaKulu(Kulu kulu) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		em.persist(kulu);
        em.getTransaction().commit();
	}
	
	public Kulut haeKulut(int kayttajaId) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kulut kulut = em.find(Kulut.class, kayttajaId);
        em.getTransaction().commit();
        return kulut;
	}
	
	public void muutaKulu(int id, Double summa, String nimi, String kuvaus) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kulu kulu = em.find(Kulu.class, id);
		kulu.setNimi(nimi);
		kulu.setSumma(summa);
		kulu.setKuvaus(kuvaus);
        em.getTransaction().commit();
	}
	
	public void poistaKulu(int id) {
		EntityManager em = datasource.MariaDbJpaConn.getInstance();
		em.getTransaction().begin();
		Kulu kulu = em.find(Kulu.class, id);
		if (kulu!=null) {
			em.remove(kulu);
		}
        em.getTransaction().commit();
	}
}
