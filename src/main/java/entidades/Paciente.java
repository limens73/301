package entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name ="paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    private String nombre;
    @Column(name="fecha_nacimiento")
    private LocalDate fechaNacimiento;
    private String direccion;

    @OneToMany(mappedBy = "paciente")
    private Set<Cita> citas;

    public void addCitasBidireccional(Cita cita){

        this.citas.add(cita);
        cita.setPaciente(this);

    }

    @OneToMany(mappedBy = "paciente")
    private Set<Recibe> listaRecibe;

}
