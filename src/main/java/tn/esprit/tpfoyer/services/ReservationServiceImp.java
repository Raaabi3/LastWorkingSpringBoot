package tn.esprit.tpfoyer.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entite.Reservation;
import tn.esprit.tpfoyer.repositories.ReservationRepository;

import java.util.List;

@Service
@AllArgsConstructor
@SuppressWarnings("null")
public class ReservationServiceImp implements IService<Reservation> {
    ReservationRepository reservationRepo;
    @Override
    public Reservation add(Reservation reservation) {
        return reservationRepo.save(reservation);
    }

    @Override
    public Reservation update(Reservation reservation) {
        return reservationRepo.save(reservation);
    }

    @Override
    public void delete(long idReservation) {
        reservationRepo.deleteById(idReservation);
    }

    @Override
    public Reservation getById(long idReservation) {
        return reservationRepo.findById(idReservation).get();
    }

    @Override
    public List<Reservation> getAll() {
        return reservationRepo.findAll();
    }

    @Override
    public List<Reservation> addAll(List<Reservation> reservations) {
        return reservationRepo.saveAll(reservations);
    }
}
