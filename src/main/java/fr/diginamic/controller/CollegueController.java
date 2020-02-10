package fr.diginamic.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.entite.Collegue;
import fr.diginamic.exception.CollegueNonTrouveException;
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

	@RequestMapping(method = RequestMethod.GET, value = "collegues/{matricule}")
	public ResponseEntity<Collegue> getCollegueByMatricule(@PathVariable String matricule) {
		Optional<Collegue> collOpt = this.collRepo.findByMatricule(matricule).stream().findFirst();
		if (!collOpt.isPresent()) {
			throw new CollegueNonTrouveException("Collegue non trouvé : matricule = " + matricule);
		}
		return ResponseEntity.status(HttpStatus.OK).body(collOpt.get());
	}

	@ExceptionHandler(value = { CollegueNonTrouveException.class })
	public ResponseEntity<String> onCollegueNotFound(CollegueNonTrouveException exception) {
		LOG.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

	@RequestMapping(method = RequestMethod.POST, path = "collegues")
	public ResponseEntity<String> creer(@RequestBody Collegue collegueRecu) {
		collegueRecu.setMatricule(UUID.randomUUID().toString());
		this.collRepo.save(collegueRecu);
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body("Le collegue " + collegueRecu + " a été créé avec succès!");
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "collegues/{matricule}")
	public ResponseEntity<String> patchPhotoUrl(@PathVariable String matricule, @RequestBody PhotoUrlJSON photoUrl) {
		Optional<Collegue> collOpt = this.collRepo.findByMatricule(matricule).stream().findFirst();
		if (!collOpt.isPresent()) {
			throw new CollegueNonTrouveException("Collegue non trouvé : matricule = " + matricule);
		}
		Collegue coll = collOpt.get();
		coll.setPhotoUrl(photoUrl.getPhotoUrl());
		this.collRepo.save(coll);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Le collegue " + coll + " a été modifié avec succès!");
	}

}

/**
 * Utile pour récupérer la valeur de photoUrl
 */
class PhotoUrlJSON {
	private String photoUrl;

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
}
