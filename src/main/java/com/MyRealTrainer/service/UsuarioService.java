package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import com.MyRealTrainer.model.Usuario;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

	@Autowired
    private UsuarioRepository repository;
    
    
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

 

    @Transactional
    public Usuario save(Usuario Usuario){ 
        return repository.save(Usuario);
    }

    @Transactional
    public void deleteById(Long id){ 
        repository.deleteById(id);
    }
    
}
