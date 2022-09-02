package com.MyRealTrainer.repository;

import org.springframework.dao.DataAccessException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.MyRealTrainer.model.LugarEntrenamiento;

import java.util.Optional;

public interface LugarEntrenamientoRepository extends JpaRepository<LugarEntrenamiento, Long> {




    

}
