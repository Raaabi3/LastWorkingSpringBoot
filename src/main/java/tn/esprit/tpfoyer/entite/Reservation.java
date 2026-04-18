package tn.esprit.tpfoyer.entite;

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
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idReservation;

    @NonNull
    @Column(unique=true, nullable = false)
    private Date anneeUniversitaire;

    private boolean estValide;

    @ManyToMany
    private List<Etudiant> le;
}
