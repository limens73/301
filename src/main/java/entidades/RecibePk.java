package entidades;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable


public class RecibePk implements Serializable {

    private int idPaciente;
    private int idTratamiento;
}
