package com.MyRealTrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.MyRealTrainer.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByNombreOrEmail(String nombre, String email);
    Optional<Usuario> findByNombre(String nombre);
    Boolean existsByNombre(String nombre);
    Boolean existsByEmail(String email);

}
