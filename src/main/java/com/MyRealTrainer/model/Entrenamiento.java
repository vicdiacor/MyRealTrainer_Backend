package com.MyRealTrainer.model;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "entrenamientos")
public class Entrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(0)
    private Integer numOrden;

    @NotBlank
    @Size(max=100)
    private String titulo;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private DiasSemana diaSemana;

    
    @ManyToOne(optional = false)
    @JoinColumn(name="rutina_id")
    @JsonIgnore
    private Rutina rutina;

    @OneToMany(mappedBy = "entrenamiento",cascade = CascadeType.ALL)
    @Valid
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


    public String getTitulo() {
        return titulo;
    }


    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((numOrden == null) ? 0 : numOrden.hashCode());
        result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
        result = prime * result + ((diaSemana == null) ? 0 : diaSemana.hashCode());
        result = prime * result + ((rutina == null) ? 0 : rutina.hashCode());
        result = prime * result + ((bloques == null) ? 0 : bloques.hashCode());
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
        if (titulo == null) {
            if (other.titulo != null)
                return false;
        } else if (!titulo.equals(other.titulo))
            return false;
        if (diaSemana != other.diaSemana)
            return false;
        if (rutina == null) {
            if (other.rutina != null)
                return false;
        } else if (!rutina.equals(other.rutina))
            return false;
        if (bloques == null) {
            if (other.bloques != null)
                return false;
        } else if (!bloques.equals(other.bloques))
            return false;
        return true;
    }



    
    
    
}