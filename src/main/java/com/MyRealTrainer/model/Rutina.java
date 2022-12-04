package com.MyRealTrainer.model;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "rutinas")
public class Rutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=80)
    private String titulo;
    
    @NotBlank
    @Size(max=300)
    private String descripcion;

    // Relaciones

    @ManyToOne(optional = true)
    @JoinColumn(name="entrenador_id")
    private Entrenador entrenador;

    @ManyToOne(optional = true)
    @JoinColumn(name="contrato_id")
    private Contrato contrato;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "rutina_etiquetas", 
               joinColumns = @JoinColumn(name = "rutina_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "etiquetas_id", referencedColumnName = "id"))
    private Set<Etiqueta> etiquetas = new HashSet<>();

    @OneToMany(mappedBy = "rutina")
    private List<Entrenamiento> entrenamientos;
    
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Set<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(Set<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contrato == null) ? 0 : contrato.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((entrenador == null) ? 0 : entrenador.hashCode());
        result = prime * result + ((entrenamientos == null) ? 0 : entrenamientos.hashCode());
        result = prime * result + ((etiquetas == null) ? 0 : etiquetas.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Rutina other = (Rutina) obj;
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
        if (entrenador == null) {
            if (other.entrenador != null)
                return false;
        } else if (!entrenador.equals(other.entrenador))
            return false;
        if (entrenamientos == null) {
            if (other.entrenamientos != null)
                return false;
        } else if (!entrenamientos.equals(other.entrenamientos))
            return false;
        if (etiquetas == null) {
            if (other.etiquetas != null)
                return false;
        } else if (!etiquetas.equals(other.etiquetas))
            return false;
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
        return true;
    }

    public Rutina() {
    }

}