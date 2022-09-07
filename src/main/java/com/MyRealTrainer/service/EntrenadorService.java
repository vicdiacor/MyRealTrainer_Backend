package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.EntrenadorRepository;
import com.MyRealTrainer.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.LugarEntrenamiento;
import com.MyRealTrainer.model.Usuario;

import org.apache.bcel.Repository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntrenadorService {

	@Autowired
    private EntrenadorRepository entrenadorRepository;
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LugarEntrenamientoService lugarService;
    
    


	public Optional<Entrenador> findById(Long id){ 
        return entrenadorRepository.findById(id);
    }



    public Entrenador updateEntrenador(Entrenador updatedEntrenador, Entrenador currenEntrenador){
        BeanUtils.copyProperties(updatedEntrenador, currenEntrenador, "id");
        this.save(currenEntrenador);
        return currenEntrenador;
        
    }

    
    public  Map<String,Object>  createNewEntrenador(Entrenador entrenador, Usuario usuario){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<String>();
        if (usuario.getEntrenador()!=null){
            errores.add("No puedes crear un perfil de entrenador si ya tenías uno previamente");
            response.put("errores", errores);
        }else{
            entrenador.setUsuario(usuario);
            Entrenador savedEntrenador= this.save(entrenador);
            savedEntrenador=lugarService.assignDefaultLugares(savedEntrenador);
            response.put("entrenador", savedEntrenador);
        }
        return response;
    } 

    @Transactional
    public Entrenador save(Entrenador entrenador){ 
        return entrenadorRepository.save(entrenador);
    }

    @Transactional
    public void deleteById(Long id){ 
        entrenadorRepository.deleteById(id);
    }
    
}
