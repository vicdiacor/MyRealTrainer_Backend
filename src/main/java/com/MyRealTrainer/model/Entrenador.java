package com.MyRealTrainer.model;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

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

    @OneToMany(mappedBy = "entrenador")
    private List<LugarEntrenamiento> lugares;
    
    @OneToOne(fetch = FetchType.LAZY,optional = false)
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

    public Usuario getUsuario() {
        if(this.usuario!=null){
            this.usuario.setEntrenador(null);
        }
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

    public interface CreateValidation{
        
    }

    public interface AdvancedValidation{
        
    }

}