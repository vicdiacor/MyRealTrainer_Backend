package com.MyRealTrainer.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "lugares_entrenamiento")
public class LugarEntrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    @NotBlank
    @Size(max=50)
    private String titulo;

    @Size(max=300)
    private String descripcion;

    @Size(max=100)
    private String direccion;

    @Size(max=100)
    private String ciudad;

    @Size(max=100)
    private String provincia;
    
    @Size(max=100)
    private String codigoPostal;

    @NonNull
    @Enumerated(EnumType.STRING)
    private TipoLugar tipoLugar;
    

   // Relationships
   
    public String getCiudad() {
        return ciudad;
    }


    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }


    public String getProvincia() {
        return provincia;
    }


    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }


    public String getCodigoPostal() {
        return codigoPostal;
    }


    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name="entrenador_id")
    @JsonIgnore
    private Entrenador entrenador;

  
    @ManyToOne(optional = true)
    @JoinColumn(name="contrato_id")
    @JsonIgnore
    private Contrato contrato;


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


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getDireccion() {
        return direccion;
    }


    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public TipoLugar getTipoLugar() {
        return tipoLugar;
    }


    public void setTipoLugar(TipoLugar tipoLugar) {
        this.tipoLugar = tipoLugar;
    }


    public Entrenador getEntrenador() {
        if(this.entrenador!=null){
            this.entrenador.getUsuario().setEntrenador(null);
        }
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
        LugarEntrenamiento other = (LugarEntrenamiento) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    public LugarEntrenamiento() {
    }

    public LugarEntrenamiento(String titulo,TipoLugar tipo,Entrenador entrenador) {
        this.titulo=titulo;
        this.tipoLugar=tipo;
        this.entrenador=entrenador;

    }


    

    
}