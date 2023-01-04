package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.SerieRepository;
import com.MyRealTrainer.model.Serie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SerieService {

	@Autowired
    private SerieRepository serieRepository;

  
    @Transactional
    public Serie save(Serie serie){ 
        return serieRepository.save(serie);
    }

    @Transactional
    public void deleteById(Long id){ 
        serieRepository.deleteById(id);
    }    
 
    
}
