package tn.esprit.tpfoyer.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entite.Foyer;
import tn.esprit.tpfoyer.repositories.FoyerRepository;

import java.util.List;

@Service
@AllArgsConstructor
@SuppressWarnings("null")
public class FoyerServiceImp implements IService<Foyer> {
    FoyerRepository foyerRepo;
    @Override
    public Foyer add(Foyer bloc) {
        return foyerRepo.save(bloc);
    }

    @Override
    public Foyer update(Foyer bloc) {
        return foyerRepo.save(bloc);
    }

    @Override
    public void delete(long idFoyer) {
        foyerRepo.deleteById(idFoyer);
    }

    @Override
    public Foyer getById(long idFoyer) {
        return foyerRepo.findById(idFoyer).get();
    }

    @Override
    public List<Foyer> getAll() {
        return foyerRepo.findAll();
    }

    @Override
    public List<Foyer> addAll(List<Foyer> foyers) {
        return foyerRepo.saveAll(foyers);
    }

    public List<Foyer> getFoyers(List<Long> numeros){
        return foyerRepo.findBylb_lc_numeroChambreIn(numeros);
    }

    public List<Foyer> getFoyers2(List<Long> numeros){
        return foyerRepo.getFoyers(numeros);
    }
}
