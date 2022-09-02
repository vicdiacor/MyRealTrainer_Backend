package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.LugarEntrenamientoRepository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.LugarEntrenamiento;
import com.MyRealTrainer.model.TipoLugar;
import com.MyRealTrainer.model.Usuario;

import org.apache.bcel.Repository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LugarEntrenamientoService {

	@Autowired
    private LugarEntrenamientoRepository lugarRepository;

    @Autowired
    private EntrenadorService entrenadorService;


	public Optional<LugarEntrenamiento> findById(Long id){ 
        return lugarRepository.findById(id);
    }

    
	
    public  Map<String,Object> createNewLugar(LugarEntrenamiento lugar, Usuario usuario){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<String>();
        if (usuario.getEntrenador()!=null){
            Entrenador entrenador= usuario.getEntrenador(); //Entrenador with user.entrenador=null
            entrenador.setUsuario(usuario); //Entrenador with full user
            lugar.setEntrenador(entrenador);
            response.put("lugares", this.save(lugar));
            
        }else{
            errores.add("No puedes crear lugares de entrenamiento si no tienes creado un perfil de entrenador");
            response.put("errores", errores);
        }
        return response;
    }
   


    @Transactional
    public LugarEntrenamiento save(LugarEntrenamiento lugar){ 
        return lugarRepository.save(lugar);
    }

    @Transactional
    public void deleteById(Long id){ 
        lugarRepository.deleteById(id);
    }
    
}