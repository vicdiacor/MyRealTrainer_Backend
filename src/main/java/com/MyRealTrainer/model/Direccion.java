package com.MyRealTrainer.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "direcciones")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=100)
    private String calle;

    @Size(max=5)
    private String numero;

    @Size(max=5)
    private String piso;


    @Size(max=100)
    private String ciudad;

    @Size(max=100)
    private String provincia;
    
    @Digits(fraction=0,integer=5)
    private Integer codigoPostal;

    @OneToOne(optional = false)
    @JoinColumn(name="lugar_id")
    @JsonIgnore
    private LugarEntrenamiento lugar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

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

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public LugarEntrenamiento getLugar() {
        return lugar;
    }

    public void setLugar(LugarEntrenamiento lugar) {
        this.lugar = lugar;
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
        Direccion other = (Direccion) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Direccion() {
    }

    public void fillFields(){
     
        this.ciudad="Sevilla";
        this.calle="Calle Feria";
        this.numero="2";
        this.piso="3º A";
        this.provincia="Sevilla";
        this.codigoPostal=41013;


    }

    
}