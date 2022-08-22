package com.MyRealTrainer.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "entrenadores")
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NonNull
    private boolean esPublico;

    @NotBlank
    @Size(max = 400)
    private String formacion;
    
    @Size(max = 500)
    private String descripcionSobreMi;

    @NotBlank
    @Size(max = 500)
    private String descripcionExperiencia;

    // Relaciones

    @OneToMany(mappedBy = "entrenador")
    private List<LugarEntrenamiento> lugares;
    
    @OneToOne(optional = false)
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "entrenador")
    private List<Servicio> servicios;

    @OneToMany(mappedBy = "entrenador")
    private List<Contrato> contratos;

    // Métodos

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEsPublico() {
        return esPublico;
    }

    public void setEsPublico(boolean esPublico) {
        this.esPublico = esPublico;
    }

    public String getFormacion() {
        return formacion;
    }

    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }

    public String getDescripcionSobreMi() {
        return descripcionSobreMi;
    }

    public void setDescripcionSobreMi(String descripcionSobreMi) {
        this.descripcionSobreMi = descripcionSobreMi;
    }

    public String getDescripcionExperiencia() {
        return descripcionExperiencia;
    }

    public void setDescripcionExperiencia(String descripcionExperiencia) {
        this.descripcionExperiencia = descripcionExperiencia;
    }

    public List<LugarEntrenamiento> getLugares() {
        return lugares;
    }

    public void setLugares(List<LugarEntrenamiento> lugares) {
        this.lugares = lugares;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((descripcionExperiencia == null) ? 0 : descripcionExperiencia.hashCode());
        result = prime * result + ((descripcionSobreMi == null) ? 0 : descripcionSobreMi.hashCode());
        result = prime * result + (esPublico ? 1231 : 1237);
        result = prime * result + ((formacion == null) ? 0 : formacion.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lugares == null) ? 0 : lugares.hashCode());
        result = prime * result + ((servicios == null) ? 0 : servicios.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Entrenador other = (Entrenador) obj;
        if (descripcionExperiencia == null) {
            if (other.descripcionExperiencia != null)
                return false;
        } else if (!descripcionExperiencia.equals(other.descripcionExperiencia))
            return false;
        if (descripcionSobreMi == null) {
            if (other.descripcionSobreMi != null)
                return false;
        } else if (!descripcionSobreMi.equals(other.descripcionSobreMi))
            return false;
        if (esPublico != other.esPublico)
            return false;
        if (formacion == null) {
            if (other.formacion != null)
                return false;
        } else if (!formacion.equals(other.formacion))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (lugares == null) {
            if (other.lugares != null)
                return false;
        } else if (!lugares.equals(other.lugares))
            return false;
        if (servicios == null) {
            if (other.servicios != null)
                return false;
        } else if (!servicios.equals(other.servicios))
            return false;
        return true;
    }

    public Entrenador() {
    }

}