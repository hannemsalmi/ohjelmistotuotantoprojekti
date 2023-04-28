package datasource;

import jakarta.persistence.*;

/**
 * A class that creates a JPA connector for database purposes.
 * @authors hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
public class MariaDbJpaConn {

	private static EntityManagerFactory emf = null;
	private static EntityManager em = null;
	
	public static EntityManager getInstance() {
	
		if (em==null) {
			if (emf==null) {
				emf = Persistence.createEntityManagerFactory("DevPU");
			}
			em = emf.createEntityManager();
		}
		return em;
	}
}
