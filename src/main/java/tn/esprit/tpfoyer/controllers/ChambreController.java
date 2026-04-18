package tn.esprit.tpfoyer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entite.Chambre;
import tn.esprit.tpfoyer.entite.Etudiant;
import tn.esprit.tpfoyer.entite.Foyer;
import tn.esprit.tpfoyer.services.ChambreServiceImp;
import tn.esprit.tpfoyer.services.EtudiantServiceImp;
import tn.esprit.tpfoyer.services.FoyerServiceImp;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@RestController
//@AllArgsConstructor
@RequestMapping("/api/chambre")
public class ChambreController {

    @Autowired
    ChambreServiceImp chambreServiceImp;

    @Autowired
    EtudiantServiceImp etudiantServiceImp;

    @Autowired
    FoyerServiceImp foyerServiceImpS;

    @PostMapping("/addChambre")
    public Chambre addChambre(@RequestBody Chambre chambre){
        return chambreServiceImp.add(chambre);
    }

    @PutMapping("/updateChambre")
    public Chambre updateChambre(@RequestBody Chambre chambre) {return chambreServiceImp.update(chambre);}

    @DeleteMapping("/deleteChambre/{idChambre}")
    public void deleteChambre(@PathVariable long idChambre) { chambreServiceImp.delete(idChambre);}

    @GetMapping("/getById/{idChambre}")
    public Chambre getById(@PathVariable long idChambre){
        return chambreServiceImp.getById(idChambre);
    }

    @GetMapping("/getAll")
    public List<Chambre> getAll(){
        return chambreServiceImp.getAll();
    }

    @PostMapping("/addAll")
    public List<Chambre> addAll(@RequestBody List<Chambre> chambres){
        return chambreServiceImp.addAll(chambres);
    }

    @GetMapping("/getChambreParNum/{num}")
    public Chambre getChambreParNum(@PathVariable long num){
        return chambreServiceImp.getChambreParNum(num);
    }

    @GetMapping("/getChambreParNum2/{num}")
    public ResponseEntity<Chambre> getChambreParNum2(@PathVariable long num){
        Chambre chambre = chambreServiceImp.getChambreParNum2(num);
        if (chambre == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(chambre);
    }

    @GetMapping("/getEtudiants/{start}/{end}")
    public List<Etudiant> getEtudiants(
            @PathVariable String start,
            @PathVariable String end
    )
    {
        Date startDate = parseDate(start);
        Date endDate = parseDate(end);
        List<Etudiant> etudiants = etudiantServiceImp.getEtudiants(startDate, endDate);
        if (etudiants == null || etudiants.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No students found for the given date range");
        }
        return etudiants;
    }

    @GetMapping("/getFoyers")
    public List<Foyer> getFoyers(@RequestParam List<Long> numeros)
    {
        return foyerServiceImpS.getFoyers(numeros);
    }

    @PostMapping("getFoyers/Post")
    public List<Foyer> getFoyersPost(@RequestBody List<Long> numeros)
    {
        return foyerServiceImpS.getFoyers(numeros);
    }

    @GetMapping("/get")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }



    @GetMapping("/getEtudiants2/{start}/{end}")
    public ResponseEntity<List<Etudiant>> getEtudiants2(@PathVariable String start,
                                                        @PathVariable String end)
    {
        Date startDate = parseDate(start);
        Date endDate = parseDate(end);
        List<Etudiant> etudiants = etudiantServiceImp.getEtudiants2(startDate, endDate);
        if (etudiants == null || etudiants.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(etudiants);
    }

    private Date parseDate(String value) {
        List<DateTimeFormatter> formats = List.of(
                DateTimeFormatter.ISO_LOCAL_DATE,
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
        );

        for (DateTimeFormatter formatter : formats) {
            try {
                LocalDate localDate = LocalDate.parse(value, formatter);
                return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            } catch (DateTimeParseException ignored) {
                // try next format
            }
        }

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid date format for '" + value + "'. Use yyyy-MM-dd, dd/MM/yyyy, or dd-MM-yyyy."
        );
    }

    @GetMapping("/getFoyers2")
    public ResponseEntity<List<Foyer>> getFoyers2(@RequestParam List<Long> numeros)
    {
        List<Foyer> foyers = foyerServiceImpS.getFoyers2(numeros);
        if (foyers == null || foyers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(foyers);
    }

    @PostMapping("getFoyers2/Post")
    public ResponseEntity<List<Foyer>> getFoyers2Post(@RequestBody List<Long> numeros)
    {
        List<Foyer> foyers = foyerServiceImpS.getFoyers2(numeros);
        if (foyers == null || foyers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(foyers);
    }


    
    
    
}

