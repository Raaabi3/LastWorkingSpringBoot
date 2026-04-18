package tn.esprit.tpfoyer.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tn.esprit.tpfoyer.entite.Bloc;
import tn.esprit.tpfoyer.entite.Chambre;
import tn.esprit.tpfoyer.entite.Etudiant;
import tn.esprit.tpfoyer.entite.Foyer;
import tn.esprit.tpfoyer.entite.Reservation;
import tn.esprit.tpfoyer.entite.TypeChambre;
import tn.esprit.tpfoyer.entite.Universite;
import tn.esprit.tpfoyer.repositories.BlocRepository;
import tn.esprit.tpfoyer.repositories.ChambreRepository;
import tn.esprit.tpfoyer.repositories.EtudiantRepository;
import tn.esprit.tpfoyer.repositories.FoyerRepository;
import tn.esprit.tpfoyer.repositories.ReservationRepository;
import tn.esprit.tpfoyer.repositories.UniversiteRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Configuration
public class DataSeeder {

    private final ChambreRepository chambreRepository;

    private static final List<String> FIRST_NAMES = Arrays.asList(
            "Ahmed", "Mohamed", "Sami", "Youssef", "Walid", "Amine", "Omar", "Nour", "Rim", "Ala",
            "Khalil", "Marwa", "Sarra", "Hiba", "Salma", "Mouna", "Yasmine", "Chaima", "Mariem", "Ines"
    );

    private static final List<String> LAST_NAMES = Arrays.asList(
            "Ben Ali", "Trabelsi", "Jaziri", "Mansouri", "Gharbi", "Hamdi", "Chebbi", "Sghaier", "Boukhris", "Haddad",
            "Aouadi", "Abid", "Miled", "Karray", "Brahmi", "Ferjani", "Chaari", "Riahi", "Dridi", "Mejri"
    );

    private static final List<String> UNIVERSITIES = Arrays.asList(
            "Universite de Tunis", "Universite de Sfax", "Universite de Monastir", "Universite de Sousse"
    );

    private static final List<String> CITY_ADDRESSES = Arrays.asList(
            "Avenue Habib Bourguiba, Tunis",
            "Route de l'Aeroport, Sfax",
            "Avenue de l'Independance, Monastir",
            "Boulevard du 14 Janvier, Sousse"
    );

    DataSeeder(ChambreRepository chambreRepository) {
        this.chambreRepository = chambreRepository;
    }

    @Bean
    CommandLineRunner seedDatabase(
            UniversiteRepository universiteRepository,
            FoyerRepository foyerRepository,
            BlocRepository blocRepository,
            ChambreRepository chambreRepository,
            EtudiantRepository etudiantRepository,
            ReservationRepository reservationRepository
    ) {
        return args -> {
            List<Foyer> foyers = seedFoyers(foyerRepository);
            seedUniversites(universiteRepository, foyers);
            List<Bloc> blocs = seedBlocs(blocRepository, foyers);
            List<Chambre> chambres = seedChambres(chambreRepository, blocs);
            List<Etudiant> etudiants = seedEtudiants(etudiantRepository);
            seedReservations(reservationRepository, chambres, etudiants);
        };
    }

    private List<Foyer> seedFoyers(FoyerRepository foyerRepository) {
        List<Foyer> foyers = new ArrayList<>();
        foyerRepository.findAll().forEach(foyers::add);

        while (foyers.size() < UNIVERSITIES.size()) {
            int index = foyers.size();
            Foyer foyer = new Foyer();
            foyer.setNomFoyer("Foyer " + cityNameFromUniversity(UNIVERSITIES.get(index)));
            foyer.setCapaciteFoyer(360);
            foyers.add(foyerRepository.save(foyer));
        }

        return foyers;
    }

    private List<Universite> seedUniversites(UniversiteRepository universiteRepository, List<Foyer> foyers) {
        List<Universite> universites = new ArrayList<>();
        universiteRepository.findAll().forEach(universites::add);

        int missingCount = UNIVERSITIES.size() - universites.size();
        for (int i = 0; i < missingCount; i++) {
            int index = universites.size();
            Universite universite = new Universite();
            universite.setNomUniversite(UNIVERSITIES.get(index));
            universite.setAdresse(CITY_ADDRESSES.get(index));
            universite.setF(foyers.get(index));
            universites.add(universiteRepository.save(universite));
        }

        return universites;
    }

    private List<Bloc> seedBlocs(BlocRepository blocRepository, List<Foyer> foyers) {
        List<Bloc> blocs = new ArrayList<>();
        blocRepository.findAll().forEach(blocs::add);

        List<Bloc> blocsToRepair = new ArrayList<>();
        for (int i = 0; i < blocs.size(); i++) {
            Bloc bloc = blocs.get(i);
            boolean updated = false;

            if ((bloc.getNomBloc() == null || bloc.getNomBloc().isBlank())) {
                bloc.setNomBloc("Bloc " + (char) ('A' + (i % 3)) + " - " + ((i / 3) + 1));
                updated = true;
            }

            if (bloc.getCapaciteBloc() == 0) {
                bloc.setCapaciteBloc(120);
                updated = true;
            }

            if (bloc.getF() == null && !foyers.isEmpty()) {
                bloc.setF(foyers.get(i % foyers.size()));
                updated = true;
            }

            if (updated) {
                blocsToRepair.add(bloc);
            }
        }

        if (!blocsToRepair.isEmpty()) {
            blocRepository.saveAll(blocsToRepair);
        }

        int expectedBlocs = foyers.size() * 3;
        while (blocs.size() < expectedBlocs) {
            int blocIndex = blocs.size();
            int foyerIndex = blocIndex / 3;
            int blocLetter = blocIndex % 3;

            Bloc bloc = new Bloc();
            bloc.setNomBloc("Bloc " + (char) ('A' + blocLetter) + " - " + (foyerIndex + 1));
            bloc.setCapaciteBloc(120);
            bloc.setF(foyers.get(foyerIndex));
            blocs.add(blocRepository.save(bloc));
        }

        return blocs;
    }

    private List<Chambre> seedChambres(ChambreRepository chambreRepository, List<Bloc> blocs) {
        List<Chambre> chambres = new ArrayList<>();
        chambreRepository.findAll().forEach(chambres::add);

        long nextRoomNumber = chambres.stream()
                .mapToLong(Chambre::getNumeroChambre)
                .max()
                .orElse(99L) + 1;

        List<Chambre> chambresToRepair = new ArrayList<>();
        for (int i = 0; i < chambres.size(); i++) {
            Chambre chambre = chambres.get(i);
            boolean updated = false;

            if (chambre.getNumeroChambre() == 0) {
                chambre.setNumeroChambre(nextRoomNumber++);
                updated = true;
            }

            if (chambre.getTypeC() == null) {
                chambre.setTypeC(typeByIndex(i % 20));
                updated = true;
            }

            if (chambre.getB() == null && !blocs.isEmpty()) {
                chambre.setB(blocs.get(i % blocs.size()));
                updated = true;
            }

            if (updated) {
                chambresToRepair.add(chambre);
            }
        }

        if (!chambresToRepair.isEmpty()) {
            chambreRepository.saveAll(chambresToRepair);
        }

        int expectedChambres = blocs.size() * 20;
        while (chambres.size() < expectedChambres) {
            int chambreIndex = chambres.size();
            Bloc bloc = blocs.get(chambreIndex / 20);

            Chambre chambre = new Chambre();
            chambre.setNumeroChambre(nextRoomNumber++);
            chambre.setTypeC(typeByIndex(chambreIndex % 20));
            chambre.setB(bloc);
            chambres.add(chambreRepository.save(chambre));
        }

        return chambres;
    }

    private List<Etudiant> seedEtudiants(EtudiantRepository etudiantRepository) {
        List<Etudiant> etudiants = new ArrayList<>();
        etudiantRepository.findAll().forEach(etudiants::add);

        long nextCin = etudiants.stream()
                .mapToLong(Etudiant::getCin)
                .max()
                .orElse(10999999L) + 1;

        while (etudiants.size() < 220) {
            int index = etudiants.size();
            Etudiant etudiant = new Etudiant();
            etudiant.setNom(LAST_NAMES.get(index % LAST_NAMES.size()));
            etudiant.setPrenom(FIRST_NAMES.get(index % FIRST_NAMES.size()));
            etudiant.setCin(nextCin++);
            etudiant.setEcole(index % 2 == 0 ? "ESPRIT" : "ISSAT");
            etudiant.setDateNaissance(toDate(LocalDate.of(1999 + (index % 6), 1 + (index % 12), 1 + (index % 27))));
            etudiants.add(etudiantRepository.save(etudiant));
        }

        return etudiants;
    }

    private void seedReservations(ReservationRepository reservationRepository, List<Chambre> chambres, List<Etudiant> etudiants) {
        List<Reservation> reservations = new ArrayList<>();
        reservationRepository.findAll().forEach(reservations::add);

        LocalDate startAcademicDate = LocalDate.of(2025, 9, 1);
        boolean createdReservations = false;
        while (reservations.size() < chambres.size()) {
            int index = reservations.size();
            Reservation reservation = new Reservation();
            reservation.setAnneeUniversitaire(toDate(startAcademicDate.plusDays(index)));
            reservation.setEstValide(index % 6 != 0);

            int firstStudent = index % etudiants.size();
            int secondStudent = (index + 71) % etudiants.size();
            reservation.setLe(Arrays.asList(etudiants.get(firstStudent), etudiants.get(secondStudent)));
            reservations.add(reservationRepository.save(reservation));
            createdReservations = true;
        }

        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            int firstStudent = i % etudiants.size();
            int secondStudent = (i + 71) % etudiants.size();
            reservation.setLe(Arrays.asList(etudiants.get(firstStudent), etudiants.get(secondStudent)));
        }
        reservationRepository.saveAll(reservations);

        if (createdReservations || !chambres.isEmpty()) {
            int linkCount = Math.min(chambres.size(), reservations.size());
            List<Chambre> chambresToLink = new ArrayList<>();
            for (int i = 0; i < linkCount; i++) {
                chambres.get(i).setLr(List.of(reservations.get(i)));
                chambresToLink.add(chambres.get(i));
            }
            if (!chambresToLink.isEmpty()) {
                chambreRepository.saveAll(chambresToLink);
            }
        }
    }

    private static TypeChambre typeByIndex(int index) {
        if (index % 3 == 0) {
            return TypeChambre.SIMPLE;
        }
        if (index % 3 == 1) {
            return TypeChambre.DOUBLE;
        }
        return TypeChambre.TRIPLE;
    }

    private static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static String cityNameFromUniversity(String universityName) {
        String[] parts = universityName.split(" ");
        return parts[parts.length - 1];
    }
}