package entidades;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="doctor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Doctor {



    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    @NonNull
    private int id;
    @NonNull
    private String nombre;
    @NonNull
    private String especialidad;
    @NonNull
    private String telefono;



    @OneToOne(mappedBy ="doctor",cascade = CascadeType.ALL) // Cascade debido a que El objeto hijo (Cita) debe eliminarse si se borra el padre (Doctor)
    private Cita cita;


}
