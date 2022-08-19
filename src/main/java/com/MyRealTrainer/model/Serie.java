package com.MyRealTrainer.model;
import java.time.Duration;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;


import org.springframework.lang.NonNull;

@Entity
@Table(name = "series")
public class Serie {

    // Atributos 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Min(0)
    private Integer numOrden;

    @NonNull
    @Min(0)
    private Integer numRepeticiones;

    @NonNull
    @Min(0)
    @Digits(fraction=2,integer=5)
    private Double peso;


    private Duration tiempo;

    // Relaciones

    @ManyToOne(optional = false)
    @JoinColumn(name="bloque_id")
    private Bloque bloque;

    @ManyToOne(optional = false)
    @JoinColumn(name="ejercicio_id")
    private Ejercicio ejercicio;

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

    public Duration getTiempo() {
        return tiempo;
    }

    public void setTiempo(Duration tiempo) {
        this.tiempo = tiempo;
    }

    public Bloque getBloque() {
        return bloque;
    }

    public void setBloque(Bloque bloque) {
        this.bloque = bloque;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bloque == null) ? 0 : bloque.hashCode());
        result = prime * result + ((ejercicio == null) ? 0 : ejercicio.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((numOrden == null) ? 0 : numOrden.hashCode());
        result = prime * result + ((numRepeticiones == null) ? 0 : numRepeticiones.hashCode());
        result = prime * result + ((peso == null) ? 0 : peso.hashCode());
        result = prime * result + ((tiempo == null) ? 0 : tiempo.hashCode());
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
        if (bloque == null) {
            if (other.bloque != null)
                return false;
        } else if (!bloque.equals(other.bloque))
            return false;
        if (ejercicio == null) {
            if (other.ejercicio != null)
                return false;
        } else if (!ejercicio.equals(other.ejercicio))
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
        return true;
    }

    public Serie() {
    }
    
}