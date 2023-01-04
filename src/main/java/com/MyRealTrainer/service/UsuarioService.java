package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.UsuarioRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.MyRealTrainer.model.Role;
import com.MyRealTrainer.model.Usuario;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

	@Autowired
    private UsuarioRepository repository;

    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
	public List<Usuario> findAll(){
        return repository.findAll();
    }

	public Optional<Usuario> findById(Long id){ 
        return repository.findById(id);
    }

    public Optional<Usuario> findByEmail(String email){ 
        return repository.findByEmail(email);
    }

    public Usuario findByNameOrEmail(String name, String email){ 
        return repository.findByNombreOrEmail(name,email).orElse(null);
    }

    @Transactional
    public Boolean existsByEmail(String email){ 
        return repository.existsByEmail(email);
    }

    public Usuario updateUser(Usuario updatedUser, Usuario currentUser){
        BeanUtils.copyProperties(updatedUser, currentUser, "id");
        this.save(currentUser);
        return currentUser;
        
    }

    public Usuario registerNewUser(Usuario usuario){
        // create user object
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Date fechaNacimiento = usuario.getFechaNacimiento();
        fechaNacimiento.setHours(0);
        fechaNacimiento.setMinutes(0);
        fechaNacimiento.setSeconds(0);
        usuario.setFechaNacimiento(fechaNacimiento);

        // add URL Foto perfil -- Falta por hacer

        // add Tipo de rol cliente

        Optional<Role> roles = roleService.findByName("ROLE_CLIENTE");
        if(roles.isPresent()){
            usuario.setRoles(Collections.singleton(roles.get()));
        }else{
            Role rolCliente= new Role();
            rolCliente.setName("ROLE_CLIENTE");
            roleService.save(rolCliente);
            usuario.setRoles(Collections.singleton(rolCliente));

        }
        return this.save(usuario);

    }
 

    @Transactional
    public Usuario save(Usuario Usuario){ 
        return repository.save(Usuario);
    }

    @Transactional
    public void deleteById(Long id){
        repository.deleteById(id);
    }
    
}
