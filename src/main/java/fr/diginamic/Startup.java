package fr.diginamic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import fr.diginamic.entite.Collegue;
import fr.diginamic.repos.CollegueRepository;

@Component
public class Startup {

	private CollegueRepository collRepo;

	public Startup(CollegueRepository collRepo) {
		super();
		this.collRepo = collRepo;
	}

	/**
	 * Remplissage de la base de données
	 */
	@EventListener(ContextRefreshedEvent.class)
	public void init() {
		if (this.collRepo.count() == 0) {

			List<Collegue> listeCollegues = new ArrayList<Collegue>();
			listeCollegues.add(new Collegue(null, "XS189657325DF", "MACRON", "Emmanuel", "e.macron@mail.com",
					LocalDate.parse("1977-12-21"),
					"https://www.unamur.be/en/sci/chemistry/rco/membres-images/inconnu/image"));
			listeCollegues.add(new Collegue(null, "XS964752585DF", "ERIME", "Pierre", "p.erime@mail.com",
					LocalDate.parse("1952-11-29"),
					"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ-y5jir5WvnRyY7MBjgfV5KAqoK3gSA8TM4FpCmDYqUB9e6Tdd"));
			listeCollegues.add(new Collegue(null, "XS184984885DF", "MOTIRA", "Chantal", "c.motira@mail.com",
					LocalDate.parse("1961-09-03"),
					"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRa4e4CssqAGU474dxaxypkZowCGK6I573qpkzDmbXksbYCMiZG"));
			listeCollegues.add(new Collegue(null, "XS874896987DF", "WALKER", "Paul", "p.walker@mail.com",
					LocalDate.parse("1914-05-11"),
					"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQjjRKyNVBo0pdtMpSlzX61qcVQ9XYoy7jY-9XCpCZM2pV5nxpN"));
			for (Collegue collegue : listeCollegues) {
				this.collRepo.save(collegue);
			}
		}

	}

}
