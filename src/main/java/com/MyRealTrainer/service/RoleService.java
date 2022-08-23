package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.RoleRepository;
import com.MyRealTrainer.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import com.MyRealTrainer.model.Role;
import com.MyRealTrainer.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

	@Autowired
    private RoleRepository repository;
    
    
    
    public Optional<Role> findByName(String name){
        return repository.findByName(name);
    }
 

    @Transactional
    public Role save(Role role){ 
        return repository.save(role);
    }

    @Transactional
    public void deleteById(Long id){ 
        repository.deleteById(id);
    }
    
}
