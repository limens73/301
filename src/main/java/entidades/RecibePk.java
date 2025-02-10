package entidades;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor

public class RecibePk implements Serializable {

    private int idPaciente;
    private int idTratamiento;
}
