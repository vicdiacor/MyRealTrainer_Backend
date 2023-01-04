package com.MyRealTrainer.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.MyRealTrainer.model.Servicio;

public interface ServicioEntrenamientoRepository extends JpaRepository<Servicio, Long> {
    
    @Query(value = "SELECT  s.* FROM Servicios s WHERE s.entrenador_id=:entrenador_id", nativeQuery=true)
    public List<Servicio>  findMyServicios(@Param("entrenador_id") Long id) throws DataAccessException;

}
