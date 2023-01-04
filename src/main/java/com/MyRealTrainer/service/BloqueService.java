package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.BloqueRepository;


import com.MyRealTrainer.model.Bloque;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BloqueService {

	@Autowired
    private BloqueRepository bloqueRepository;

  
    @Transactional
    public Bloque save(Bloque bloque){ 
        return bloqueRepository.save(bloque);
    }

    @Transactional
    public void deleteById(Long id){ 
        bloqueRepository.deleteById(id);
    }    
 
    
}
