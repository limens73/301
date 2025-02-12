package entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="recibe")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Recibe {

    @EmbeddedId
    private RecibePk id;

    @ManyToOne
    @MapsId("idPaciente")
    @JoinColumn(name="id_paciente")
    private Paciente paciente;

    @ManyToOne
    @MapsId("idTratamiento")
    @JoinColumn(name ="tratamiento_id" )
    private Tratamiento tratamiento;

    @Column(name="fecha_inicio")
    private LocalDate fechaInicio;
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Override
    public String toString() {
        return "{" + tratamiento +
                ", fechaInicio= " + fechaInicio +
                ", fechaFin= " + fechaFin +
                '}';
    }
}
