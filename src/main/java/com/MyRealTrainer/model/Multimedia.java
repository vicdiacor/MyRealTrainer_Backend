package com.MyRealTrainer.model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "multimedia")
public class Multimedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=80)
    @NotBlank
    private String urlMultimedia;

    @ManyToOne(optional = false)
    @JoinColumn(name="ejercicio_id")
    private Ejercicio ejercicio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlMultimedia() {
        return urlMultimedia;
    }

    public void setUrlMultimedia(String urlMultimedia) {
        this.urlMultimedia = urlMultimedia;
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
        result = prime * result + ((ejercicio == null) ? 0 : ejercicio.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((urlMultimedia == null) ? 0 : urlMultimedia.hashCode());
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
        Multimedia other = (Multimedia) obj;
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
        if (urlMultimedia == null) {
            if (other.urlMultimedia != null)
                return false;
        } else if (!urlMultimedia.equals(other.urlMultimedia))
            return false;
        return true;
    }

    public Multimedia() {
    }

    
    
}