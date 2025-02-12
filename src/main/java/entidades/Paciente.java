package entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name ="paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor


public class Paciente {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @NonNull
    private int id;
    @NonNull
    private String nombre;
    @Column(name="fecha_nacimiento")
    @NonNull
    private LocalDate fechaNacimiento;
    @NonNull
    private String direccion;


    @OneToMany(mappedBy = "paciente")
    private Set<Cita> citas;

    public void addCitasBidireccional(Cita cita){

        this.citas.add(cita);
        cita.setPaciente(this);

    }


    @OneToMany(mappedBy = "paciente")
    private Set<Recibe> listaRecibe;


    @Override
    public String toString() {
        return "\nPaciente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", direccion='" + direccion + '\'' +
                ", \n" + citas +
               //", Tratamientos=" + listaRecibe +
                '}';
    }
}
