package tn.esprit.tpfoyer.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entite.Bloc;
import tn.esprit.tpfoyer.repositories.BlocRepository;


import java.util.List;

@Service
@AllArgsConstructor
@SuppressWarnings("null")
public class BlocServiceImp implements IService<Bloc> {
    BlocRepository blocRepo;
    @Override
    public Bloc add(Bloc bloc) {
        return blocRepo.save(bloc);
    }

    @Override
    public Bloc update(Bloc bloc) {
        return blocRepo.save(bloc);
    }

    @Override
    public void delete(long idBloc) {
        blocRepo.deleteById(idBloc);
    }

    @Override
    public Bloc getById(long idBloc) {
        return blocRepo.findById(idBloc).get();
    }

    @Override
    public List<Bloc> getAll() {
        return blocRepo.findAll();
    }

    @Override
    public List<Bloc> addAll(List<Bloc> blocs) {
        return blocRepo.saveAll(blocs);
    }
}
