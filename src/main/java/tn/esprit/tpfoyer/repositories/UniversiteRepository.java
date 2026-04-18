package tn.esprit.tpfoyer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entite.Universite;

@Repository
public interface UniversiteRepository extends JpaRepository<Universite, Long>{

@Query("SELECT u FROM Universite u JOIN u.f f JOIN f.lb b WHERE b.capaciteBloc < :capacite")
Universite getUniversite (@Param("capacite") long capacite);
}
