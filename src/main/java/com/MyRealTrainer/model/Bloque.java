package com.MyRealTrainer.model;
import java.time.Duration;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Min;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "bloques")
public class Bloque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private Duration tiempoEntreSeries;

    @NonNull
    @Min(0)
    private Integer numOrden;
    
    @OneToMany(mappedBy = "bloque")
    private List<Serie> series;

    @ManyToOne(optional = false)
    @JoinColumn(name="entrenamiento_id")
    private Entrenamiento entrenamiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Duration getTiempoEntreSeries() {
        return tiempoEntreSeries;
    }

    public void setTiempoEntreSeries(Duration tiempoEntreSeries) {
        this.tiempoEntreSeries = tiempoEntreSeries;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

    public Entrenamiento getEntrenamiento() {
        return entrenamiento;
    }

    public void setEntrenamiento(Entrenamiento entrenamiento) {
        this.entrenamiento = entrenamiento;
    }

    public Integer getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(Integer numOrden) {
        this.numOrden = numOrden;
    }

    public Bloque() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entrenamiento == null) ? 0 : entrenamiento.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((numOrden == null) ? 0 : numOrden.hashCode());
        result = prime * result + ((series == null) ? 0 : series.hashCode());
        result = prime * result + ((tiempoEntreSeries == null) ? 0 : tiempoEntreSeries.hashCode());
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
        Bloque other = (Bloque) obj;
        if (entrenamiento == null) {
            if (other.entrenamiento != null)
                return false;
        } else if (!entrenamiento.equals(other.entrenamiento))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (numOrden == null) {
            if (other.numOrden != null)
                return false;
        } else if (!numOrden.equals(other.numOrden))
            return false;
        if (series == null) {
            if (other.series != null)
                return false;
        } else if (!series.equals(other.series))
            return false;
        if (tiempoEntreSeries == null) {
            if (other.tiempoEntreSeries != null)
                return false;
        } else if (!tiempoEntreSeries.equals(other.tiempoEntreSeries))
            return false;
        return true;
    }

    

    
    
}