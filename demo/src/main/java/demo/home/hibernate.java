package demo.home;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;



public class hibernate {
	EntityManagerFactory e=Persistence.createEntityManagerFactory("demo");
	EntityManager m=e.createEntityManager();
	EntityTransaction t=m.getTransaction();
	public void register(register r)
	{
		t.begin();
		m.persist(r);
		t.commit();
	}

}
