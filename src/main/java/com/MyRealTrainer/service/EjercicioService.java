package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.EjercicioRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.MyRealTrainer.model.Ejercicio;
import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.Rutina;
import com.MyRealTrainer.model.Usuario;

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

    public  Map<String,Object>  findByUsuario(Usuario usuario){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<String>();

        if(usuario.getEntrenador()!=null){
            List <Ejercicio> ejercicios= this.ejercicioRepository.findByEntrenadorId(usuario.getEntrenador().getId());
            response.put("ejercicios", ejercicios);
        }else{
            errores.add("Este usuario no tiene creado un perfil de entrenador");
            response.put("errores",errores);
        }
        
        return response;
    }

    public Map<String,Object> getMyEjerciciosId(Usuario usuario){
        Map<String,Object> response =   this.findByUsuario(usuario);

        if(response.containsKey("ejercicios")){
            List<Ejercicio> myEjercicios = (List<Ejercicio>) response.get("ejercicios");
            Set<Long> myEjerciciosId = myEjercicios.stream().map(ejercicio -> ejercicio.getId()).collect(Collectors.toSet());
            response.put("myEjerciciosId", myEjerciciosId);
        }
        return response;

    }

  
    
}
