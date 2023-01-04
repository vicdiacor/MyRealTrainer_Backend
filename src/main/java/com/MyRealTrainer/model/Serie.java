package com.MyRealTrainer.model;
import java.time.Duration;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "series")
public class Serie {

    // Atributos 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(0)
    private Integer numOrden;

    
    @Min(1)
    @Max(999)
    private Integer numRepeticiones;

    @Min(0)
    @Max(999)
    @Digits(fraction=2,integer=5)
    private Double peso;

   
    // (Max 04:59:59) 
    @Pattern(regexp = "^0[0-4]:\\d{2}:\\d{2}$")
    private String tiempoEntreSeries;

    // Relaciones

    @ManyToOne(optional = false)
    @JoinColumn(name="bloque_id")
    private Bloque bloque;


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

    public Integer getNumRepeticiones() {
        return numRepeticiones;
    }

    public void setNumRepeticiones(Integer numRepeticiones) {
        this.numRepeticiones = numRepeticiones;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    

    public Bloque getBloque() {
        return bloque;
    }

    public void setBloque(Bloque bloque) {
        this.bloque = bloque;
    }


    public Serie() {
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
        result = prime * result + ((numOrden == null) ? 0 : numOrden.hashCode());
        result = prime * result + ((numRepeticiones == null) ? 0 : numRepeticiones.hashCode());
        result = prime * result + ((peso == null) ? 0 : peso.hashCode());
        result = prime * result + ((tiempoEntreSeries == null) ? 0 : tiempoEntreSeries.hashCode());
        result = prime * result + ((bloque == null) ? 0 : bloque.hashCode());
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
        Serie other = (Serie) obj;
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
        if (numRepeticiones == null) {
            if (other.numRepeticiones != null)
                return false;
        } else if (!numRepeticiones.equals(other.numRepeticiones))
            return false;
        if (peso == null) {
            if (other.peso != null)
                return false;
        } else if (!peso.equals(other.peso))
            return false;
        if (tiempoEntreSeries == null) {
            if (other.tiempoEntreSeries != null)
                return false;
        } else if (!tiempoEntreSeries.equals(other.tiempoEntreSeries))
            return false;
        if (bloque == null) {
            if (other.bloque != null)
                return false;
        } else if (!bloque.equals(other.bloque))
            return false;
        return true;
    }
    
}