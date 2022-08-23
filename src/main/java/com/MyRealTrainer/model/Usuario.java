package com.MyRealTrainer.model;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 25)
    private String nombre;

    @NotBlank
    @Size(max = 320)
    @Column(unique=true)
    @Email
    private String email;

    @NotBlank
    private String password;
  
    @NotBlank
    @Size(max = 50)
    private String apellidos;

    @NotBlank
    @Size(max = 100)
    private String localidad;

    @NonNull
    @Past
    private Date fechaNacimiento;


    private String urlFotoPerfil;
    
    // relaciones
    @OneToMany(mappedBy = "cliente")
    private List<Contrato> contratos;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_roles", 
               joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    //Métodos

    public Usuario(){
    }
    
    public Usuario(long id){
        this.id = id;
    }

    public Usuario orElseThrow(Object object) {
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
        result = prime * result + ((contratos == null) ? 0 : contratos.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((localidad == null) ? 0 : localidad.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
        result = prime * result + ((urlFotoPerfil == null) ? 0 : urlFotoPerfil.hashCode());
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
        Usuario other = (Usuario) obj;
        if (apellidos == null) {
            if (other.apellidos != null)
                return false;
        } else if (!apellidos.equals(other.apellidos))
            return false;
        if (contratos == null) {
            if (other.contratos != null)
                return false;
        } else if (!contratos.equals(other.contratos))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (fechaNacimiento == null) {
            if (other.fechaNacimiento != null)
                return false;
        } else if (!fechaNacimiento.equals(other.fechaNacimiento))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (localidad == null) {
            if (other.localidad != null)
                return false;
        } else if (!localidad.equals(other.localidad))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (roles == null) {
            if (other.roles != null)
                return false;
        } else if (!roles.equals(other.roles))
            return false;
        if (urlFotoPerfil == null) {
            if (other.urlFotoPerfil != null)
                return false;
        } else if (!urlFotoPerfil.equals(other.urlFotoPerfil))
            return false;
        return true;
    }

    


   
    

}
