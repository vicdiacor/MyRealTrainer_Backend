package com.MyRealTrainer.model;
import javax.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NonNull
    @Enumerated(EnumType.STRING)
    private TipoRol tipoRol;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTipoRol() {
        return tipoRol.toString();
    }


    public void setTipoRol(TipoRol tipoRol) {
        this.tipoRol = tipoRol;
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((tipoRol == null) ? 0 : tipoRol.hashCode());
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
        Rol other = (Rol) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (tipoRol != other.tipoRol)
            return false;
        return true;
    }

    public Rol() {
    }

    
}