package com.MyRealTrainer.repository;

import org.springframework.dao.DataAccessException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.Servicio;
import com.MyRealTrainer.model.Usuario;

public interface ServicioEntrenamientoRepository extends JpaRepository<Servicio, Long> {




    

}
