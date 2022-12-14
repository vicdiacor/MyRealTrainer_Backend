package com.MyRealTrainer.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "lugares_entrenamiento")
public class LugarEntrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    @NotBlank
    @Size(max=80)
    private String titulo;

    @Size(max=300)
    private String descripcion;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoLugar tipoLugar;
    
    // Relationships

   
    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name="entrenador_id")
    @JsonIgnore
    private Entrenador entrenador;

  
    @ManyToOne(optional = true)
    @JoinColumn(name="contrato_id")
    @JsonIgnore
    private Contrato contrato;

    
    @OneToOne(optional = true,mappedBy = "lugar")
    private Direccion direccion;

    // Methods
    

   


    public Long getId() {
        return id;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contrato == null) ? 0 : contrato.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
        result = prime * result + ((entrenador == null) ? 0 : entrenador.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((tipoLugar == null) ? 0 : tipoLugar.hashCode());
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
        LugarEntrenamiento other = (LugarEntrenamiento) obj;
        if (contrato == null) {
            if (other.contrato != null)
                return false;
        } else if (!contrato.equals(other.contrato))
            return false;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (direccion == null) {
            if (other.direccion != null)
                return false;
        } else if (!direccion.equals(other.direccion))
            return false;
        if (entrenador == null) {
            if (other.entrenador != null)
                return false;
        } else if (!entrenador.equals(other.entrenador))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (tipoLugar != other.tipoLugar)
            return false;
        if (titulo == null) {
            if (other.titulo != null)
                return false;
        } else if (!titulo.equals(other.titulo))
            return false;
        return true;
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


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public TipoLugar getTipoLugar() {
        return tipoLugar;
    }


    public void setTipoLugar(TipoLugar tipoLugar) {
        this.tipoLugar = tipoLugar;
    }


    public Entrenador getEntrenador() {
        return entrenador;
    }


    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }


    public Contrato getContrato() {
        return contrato;
    }


    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }


    public Direccion getDireccion() {
        return direccion;
    }


    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

   

    public LugarEntrenamiento() {
    }


    public LugarEntrenamiento(String titulo, TipoLugar tipoLugar, Entrenador entrenador) {
        this.titulo = titulo;
        this.tipoLugar = tipoLugar;
        this.entrenador = entrenador;
    }
    
    public void fillFields(){
        this.titulo="My gym";
        this.tipoLugar=TipoLugar.MI_GIMNASIO;
        this.descripcion="Gym with capacity for 100 people and all kinds of equipment, both for bodybuilding and functional training";
        
    }
    
}