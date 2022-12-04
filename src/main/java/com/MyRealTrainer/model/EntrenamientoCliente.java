package com.MyRealTrainer.model;
import java.util.Date;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "entrenamientos_cliente")
public class EntrenamientoCliente extends Entrenamiento {

 
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@NonNull
	private Date fechaPlaneada;
    
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@NonNull
	private Date fechaRealizada;

    public Date getFechaPlaneada() {
        return fechaPlaneada;
    }

    public void setFechaPlaneada(Date fechaPlaneada) {
        this.fechaPlaneada = fechaPlaneada;
    }

    public Date getFechaRealizada() {
        return fechaRealizada;
    }

    public void setFechaRealizada(Date fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }

    public EntrenamientoCliente() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((fechaPlaneada == null) ? 0 : fechaPlaneada.hashCode());
        result = prime * result + ((fechaRealizada == null) ? 0 : fechaRealizada.hashCode());
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
        EntrenamientoCliente other = (EntrenamientoCliente) obj;
        if (fechaPlaneada == null) {
            if (other.fechaPlaneada != null)
                return false;
        } else if (!fechaPlaneada.equals(other.fechaPlaneada))
            return false;
        if (fechaRealizada == null) {
            if (other.fechaRealizada != null)
                return false;
        } else if (!fechaRealizada.equals(other.fechaRealizada))
            return false;
        return true;
    }

    
}