package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import model.*;

public class Main {

	private static final Logger log = Logger.getLogger(Main.class);
	@PersistenceUnit(unitName = "westbahn")
	private static EntityManagerFactory sessionFactory;
	@PersistenceContext
	private static EntityManager entitymanager;
	
	static SimpleDateFormat dateForm = new SimpleDateFormat("dd.MM.yyyy");
	static SimpleDateFormat timeForm = new SimpleDateFormat("dd.MM.yyyy mm:hh");

	private Main() {
		super();
	}

	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		
		try {
			Logger.getLogger("org.hibernate").setLevel(Level.OFF);
			log.info("Starting \"Mapping Perstistent Classes and Associations\" (task1)");
			sessionFactory = Persistence.createEntityManagerFactory("westbahn");
			entitymanager = sessionFactory.createEntityManager();
			fillDB(entitymanager);
			task01();
			log.info("Starting \"Working with JPA-QL and the Hibernate Criteria API\" (task2)");
			log.setLevel(Level.OFF);
			task02();
			task02a();
			task02b();
			task02c();
			log.setLevel(Level.ALL);
			task03(entitymanager);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (entitymanager.getTransaction().isActive())
				entitymanager.getTransaction().rollback();
			entitymanager.close();
			sessionFactory.close();
		}
	}

	public static void fillDB(EntityManager em) throws ParseException {
		em.getTransaction().begin();
		List<Bahnhof> list = new ArrayList<Bahnhof>();
		list.add(new Bahnhof("WienHbf", 0, 0, 0, true));
		list.add(new Bahnhof("SalzburgHbf", 20, 60, 120, true));
		list.add(new Bahnhof("Amstetten", 40, 124, 169, false));
		list.add(new Bahnhof("Linz-Ost", 140, 320, 250, false));
		list.add(new Bahnhof("Huetteldorf", 3, 5, 19, false));
		list.add(new Bahnhof("Wels-Zentrum", 102, 400, 250, true));
		for (Bahnhof b : list)
			em.persist(b);

		em.flush();
		em.getTransaction().commit();
		em.getTransaction().begin();

		Strecke strecke = new Strecke();
		strecke.setStart(list.get(0));
		strecke.setEnde(list.get(1));

		em.persist(strecke);
		em.flush();
		em.getTransaction().commit();
		em.getTransaction().begin();

		Zeitkarte monatsKarte = new Zeitkarte();
		monatsKarte.setTyp(ZeitkartenTyp.MONATSKARTE);
		em.persist(monatsKarte);

		Collection<Ticket> tickets = new ArrayList<>();
		tickets.add(monatsKarte);

		Benutzer benutzer = new Benutzer();
		benutzer.setVorName("David");
		benutzer.setNachName("Langheiter");
		benutzer.seteMail("david@langheiter.com");
		benutzer.setPasswort("thisisapassword");
		benutzer.setVerbuchtePraemienMeilen(40L);
		benutzer.setTickets(tickets);

		em.persist(benutzer);

		Benutzer benutzer2 = new Benutzer();
		benutzer2.setVorName("Benjamin");
		benutzer2.setNachName("Bulis");
		benutzer2.seteMail("bbulis@student.tgm.ac.at");

		em.flush();
		em.getTransaction().commit();
		em.getTransaction().begin();

		Reservierung reservierung = new Reservierung();
		reservierung.setBenutzer(benutzer);

		em.persist(reservierung);

		Reservierung r2 = new Reservierung();
		em.persist(r2);

		Reservierung r3 = new Reservierung();
		r3.setBenutzer(benutzer);
		em.persist(r3);

		em.flush();
		em.getTransaction().commit();
	}

	public static void task01() throws ParseException, InterruptedException {
	}

	public static <T> void task02() throws ParseException {
		Query q = entitymanager.createNamedQuery("Bahnhof.getAll");

		List<?> l = q.getResultList();

		System.out.println("\nBAHNHOEFE");
		System.out.println("-------------------------------");
		for (Object b : l) {
			Bahnhof bhf = null;
			if (b instanceof Bahnhof) {
				bhf = (Bahnhof) b;
				System.out.println(bhf.getName());
			}
		}
		System.out.println("-------------------------------");
	}

	public static void task02a() throws ParseException {
		List<Reservierung> res = entitymanager.createNamedQuery("Benutzer.getReservierungenByEmail")
				.setParameter("email", "david@langheiter.com")
				.getResultList();

		System.out.println("\nRESERVIERUNGEN");
		System.out.println("-------------------------------");
		for (Reservierung r : res) {
			System.out.println(r);
		}
		System.out.println("-------------------------------");
	}

	public static void task02b() throws ParseException {
		List<Benutzer> benutzer = entitymanager.createNamedQuery("Benutzer.getAllWithMonatskarte").getResultList();

		System.out.println("\nUSERS with Monatskarte");
		System.out.println("-------------------------------");
		for (Benutzer b : benutzer) {
			System.out.println(b);
		}
		System.out.println("-------------------------------");
	}

	public static void task02c() throws ParseException {
	}

	public static void task03(EntityManager em) {
	}

	public static void validate(Object obj) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> violations = validator.validate(obj);
		for (ConstraintViolation<Object> violation : violations) {
			log.error(violation.getMessage());
			System.out.println(violation.getMessage());
		}
	}
}
