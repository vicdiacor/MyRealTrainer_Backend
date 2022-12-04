package com.MyRealTrainer.model;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Min;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "entrenamientos")
public class Entrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Min(0)
    private Integer numOrden;

    
    @NonNull
    @Enumerated(EnumType.STRING)
    private DiasSemana diaSemana;

    
    @ManyToOne(optional = false)
    @JoinColumn(name="rutina_id")
    private Rutina rutina;

    @OneToMany(mappedBy = "entrenamiento")
    private List<Bloque> bloques;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Integer getNumOrden() {
        return numOrden;
    }


    public void setNumOrden(Integer numOrden) {
        this.numOrden = numOrden;
    }


    public DiasSemana getDiaSemana() {
        return diaSemana;
    }


    public void setDiaSemana(DiasSemana diaSemana) {
        this.diaSemana = diaSemana;
    }


    public Rutina getRutina() {
        return rutina;
    }


    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }


    public List<Bloque> getBloques() {
        return bloques;
    }


    public void setBloques(List<Bloque> bloques) {
        this.bloques = bloques;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bloques == null) ? 0 : bloques.hashCode());
        result = prime * result + ((diaSemana == null) ? 0 : diaSemana.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((numOrden == null) ? 0 : numOrden.hashCode());
        result = prime * result + ((rutina == null) ? 0 : rutina.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Entrenamiento other = (Entrenamiento) obj;
        if (bloques == null) {
            if (other.bloques != null)
                return false;
        } else if (!bloques.equals(other.bloques))
            return false;
        if (diaSemana != other.diaSemana)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (numOrden == null) {
            if (other.numOrden != null)
                return false;
        } else if (!numOrden.equals(other.numOrden))
            return false;
        if (rutina == null) {
            if (other.rutina != null)
                return false;
        } else if (!rutina.equals(other.rutina))
            return false;
        return true;
    }

    
    
    
}