package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.DireccionRepository;
import java.util.Optional;


import com.MyRealTrainer.model.Direccion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DireccionService {

	@Autowired
    private DireccionRepository direccionRepository;


	public Optional<Direccion> findById(Long id){ 
        return direccionRepository.findById(id);
    }

 
    public boolean isEmpty(Direccion direccion){
        boolean res= true;

        if(!direccion.getCalle().isBlank() || !direccion.getCiudad().isBlank() || !String.valueOf(direccion.getCodigoPostal()!=null? direccion.getCodigoPostal(): "").isBlank()
            ||  !direccion.getProvincia().isBlank()) {
                res=false;
            }
          
       
       return res;
    }

    public boolean direccionWithoutCalle(Direccion direccion){
        boolean res= false;

        if(direccion.getCalle().isBlank() &&  (!direccion.getNumero().isBlank() || !direccion.getPiso().isBlank())) {
                res=true;
            }
          
       
       return res;
    }

    @Transactional
    public Direccion save(Direccion direccion){ 
        return direccionRepository.save(direccion);
    }

    @Transactional
    public void deleteById(Long id){ 
        direccionRepository.deleteById(id);
    }
    
}
