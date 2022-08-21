package com.MyRealTrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.MyRealTrainer.model.Rol;
import com.MyRealTrainer.model.TipoRol;

public interface RolRepository extends JpaRepository<Rol, Long>{

    Optional<Rol> findByTipoRol(TipoRol nombre);
    
    
}
