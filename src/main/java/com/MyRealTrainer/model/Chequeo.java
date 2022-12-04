package com.MyRealTrainer.model;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "chequeos")
public class Chequeo {
    
    // Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=80)
    private String titulo;
    
    @Size(max=300)
    private String descripcion;

    // Relationships

    @OneToMany(mappedBy = "chequeo")
    private Set<Metrica> metricas = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name="contrato_id")
    private Contrato cliente;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Metrica> getMetricas() {
        return metricas;
    }

    public void setMetricas(Set<Metrica> metricas) {
        this.metricas = metricas;
    }

    public Contrato getCliente() {
        return cliente;
    }

    public void setCliente(Contrato cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((metricas == null) ? 0 : metricas.hashCode());
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
        Chequeo other = (Chequeo) obj;
        if (cliente == null) {
            if (other.cliente != null)
                return false;
        } else if (!cliente.equals(other.cliente))
            return false;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (metricas == null) {
            if (other.metricas != null)
                return false;
        } else if (!metricas.equals(other.metricas))
            return false;
        if (titulo == null) {
            if (other.titulo != null)
                return false;
        } else if (!titulo.equals(other.titulo))
            return false;
        return true;
    }

    public Chequeo() {
    }
    

}