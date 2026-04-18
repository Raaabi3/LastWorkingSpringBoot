package tn.esprit.tpfoyer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.tpfoyer.entite.Etudiant;

import java.util.Date;
import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long>{
    List<Etudiant> findByDateNaissanceBetween (Date start, Date end);

    List<Etudiant> findByLr_anneeUniversitaireBetween(Date start, Date end);

       @Query("SELECT e FROM Etudiant e JOIN e.lr r WHERE r.anneeUniversitaire BETWEEN :start AND :end")
       List<Etudiant> getEtudiants(@Param("start") Date start,@Param("end") Date end);

}
