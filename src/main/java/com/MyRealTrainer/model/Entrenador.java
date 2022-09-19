package com.MyRealTrainer.model;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "entrenadores")
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private boolean esPublico;

    @NotBlank
    @Size(max = 400)
    private String formacion;
    
    @Size(max = 500)
    private String descripcionSobreMi;

   
    @Size(max = 500)
    private String descripcionExperiencia;

    // Relaciones

    
    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="usuario_id")
    @JsonIgnore
    private Usuario usuario;

    @OneToMany(mappedBy = "entrenador")
    private List<Servicio> servicios;

    
    @OneToMany(mappedBy = "entrenador")
    private List<LugarEntrenamiento> lugares;

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

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Usuario getUsuario() {
      
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
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
        Entrenador other = (Entrenador) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Entrenador() {
    }

    public void fillFields(){
        this.formacion="A correct training description that has less than 400 characters: I studied sports science at the university of Seville";
        this.descripcionExperiencia="A correct experience description that has less than 500 characters: I have been working in different gyms as monitor the last 10 years in Seville";
        this.descripcionSobreMi="A correct description about me that has less than 500 characters: I have practised sports since i was 6 years old and i love to help people to get their objectives at the gym";
        this.esPublico=true;
    }

    public interface CreateValidation{
        
    }

    public interface AdvancedValidation{
        
    }

    public List<LugarEntrenamiento> getLugares() {
        return lugares;
    }

    public void setLugares(List<LugarEntrenamiento> lugares) {
        this.lugares = lugares;
    }

}