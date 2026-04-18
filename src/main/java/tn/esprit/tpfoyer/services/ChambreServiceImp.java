package tn.esprit.tpfoyer.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entite.Chambre;
import tn.esprit.tpfoyer.repositories.ChambreRepository;

import java.util.List;

@Service
@AllArgsConstructor
@SuppressWarnings("null")
public class ChambreServiceImp implements IService<Chambre> {


    ChambreRepository chambreRep;
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

    public Chambre getChambreParNum(long numChambre){
        return chambreRep.findByNumeroChambre(numChambre);
    }
    public Chambre getChambreParNum2(long numChambre){
        return chambreRep.findChambreByNumeroChambre(numChambre);
    }
}
