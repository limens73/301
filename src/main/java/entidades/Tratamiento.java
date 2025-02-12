package entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name="tratamiento")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    private String tipo;
    @Column(precision = 10, scale = 2)
    private BigDecimal costo;

    @ManyToOne
    @JoinColumn(name="id_hospital")
    private Hospital hospital;

    @OneToMany(mappedBy = "tratamiento")
    private Set<Recibe> listaRecibe;

    @Override
    public String toString() {
        return "Tratamiento{" +
                "id=" + id +
                ", tipo='" + tipo +
                ", costo=" + costo +
                '}';
    }
}
