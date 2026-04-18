package tn.esprit.tpfoyer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entite.Foyer;

import java.util.List;

@Repository
public interface FoyerRepository extends JpaRepository<Foyer, Long>{

    // this is correct
    // so we took the first atttribut to help us get the chanmbre which is in class Foyer lb then in the class bloc
    // lc then in the class chambre the NumeroChambre then In this list
    List<Foyer> findBylb_lc_numeroChambreIn(List<Long> numeros);

    @Query("SELECT f FROM Foyer f JOIN f.lb b JOIN b.lc c " + "WHERE c.numeroChambre IN :numeros")
    List<Foyer> getFoyers (@Param("numeros") List<Long> numeros);
}
