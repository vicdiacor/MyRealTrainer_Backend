package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.EntrenamientoRepository;


import com.MyRealTrainer.model.Entrenamiento;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntrenamientoService {

	@Autowired
    private EntrenamientoRepository entrenamientoRepository;

  
    @Transactional
    public Entrenamiento save(Entrenamiento entrenamiento){ 
        return entrenamientoRepository.save(entrenamiento);
    }

    @Transactional
    public void deleteById(Long id){ 
        entrenamientoRepository.deleteById(id);
    }

    
 
    
}
