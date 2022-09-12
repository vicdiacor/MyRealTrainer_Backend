package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.EjercicioRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import com.MyRealTrainer.model.Ejercicio;
import com.MyRealTrainer.model.Entrenador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EjercicioService {

	@Autowired
    private EjercicioRepository ejercicioRepository;


    
	public Optional<Ejercicio> findById(Long id){ 
        return ejercicioRepository.findById(id);
    }

    public  Map<String,Object> createEjercicio(Ejercicio ejercicio, Entrenador entrenador){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<String>();
        ejercicio.setEntrenador(entrenador);
        response.put("servicio", this.save(ejercicio));
        return response;
    }


    @Transactional
    public Ejercicio save(Ejercicio ejercicio){ 
        return ejercicioRepository.save(ejercicio);
    }

    @Transactional
    public void deleteById(Long id){ 
        ejercicioRepository.deleteById(id);
    }

  
    
}
