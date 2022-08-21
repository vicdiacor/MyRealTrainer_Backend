package com.MyRealTrainer.model;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "metricas")
public class Metrica {
    
    // Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=80)
    private String nombre;
   
    @Size(max=300)
    private String descripcion;

    @NonNull
    @Enumerated(EnumType.STRING)
    private TipoMetrica tipoMetrica;

    @NonNull
    private boolean enVigor;

    // Relationships
   
    @ManyToOne(optional = false)
    @JoinColumn(name="chequeo_id")
    private Chequeo chequeo;

    
    @OneToMany(mappedBy = "metrica")
    private Set<ValorMetrica> metricas = new HashSet<>();

    // Methods

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public TipoMetrica getTipoMetrica() {
        return tipoMetrica;
    }


    public void setTipoMetrica(TipoMetrica tipoMetrica) {
        this.tipoMetrica = tipoMetrica;
    }


    public Chequeo getChequeo() {
        return chequeo;
    }


    public void setChequeo(Chequeo chequeo) {
        this.chequeo = chequeo;
    }


    public Set<ValorMetrica> getMetricas() {
        return metricas;
    }


    public void setMetricas(Set<ValorMetrica> metricas) {
        this.metricas = metricas;
    }


    public boolean isEnVigor() {
        return enVigor;
    }


    public void setEnVigor(boolean enVigor) {
        this.enVigor = enVigor;
    }

    public Metrica() {
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((chequeo == null) ? 0 : chequeo.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + (enVigor ? 1231 : 1237);
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((metricas == null) ? 0 : metricas.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((tipoMetrica == null) ? 0 : tipoMetrica.hashCode());
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
        Metrica other = (Metrica) obj;
        if (chequeo == null) {
            if (other.chequeo != null)
                return false;
        } else if (!chequeo.equals(other.chequeo))
            return false;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (enVigor != other.enVigor)
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
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (tipoMetrica != other.tipoMetrica)
            return false;
        return true;
    }

    
}