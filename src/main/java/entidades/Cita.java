package entidades;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="cita")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @NonNull
    private LocalDate fecha;
    @NonNull
    private String estado;

    @OneToOne
    @JoinColumn(name = "id_doctor",referencedColumnName = "id")
    @NonNull
    private Doctor doctor;

    public void setDoctorBidireccional(Doctor doctor){

        this.doctor = doctor;
        doctor.setCita(this);

    }

    @ManyToOne
    @JoinColumn(name ="id_paciente", referencedColumnName = "id")
    @NonNull
    private Paciente paciente;

    public void setPacienteBidireccional(Paciente paciente){

        this.paciente = paciente;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cita cita = (Cita) o;
        return id == cita.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
