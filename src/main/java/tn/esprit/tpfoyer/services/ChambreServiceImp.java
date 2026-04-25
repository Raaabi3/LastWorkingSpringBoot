package tn.esprit.tpfoyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpfoyer.entite.Bloc;
import tn.esprit.tpfoyer.entite.Chambre;
import tn.esprit.tpfoyer.entite.Reservation;
import tn.esprit.tpfoyer.entite.TypeChambre;
import tn.esprit.tpfoyer.repositories.BlocRepository;
import tn.esprit.tpfoyer.repositories.ChambreRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@AllArgsConstructor
@SuppressWarnings("null")
public class ChambreServiceImp implements IService<Chambre> {
    private List<Chambre> chambres ;
    ChambreRepository chambreRep;
    BlocRepository blocRep;

    @Override
    public Chambre add(Chambre chambre) {
        return chambreRep.save(chambre);
    }

    @Override
    public Chambre update(Chambre chambre) {
        return chambreRep.save(chambre);
    }

    @Override
    public void delete(long idChambre) {
        chambreRep.deleteById(idChambre);
    }

    @Override
    public Chambre getById(long idChambre) {
        return chambreRep.findById(idChambre).orElse(null);
    }

    @Override
    public List<Chambre> getAll() {
        return chambreRep.findAll();
    }

    @Override
    public List<Chambre> addAll(List<Chambre> chambres) {
        return chambreRep.saveAll(chambres);
    }


    @Scheduled(fixedDelay = 60000)
    public void listeChambresParBloc() {
        List<Bloc> blocs = blocRep.findAll();
        chambres = chambreRep.findAll();

        for (Bloc bloc : blocs) {
            String nomBloc = bloc.getNomBloc() ;
            log.info("Bloc {} ayant une capacite {}", nomBloc, bloc.getCapaciteBloc());
            boolean chambreTrouvee = false;
            for (Chambre chambre : chambres) {
                if (chambre.getB() != null && chambre.getB().getIdBloc() == bloc.getIdBloc()) {
                    if (!chambreTrouvee) {
                        log.info("La liste des chambres pour ce bloc:");
                        chambreTrouvee = true;
                    }
                    log.info("NumChambre: {} type: {}", chambre.getNumeroChambre(), chambre.getTypeC());
                }
            }
            if (!chambreTrouvee) {
                log.info("Pas de chambre disponible dans ce bloc");
            }
            log.info("********************");
        }
    }

    @Scheduled(fixedDelay = 360000)
    public void pourcentageChambreParTypeChambre() {
        long totalChambres = chambres.size();
        long nbSimple = 0;
        long nbDouble = 0;
        long nbTriple = 0;

        for (Chambre chambre : chambres) {
            TypeChambre type = chambre.getTypeC();
            switch (type) {
                case SIMPLE -> nbSimple++;
                case DOUBLE -> nbDouble++;
                case TRIPLE -> nbTriple++;
            }
        }
        log.info("Nombre total des chambres: {}", totalChambres);
        log.info(
                "le percentage des chambre pour le type SIMPLE est égale à {}",
                (nbSimple * 100.0) / totalChambres);
        log.info(
                "le percentage des chambre pour le type DOUBLE est égale à {}",
                (nbDouble * 100.0) / totalChambres);
        log.info(
                "le percentage des chambre pour le type TRIPLE est égale à {}",
                (nbTriple * 100.0) / totalChambres);
    }

/* 
    @Scheduled(fixedDelay =  180000)
    @Transactional(readOnly = true)
    public void nbPlacesDisponibleParChambreAnneeEnCours() {
        int anneeCourante = LocalDate.now().getYear();

        for (Chambre chambre : chambres) {
            int capacite = capaciteParType(chambre.getTypeC());
            int placesOccupees = 0;

            if (chambre.getLr() != null) {
                for (Reservation reservation : chambre.getLr()) {
                    if (reservation == null || !reservation.isEstValide() || reservation.getAnneeUniversitaire() == null) {
                        continue;
                    }
                    int anneeReservation = reservation.getAnneeUniversitaire()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                            .getYear();

                    if (anneeReservation != anneeCourante) {
                        continue;
                    }

                    if (reservation.getLe() != null) {
                        placesOccupees += reservation.getLe().size();
                    }
                }
            }

            int placesDisponibles = Math.max(capacite - placesOccupees, 0);
            log.info("Chambre {} ({}): {} place(s) disponible(s)", chambre.getNumeroChambre(), chambre.getTypeC(), placesDisponibles);
        }
    }

    private int capaciteParType(TypeChambre typeChambre) {
        if (typeChambre == null) {
            return 0;
        }

        return switch (typeChambre) {
            case SIMPLE -> 1;
            case DOUBLE -> 2;
            case TRIPLE -> 3;
        };
    }
    */

    public Chambre getChambreParNum(long numChambre){
        return chambreRep.findByNumeroChambre(numChambre);
    }
    public Chambre getChambreParNum2(long numChambre){
        return chambreRep.findChambreByNumeroChambre(numChambre);
    }
}
