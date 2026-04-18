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
public class Chambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idChambre;

    @Column(name = "numero_chambre", unique = true, nullable = false)
    private long numeroChambre;

    @Column(name = "numero", unique = true, nullable = false)
    private long numeroLegacy;

    @Enumerated(EnumType.STRING) // pour pouvoir vior les valeur dans la base de données sinon
    // avec ORDINAL dans la base de données tu vois des numéros par ordre  pour la 1ere valeur etc
    private TypeChambre typeC;

    @JsonIgnore
    @OneToMany
    private List<Reservation> lr;

    @ManyToOne
    private Bloc b;

    @PrePersist
    @PreUpdate
    private void syncNumeroColumns() {
        if (numeroChambre == 0 && numeroLegacy != 0) {
            numeroChambre = numeroLegacy;
        }
        numeroLegacy = numeroChambre;
    }

    public long getIdChambre() {
        return idChambre;
    }

    public long getNumeroChambre() {
        return numeroChambre;
    }

    public TypeChambre getTypeC() {
        return typeC;
    }

    public List<Reservation> getLr() {
        return lr;
    }

    public Bloc getB() {
        return b;
    }
}
