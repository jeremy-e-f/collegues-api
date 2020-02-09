package fr.diginamic.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.entite.Collegue;

public interface CollegueRepository extends JpaRepository<Collegue, Integer> {

	public List<Collegue> findByNom(String nom);

}
