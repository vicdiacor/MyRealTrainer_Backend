package com.MyRealTrainer.model;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ejercicios")
public class Ejercicio {

    // Atributos 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Ejercicio() {
    }

    @NotBlank
    @Size(max=100)
    private String titulo;
    
    @Size(max=500)
    private String preparacion;

    @Size(max=500)
    private String ejecucion;

    @Size(max=500)
    private String consejos;

   
    private String imagenURL;

    // Relationships

    
    @OneToMany(mappedBy = "ejercicio",cascade = CascadeType.REMOVE)
    private List<Multimedia> recursosMultimedia;

   

    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "ejercicio_etiquetas", 
               joinColumns = @JoinColumn(name = "ejercicio_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "etiquetas_id", referencedColumnName = "id"))
    private Set<Etiqueta> etiquetas = new HashSet<>();

    @ManyToOne(optional = true)
    @JoinColumn(name="entrenador_id")
    private Entrenador entrenador;

    // Methods

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

    public String getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }

    public String getEjecucion() {
        return ejecucion;
    }

    public void setEjecucion(String ejecucion) {
        this.ejecucion = ejecucion;
    }

    public String getConsejos() {
        return consejos;
    }

    public void setConsejos(String consejos) {
        this.consejos = consejos;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    public List<Multimedia> getRecursosMultimedia() {
        return recursosMultimedia;
    }

    public void setRecursosMultimedia(List<Multimedia> recursosMultimedia) {
        this.recursosMultimedia = recursosMultimedia;
    }


    public Set<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(Set<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
        result = prime * result + ((preparacion == null) ? 0 : preparacion.hashCode());
        result = prime * result + ((ejecucion == null) ? 0 : ejecucion.hashCode());
        result = prime * result + ((consejos == null) ? 0 : consejos.hashCode());
        result = prime * result + ((imagenURL == null) ? 0 : imagenURL.hashCode());
        result = prime * result + ((recursosMultimedia == null) ? 0 : recursosMultimedia.hashCode());
        result = prime * result + ((etiquetas == null) ? 0 : etiquetas.hashCode());
        result = prime * result + ((entrenador == null) ? 0 : entrenador.hashCode());
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
        Ejercicio other = (Ejercicio) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (titulo == null) {
            if (other.titulo != null)
                return false;
        } else if (!titulo.equals(other.titulo))
            return false;
        if (preparacion == null) {
            if (other.preparacion != null)
                return false;
        } else if (!preparacion.equals(other.preparacion))
            return false;
        if (ejecucion == null) {
            if (other.ejecucion != null)
                return false;
        } else if (!ejecucion.equals(other.ejecucion))
            return false;
        if (consejos == null) {
            if (other.consejos != null)
                return false;
        } else if (!consejos.equals(other.consejos))
            return false;
        if (imagenURL == null) {
            if (other.imagenURL != null)
                return false;
        } else if (!imagenURL.equals(other.imagenURL))
            return false;
        if (recursosMultimedia == null) {
            if (other.recursosMultimedia != null)
                return false;
        } else if (!recursosMultimedia.equals(other.recursosMultimedia))
            return false;
        if (etiquetas == null) {
            if (other.etiquetas != null)
                return false;
        } else if (!etiquetas.equals(other.etiquetas))
            return false;
        if (entrenador == null) {
            if (other.entrenador != null)
                return false;
        } else if (!entrenador.equals(other.entrenador))
            return false;
        return true;
    }

    
    
}