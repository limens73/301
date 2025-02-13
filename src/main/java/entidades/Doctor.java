package entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


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



    @OneToOne(mappedBy ="doctor",cascade = CascadeType.ALL)
    private Cita cita;

    @Override
    public String toString() {
        return "Doctor{id=" + id + ", nombre='" + nombre + "', especialidad='" + especialidad + "', telefono='" + telefono + "'}";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return id == doctor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
