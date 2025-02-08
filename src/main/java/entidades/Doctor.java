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


    // La notación cascade siempre se ubica en la anotación de relación que lleva el mappedBy.
    // Cascade debido a que El objeto hijo (Cita) debe eliminarse si se borra el padre (Doctor)
    @OneToOne(mappedBy ="doctor",cascade = CascadeType.ALL)
    private Cita cita;


}
