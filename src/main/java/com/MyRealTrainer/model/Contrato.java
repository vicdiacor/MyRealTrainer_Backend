package com.MyRealTrainer.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "contratos")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=500)
    private String descripcion;

    @Size(max=500)
    private String limitaciones;

    @NonNull
    @Min(0)
    @Digits(fraction=2,integer=5)
    private Double precioTotal;

    //Relaciones

    @ManyToOne(optional = false)
    @JoinColumn(name="cliente_id")
    private Usuario cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name="entrenador_id")
    private Entrenador entrenador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLimitaciones() {
        return limitaciones;
    }

    public void setLimitaciones(String limitaciones) {
        this.limitaciones = limitaciones;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
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
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((entrenador == null) ? 0 : entrenador.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((limitaciones == null) ? 0 : limitaciones.hashCode());
        result = prime * result + ((precioTotal == null) ? 0 : precioTotal.hashCode());
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
        Contrato other = (Contrato) obj;
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
        if (entrenador == null) {
            if (other.entrenador != null)
                return false;
        } else if (!entrenador.equals(other.entrenador))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (limitaciones == null) {
            if (other.limitaciones != null)
                return false;
        } else if (!limitaciones.equals(other.limitaciones))
            return false;
        if (precioTotal == null) {
            if (other.precioTotal != null)
                return false;
        } else if (!precioTotal.equals(other.precioTotal))
            return false;
        return true;
    }

    public Contrato() {
    }

    
    
}