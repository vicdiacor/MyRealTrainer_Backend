package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.UsuarioRepository;

import java.util.List;

import com.MyRealTrainer.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
    private UsuarioRepository repository;
    
    
	public List<Usuario> findAll(){
        return repository.findAll();
    }

	public Usuario findById(Long id){ 
        return repository.findById(id).orElse(null);
    }

    public Usuario findByEmail(String email){ 
        return repository.findByEmail(email).orElse(null);
    }

    public Usuario findByNameOrEmail(String name, String email){ 
        return repository.findByNombreOrEmail(name,email).orElse(null);
    }

    public Boolean existsByEmail(String email){ 
        return repository.existsByEmail(email);
    }

 


    public Usuario save(Usuario Usuario){ 
        return repository.save(Usuario);
    }

    public void deleteById(Long id){ 
        repository.deleteById(id);
    }
    
}
