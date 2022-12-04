package com.MyRealTrainer.model;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "etiquetas")
public class Etiqueta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=80)
    private String nombre;
    
    @NonNull
    @Min(1)
    private Integer ordenJerarquia;
    
    // Relaciones
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Etiqueta etiquetaPadre;
 
    @OneToMany(mappedBy = "etiquetaPadre")
    private Set<Etiqueta> etiquetasHija = new HashSet<>();

   

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "etiqueta_ejercicios", 
               joinColumns = @JoinColumn(name = "etiqueta_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ejercicios_id", referencedColumnName = "id"))
    private Set<Ejercicio> ejercicios = new HashSet<>();

     // Métodos

    public Etiqueta addEtiquetaHija(String nombreEtiqueta) {
        Etiqueta subetiqueta = new Etiqueta();
        subetiqueta.setNombre(nombreEtiqueta);
        this.etiquetasHija.add(subetiqueta);
        setEtiquetasHija(etiquetasHija);
        return subetiqueta;
    }

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

    public Integer getOrdenJerarquia() {
        return ordenJerarquia;
    }

    public void setOrdenJerarquia(Integer ordenJerarquia) {
        this.ordenJerarquia = ordenJerarquia;
    }

    public Etiqueta getEtiquetaPadre() {
        return etiquetaPadre;
    }

    public void setEtiquetaPadre(Etiqueta etiquetaPadre) {
        this.etiquetaPadre = etiquetaPadre;
    }

    public Set<Etiqueta> getEtiquetasHija() {
        return etiquetasHija;
    }

    public void setEtiquetasHija(Set<Etiqueta> etiquetasHija) {
        this.etiquetasHija = etiquetasHija;
    }

    public Set<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(Set<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ejercicios == null) ? 0 : ejercicios.hashCode());
        result = prime * result + ((etiquetaPadre == null) ? 0 : etiquetaPadre.hashCode());
        result = prime * result + ((etiquetasHija == null) ? 0 : etiquetasHija.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((ordenJerarquia == null) ? 0 : ordenJerarquia.hashCode());
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
        Etiqueta other = (Etiqueta) obj;
        if (ejercicios == null) {
            if (other.ejercicios != null)
                return false;
        } else if (!ejercicios.equals(other.ejercicios))
            return false;
        if (etiquetaPadre == null) {
            if (other.etiquetaPadre != null)
                return false;
        } else if (!etiquetaPadre.equals(other.etiquetaPadre))
            return false;
        if (etiquetasHija == null) {
            if (other.etiquetasHija != null)
                return false;
        } else if (!etiquetasHija.equals(other.etiquetasHija))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (ordenJerarquia == null) {
            if (other.ordenJerarquia != null)
                return false;
        } else if (!ordenJerarquia.equals(other.ordenJerarquia))
            return false;
        return true;
    }

    public Etiqueta() {
    }
    
}