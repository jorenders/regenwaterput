package application;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import application.domain.Configuratie;
import application.domain.Metingen;

public class DataManager {
	Connection connection;
	Statement stmt;
	
	private static final SessionFactory sessionFactory = buildSessionFactory();
	Session session = (Session)sessionFactory.getCurrentSession();
	
	public void openConnectionDB() {
		session = (Session)sessionFactory.getCurrentSession();
		session.beginTransaction();
	}
	
	public void closeConnectionDB() {
		session = (Session)sessionFactory.getCurrentSession();
		session.close();
	}
	
	private static SessionFactory buildSessionFactory() {
		SessionFactory sessionFactory = new Configuration()
				.configure("resources/hibernate.cfg.xml")
				.buildSessionFactory();
		return sessionFactory;
	}

	private void CheckConnectionOpenIfClose() {
		if (!(session.isOpen())) {
			openConnectionDB();
		}
	}
	
	public static void shutdown() {
		sessionFactory.close();
	}
	
	public String haalConfiguratieParameterOp(ConfiguratieType Key) {
		CheckConnectionOpenIfClose();
				
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Configuratie> criteria = builder.createQuery(Configuratie.class);
			
		Root<Configuratie> configuratie = criteria.from(Configuratie.class);
		criteria.where(builder.equal(configuratie.get("key"), Key.getNaam()));
					
		// Execute query
		List<Configuratie> configurations = session.createQuery(criteria).getResultList();
		closeConnectionDB();
		
		if (configurations.size() == 1) {
			String result = configurations.get(0).getValue();
			return result;
		}	
		return null;
	}
	
	public Set<Integer> haalJaartallenOp() {
		CheckConnectionOpenIfClose();
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Metingen> criteria = builder.createQuery(Metingen.class);
			
		@SuppressWarnings("unused")
		Root<Metingen> configuratie = criteria.from(Metingen.class);
		criteria.distinct(true);

		List<Metingen> metingen = session.createQuery(criteria).getResultList();
		closeConnectionDB();
		
		Set<Integer> jaartallen = new HashSet<Integer>();
		for (Metingen meting : metingen) {
			jaartallen.add(meting.getDatum().getYear());
		}
		return jaartallen;
	}

	public Map<Month, Long> haalJaaroverzichtOpVanBepaaldJaar(int jaartal) {
		CheckConnectionOpenIfClose();
		
		LocalDateTime jaar = LocalDateTime.of(jaartal, 1, 1, 1, 1);
        jaar.format(DateTimeFormatter.ofPattern("yyyy"));
		
		Map<Month, Long> maandoverzicht = new HashMap<Month, Long>();
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Metingen> criteria = builder.createQuery(Metingen.class);
			
		Root<Metingen> metingRoot = criteria.from(Metingen.class);
		
		//criteria.where(builder.equal(builder.function("year", Integer.class, meting.get("datum")), jaartal),builder.equal(meting.get("id"), 1));
		criteria.where(builder.equal(builder.function("year", Integer.class, metingRoot.get("datum")), jaartal));
		List<Metingen> metingen = session.createQuery(criteria).getResultList();
		
		for (Metingen meting : metingen) {
//			maandoverzicht.put(meting.getDatum().getMonth(), meting.getWaarde());
			maandoverzicht.put(meting.getDatum().getMonth(), maandoverzicht.getOrDefault(meting.getDatum().getMonth(), 0L) + meting.getWaarde());
		}
		
		closeConnectionDB();
		return maandoverzicht;
	}
}