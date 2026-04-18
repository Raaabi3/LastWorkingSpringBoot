package tn.esprit.tpfoyer.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entite.Etudiant;
import tn.esprit.tpfoyer.repositories.EtudiantRepository;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@SuppressWarnings("null")
public class EtudiantServiceImp implements IService<Etudiant> {
    EtudiantRepository etdudiantRepo;
    @Override
    public Etudiant add(Etudiant etdudiant) {
        return etdudiantRepo.save(etdudiant);
    }

    @Override
    public Etudiant update(Etudiant etdudiant) {
        return etdudiantRepo.save(etdudiant);
    }

    @Override
    public void delete(long idEtudiant) {
        etdudiantRepo.deleteById(idEtudiant);
    }

    @Override
    public Etudiant getById(long idEtudiant) {
        return etdudiantRepo.findById(idEtudiant).get();
    }

    @Override
    public List<Etudiant> getAll() {
        return etdudiantRepo.findAll();
    }

    @Override
    public List<Etudiant> addAll(List<Etudiant> etudiants) {
        return etdudiantRepo.saveAll(etudiants);
    }

    public List<Etudiant> getEtudiants(Date start, Date end) {
        return etdudiantRepo.findByDateNaissanceBetween(start, end);
    }

    public List<Etudiant> getEtudiants2(Date start, Date end) {
        return etdudiantRepo.getEtudiants(start, end);
    }
}
