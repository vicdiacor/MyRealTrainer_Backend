package com.MyRealTrainer.model;
import java.time.Duration;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @Pattern(regexp = "^0[0-4]:[0-5]\\d:[0-5]\\d$")
    private String tiempo;

    // Relaciones

    @ManyToOne(optional = false)
    @JoinColumn(name="bloque_id")
    @JsonIgnore
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

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }


    public Serie() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((numOrden == null) ? 0 : numOrden.hashCode());
        result = prime * result + ((numRepeticiones == null) ? 0 : numRepeticiones.hashCode());
        result = prime * result + ((peso == null) ? 0 : peso.hashCode());
        result = prime * result + ((tiempo == null) ? 0 : tiempo.hashCode());
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
        if (tiempo == null) {
            if (other.tiempo != null)
                return false;
        } else if (!tiempo.equals(other.tiempo))
            return false;
        if (bloque == null) {
            if (other.bloque != null)
                return false;
        } else if (!bloque.equals(other.bloque))
            return false;
        return true;
    }

   
    
}