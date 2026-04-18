package tn.esprit.tpfoyer.entite;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
//@Table(name = "c")
public class Foyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFoyer;

    @NonNull
    private String nomFoyer;

    private long capaciteFoyer;

    @OneToMany(mappedBy = "f")
    @JsonIgnore
    private List<Bloc> lb;

    @JsonIgnore
    @OneToOne(mappedBy = "f") // le nom de l'attribut fils dans la classe parent case sensitive
    private Universite u;

    public long getIdFoyer() {
        return idFoyer;
    }

    public @NonNull String getNomFoyer() {
        return nomFoyer;
    }

    public long getCapaciteFoyer() {
        return capaciteFoyer;
    }

    public List<Bloc> getLb() {
        return lb;
    }

    public Universite getU() {
        return u;
    }
}
