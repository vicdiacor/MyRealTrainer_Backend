package com.MyRealTrainer.model;
import java.util.Date;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "valores_metricas")
public class ValorMetrica {
    
    // Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@NonNull
	private Date fechaRegistro;

    // Relationships
   
    @ManyToOne(optional = false)
    @JoinColumn(name="metrica_id")
    private Metrica metrica;


    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public Date getFechaRegistro() {
        return fechaRegistro;
    }



    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }



    public Metrica getMetrica() {
        return metrica;
    }



    public void setMetrica(Metrica metrica) {
        this.metrica = metrica;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fechaRegistro == null) ? 0 : fechaRegistro.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((metrica == null) ? 0 : metrica.hashCode());
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
        ValorMetrica other = (ValorMetrica) obj;
        if (fechaRegistro == null) {
            if (other.fechaRegistro != null)
                return false;
        } else if (!fechaRegistro.equals(other.fechaRegistro))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (metrica == null) {
            if (other.metrica != null)
                return false;
        } else if (!metrica.equals(other.metrica))
            return false;
        return true;
    }

    public ValorMetrica() {
    }

    
}