package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.EntrenadorRepository;
import com.MyRealTrainer.repository.TarifaRepository;
import com.MyRealTrainer.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.LugarEntrenamiento;
import com.MyRealTrainer.model.Servicio;
import com.MyRealTrainer.model.Tarifa;
import com.MyRealTrainer.model.Usuario;

import org.apache.bcel.Repository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TarifaService {

	@Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private LugarEntrenamientoService lugarService;
    
    


	public Optional<Tarifa> findById(Long id){ 
        return tarifaRepository.findById(id);
    }





    public  Map<String,Object> createNewTarifa(Servicio servicio, Tarifa tarifa){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<String>();
            List<LugarEntrenamiento> lugares= new ArrayList<LugarEntrenamiento>();
            for(LugarEntrenamiento lugar : tarifa.getLugares()){
                Optional<LugarEntrenamiento> storedLugar= lugarService.findById(lugar.getId());
                if(storedLugar.isPresent()){
                    lugares.add(storedLugar.get());
                }else{
                    errores.add("Se está intentando asociar una tarifa a un lugar que no existe, cuyo id es: " + String.valueOf(lugar.getId()));
                    response.put("errores", errores);
                    return response;
                }

            }
            tarifa.setLugares(lugares);
            tarifa.setServicio(servicio);
            Tarifa savedTarifa= this.save(tarifa);
            response.put("tarifa", savedTarifa);
            return response;
    } 

    @Transactional
    public Tarifa save(Tarifa tarifa){ 
        return tarifaRepository.save(tarifa);
    }

    @Transactional
    public void deleteById(Long id){ 
        tarifaRepository.deleteById(id);
    }
    
}
