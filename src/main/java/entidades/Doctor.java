package entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="doctor")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Doctor {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    private String nombre;
    private String especialidad;
    private String telefono;



    @OneToOne(mappedBy ="doctor")
    private Cita cita;


}
