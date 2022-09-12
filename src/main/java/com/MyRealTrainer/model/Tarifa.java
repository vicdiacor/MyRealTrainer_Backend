package com.MyRealTrainer.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tarifa_lugares", 
               joinColumns = @JoinColumn(name = "tarifa_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "lugar_id", referencedColumnName = "id"))
    private List<LugarEntrenamiento> lugares = new ArrayList<>();

    @ManyToOne(optional = true)
    @JoinColumn(name="servicio_id")
    @JsonIgnore
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Tarifa() {
    }

    public List<LugarEntrenamiento> getLugares() {
        return lugares;
    }

    public void setLugares(List<LugarEntrenamiento> lugares) {
        this.lugares = lugares;
    }
    
    
    
    
}