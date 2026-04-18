package tn.esprit.tpfoyer.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
//@Table(name = "c")
public class Bloc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBloc;

    @NonNull
    private String nomBloc;

    private long capaciteBloc;


    @ManyToOne
    private Foyer f; // le nom de l'attribut fils dans la classe parent case sensitive

    @JsonIgnore
    @OneToMany(mappedBy = "b")
    private List<Chambre> lc;

    public long getIdBloc() {
        return idBloc;
    }

    public @NonNull String getNomBloc() {
        return nomBloc;
    }

    public long getCapaciteBloc() {
        return capaciteBloc;
    }

    public Foyer getF() {
        return f;
    }

    public List<Chambre> getLc() {
        return lc;
    }
}
