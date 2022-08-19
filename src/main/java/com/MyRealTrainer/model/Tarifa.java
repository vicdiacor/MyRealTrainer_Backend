package com.MyRealTrainer.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "tarifas")
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max=80)
    private String titulo;

    @NonNull
    @Min(0)
    @Digits(fraction=2,integer=5)
    private Double precio;

    @NonNull
    @Enumerated(EnumType.STRING)
    private TipoDuracion tipoDuracion;

    @NonNull
    @Min(0)
    private Double duracion;

    @Size(max=500)
    private String limitaciones;

    // Relaciones

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "tarifa_lugares", 
               joinColumns = @JoinColumn(name = "tarifa_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "lugares_id", referencedColumnName = "id"))
    private Set<LugarEntrenamiento> lugares = new HashSet<>();

    @ManyToOne(optional = true)
    @JoinColumn(name="servicio_id")
    private Servicio servicio;
    
    // Métodos
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public TipoDuracion getTipoDuracion() {
        return tipoDuracion;
    }

    public void setTipoDuracion(TipoDuracion tipoDuracion) {
        this.tipoDuracion = tipoDuracion;
    }

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public String getLimitaciones() {
        return limitaciones;
    }

    public void setLimitaciones(String limitaciones) {
        this.limitaciones = limitaciones;
    }

    public Set<LugarEntrenamiento> getLugares() {
        return lugares;
    }

    public void setLugares(Set<LugarEntrenamiento> lugares) {
        this.lugares = lugares;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((duracion == null) ? 0 : duracion.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((limitaciones == null) ? 0 : limitaciones.hashCode());
        result = prime * result + ((lugares == null) ? 0 : lugares.hashCode());
        result = prime * result + ((precio == null) ? 0 : precio.hashCode());
        result = prime * result + ((servicio == null) ? 0 : servicio.hashCode());
        result = prime * result + ((tipoDuracion == null) ? 0 : tipoDuracion.hashCode());
        result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
        Tarifa other = (Tarifa) obj;
        if (duracion == null) {
            if (other.duracion != null)
                return false;
        } else if (!duracion.equals(other.duracion))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (limitaciones == null) {
            if (other.limitaciones != null)
                return false;
        } else if (!limitaciones.equals(other.limitaciones))
            return false;
        if (lugares == null) {
            if (other.lugares != null)
                return false;
        } else if (!lugares.equals(other.lugares))
            return false;
        if (precio == null) {
            if (other.precio != null)
                return false;
        } else if (!precio.equals(other.precio))
            return false;
        if (servicio == null) {
            if (other.servicio != null)
                return false;
        } else if (!servicio.equals(other.servicio))
            return false;
        if (tipoDuracion != other.tipoDuracion)
            return false;
        if (titulo == null) {
            if (other.titulo != null)
                return false;
        } else if (!titulo.equals(other.titulo))
            return false;
        return true;
    }

    public Tarifa() {
    }
    
    
    
    
}