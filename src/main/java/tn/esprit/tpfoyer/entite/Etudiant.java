package tn.esprit.tpfoyer.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
//@Table(name = "c")
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEtudiant;

    @NonNull
    private String nom;
    private String prenom;

    @Column(unique=true, nullable = false)
    private long cin;

    private String ecole;
    private Date dateNaissance;

    @JsonIgnore
    @ManyToMany(mappedBy = "le") // le nom de l'attribut fils dans la classe parent
    private List<Reservation> lr;

}
