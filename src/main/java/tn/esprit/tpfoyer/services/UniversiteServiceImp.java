package tn.esprit.tpfoyer.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entite.Universite;
import tn.esprit.tpfoyer.repositories.UniversiteRepository;

import java.util.List;

@Service
@AllArgsConstructor
@SuppressWarnings("null")
public class UniversiteServiceImp implements IService<Universite> {
    UniversiteRepository universiteRepo;
    @Override
    public Universite add(Universite universite) {
        return universiteRepo.save(universite);
    }

    @Override
    public Universite update(Universite universite) {
        return universiteRepo.save(universite);
    }

    @Override
    public void delete(long idUniversite) {
        universiteRepo.deleteById(idUniversite);
    }

    @Override
    public Universite getById(long idUniversite) {
        return universiteRepo.findById(idUniversite).get();
    }

    @Override
    public List<Universite> getAll() {
        return universiteRepo.findAll();
    }

    @Override
    public List<Universite> addAll(List<Universite> universites) {
        return universiteRepo.saveAll(universites);
    }

    public Universite getUniversite(long capacite){
        return universiteRepo.getUniversite(capacite);
    }
}
