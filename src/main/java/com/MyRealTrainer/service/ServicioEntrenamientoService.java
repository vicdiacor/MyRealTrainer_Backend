package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.LugarEntrenamientoRepository;
import com.MyRealTrainer.repository.ServicioEntrenamientoRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.MyRealTrainer.model.Direccion;
import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.LugarEntrenamiento;
import com.MyRealTrainer.model.Servicio;
import com.MyRealTrainer.model.Tarifa;
import com.MyRealTrainer.model.TipoLugar;
import com.MyRealTrainer.model.Usuario;

import org.apache.bcel.Repository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioEntrenamientoService {

	@Autowired
    private ServicioEntrenamientoRepository servicioRepository;

    @Autowired
    private TarifaService tarifaService;



	public Optional<Servicio> findById(Long id){ 
        return servicioRepository.findById(id);
    }


    public  Map<String,Object> createNewServicio(Servicio servicio, Usuario usuario){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<String>();
       
        if (usuario.getEntrenador()!=null){
            Entrenador entrenador= usuario.getEntrenador();
            servicio.setEntrenador(entrenador);
            if(servicio.getTarifas().size()<1){
                errores.add("No puedes crear servicios de entrenamiento si no tienes creado un perfil de entrenador");
                response.put("errores", errores);
            }else{
                
                List<Tarifa> tarifaList= servicio.getTarifas();
                servicio.setTarifas(null);
                Servicio savedServicio= this.save(servicio);
                List<Tarifa> savedTarifas= new ArrayList<Tarifa>();
             for (int i=0;i<tarifaList.size();i++) {
                    
                    Map<String,Object> responseTarifa= tarifaService.createNewTarifa(savedServicio, tarifaList.get(i));
                    if(responseTarifa.containsKey("errores")){
                        return responseTarifa;
                    }else{
                        savedTarifas.add( (Tarifa) responseTarifa.get("tarifa"));
                    }
                }
                servicio.setTarifas(savedTarifas);
                response.put("servicio", savedServicio);

            }
            
            
        }else {
            errores.add("No puedes crear servicios de entrenamiento si no tienes creado un perfil de entrenador");
            response.put("errores", errores);
        }
        return response;
    }

    

    @Transactional
    public Servicio save(Servicio servicio){ 
        return servicioRepository.save(servicio);
    }

    @Transactional
    public void deleteById(Long id){ 
        servicioRepository.deleteById(id);
    }
    
}