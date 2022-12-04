package com.MyRealTrainer.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.dao.DataAccessException;


import com.MyRealTrainer.model.Ejercicio;


public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {

    @Query(value = "SELECT  e.* FROM Ejercicios e WHERE e.entrenador_id=:entrenador_id", nativeQuery=true)
    public List<Ejercicio>  findByEntrenadorId(@Param("entrenador_id") Long entrenador_id) throws DataAccessException;


}
