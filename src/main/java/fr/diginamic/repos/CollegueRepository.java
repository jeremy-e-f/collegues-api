package fr.diginamic.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.entite.Collegue;

public interface CollegueRepository extends JpaRepository<Collegue, Integer> {
	/** Rechercher un collègue par nom */
	public List<Collegue> findByNom(String nom);

	/** Rechercher un collègue par matricule */
	public List<Collegue> findByMatricule(String matricule);

}
