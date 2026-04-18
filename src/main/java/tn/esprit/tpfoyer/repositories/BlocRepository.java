package tn.esprit.tpfoyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entite.Bloc;

@Repository
public interface BlocRepository extends JpaRepository<Bloc, Long>{
}
