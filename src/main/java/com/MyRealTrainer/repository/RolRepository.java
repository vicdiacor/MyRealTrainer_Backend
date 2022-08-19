package com.MyRealTrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.MyRealTrainer.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{

    Optional<Rol> findByNombre(String nombre);
    
    
}
