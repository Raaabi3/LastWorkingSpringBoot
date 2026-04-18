package tn.esprit.tpfoyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entite.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
}
