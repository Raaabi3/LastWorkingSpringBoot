package tn.esprit.tpfoyer.entite;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
//@Table(name = "c")
public class Universite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUniversite;

    @NonNull
    private String nomUniversite;
    private String adresse;

    @OneToOne
    private Foyer f;
}
