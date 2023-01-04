package com.MyRealTrainer.repository;

import org.springframework.dao.DataAccessException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


import com.MyRealTrainer.model.Rutina;


public interface RutinaRepository extends JpaRepository<Rutina, Long> {
    
    @Query(value = "SELECT  r.* FROM RUTINAS r WHERE r.entrenador_id=:entrenador_id", nativeQuery=true)
    public List<Rutina>  findMyRutinas(@Param("entrenador_id") Long id) throws DataAccessException;

}
