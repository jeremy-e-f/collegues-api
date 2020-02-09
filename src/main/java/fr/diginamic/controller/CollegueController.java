package fr.diginamic.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.entite.Collegue;
import fr.diginamic.repos.CollegueRepository;

@RestController
public class CollegueController {

	private static final Logger LOG = LoggerFactory.getLogger(fr.diginamic.controller.CollegueController.class);

	private CollegueRepository collRepo;

	CollegueController(CollegueRepository collRepo) {
		this.collRepo = collRepo;
	}

	@RequestMapping(method = RequestMethod.GET, path = "collegues", params = "nom")
	public List<String> getMatriculeByNom(String nom) {
		List<Collegue> listeCollegues = this.collRepo.findByNom(nom);
		if (listeCollegues.isEmpty()) {
			return null;
		}
		return listeCollegues.stream().map(Collegue::getMatricule).collect(Collectors.toList());
	}

}
