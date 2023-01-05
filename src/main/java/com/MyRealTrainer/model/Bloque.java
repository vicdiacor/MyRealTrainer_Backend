package com.MyRealTrainer.model;
import java.time.Duration;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bloques")
public class Bloque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Max 29:59
    @Pattern(regexp = "^[0-2]\\d:[0-5]\\d$")
    private String tiempoEntreSeries;

    @NotNull
    @Min(0)
    private Integer numOrden;
    
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoBloque tipoBloque;

    @OneToMany(mappedBy = "bloque",cascade = CascadeType.REMOVE)
    @Valid
    private List<Serie> series;

    
    @ManyToOne(optional = false)
    @JoinColumn(name="ejercicio_id")
    private Ejercicio ejercicio;

    @ManyToOne(optional = false)
    @JoinColumn(name="entrenamiento_id")
    @JsonIgnore
    private Entrenamiento entrenamiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   
    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

    public Entrenamiento getEntrenamiento() {
        return entrenamiento;
    }

    public void setEntrenamiento(Entrenamiento entrenamiento) {
        this.entrenamiento = entrenamiento;
    }

    public Integer getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(Integer numOrden) {
        this.numOrden = numOrden;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public TipoBloque getTipoBloque() {
        return tipoBloque;
    }

    public void setTipoBloque(TipoBloque tipoBloque) {
        this.tipoBloque = tipoBloque;
    }

    
    public String getTiempoEntreSeries() {
        return tiempoEntreSeries;
    }

    public void setTiempoEntreSeries(String tiempoEntreSeries) {
        this.tiempoEntreSeries = tiempoEntreSeries;
    }
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((tiempoEntreSeries == null) ? 0 : tiempoEntreSeries.hashCode());
        result = prime * result + ((numOrden == null) ? 0 : numOrden.hashCode());
        result = prime * result + ((tipoBloque == null) ? 0 : tipoBloque.hashCode());
        result = prime * result + ((series == null) ? 0 : series.hashCode());
        result = prime * result + ((ejercicio == null) ? 0 : ejercicio.hashCode());
        result = prime * result + ((entrenamiento == null) ? 0 : entrenamiento.hashCode());
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
        Bloque other = (Bloque) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (tiempoEntreSeries == null) {
            if (other.tiempoEntreSeries != null)
                return false;
        } else if (!tiempoEntreSeries.equals(other.tiempoEntreSeries))
            return false;
        if (numOrden == null) {
            if (other.numOrden != null)
                return false;
        } else if (!numOrden.equals(other.numOrden))
            return false;
        if (tipoBloque != other.tipoBloque)
            return false;
        if (series == null) {
            if (other.series != null)
                return false;
        } else if (!series.equals(other.series))
            return false;
        if (ejercicio == null) {
            if (other.ejercicio != null)
                return false;
        } else if (!ejercicio.equals(other.ejercicio))
            return false;
        if (entrenamiento == null) {
            if (other.entrenamiento != null)
                return false;
        } else if (!entrenamiento.equals(other.entrenamiento))
            return false;
        return true;
    }

    public Bloque() {
    }


   
  

    

    
    
}