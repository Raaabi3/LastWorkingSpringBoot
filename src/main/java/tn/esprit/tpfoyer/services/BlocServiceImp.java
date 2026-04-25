package tn.esprit.tpfoyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entite.Bloc;
import tn.esprit.tpfoyer.repositories.BlocRepository;


import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@SuppressWarnings("null")
public class BlocServiceImp implements IService<Bloc> {
    BlocRepository blocRepo;

    @Scheduled(fixedDelay = 60000)
    public void fixedDelayMethod() {
        log.info("Method with fixed delay bla bla ");
    }
     
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
