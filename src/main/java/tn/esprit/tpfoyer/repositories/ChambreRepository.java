package tn.esprit.tpfoyer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entite.Chambre;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long>{
    Chambre findByNumeroChambre(long numeroChambre);

    @Query("SELECT c FROM Chambre c WHERE c.numeroChambre =:numeroChambre")
    Chambre findChambreByNumeroChambre(@Param("numeroChambre") long numeroChambre);


}
