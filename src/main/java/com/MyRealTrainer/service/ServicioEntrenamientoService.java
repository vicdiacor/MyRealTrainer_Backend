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

    public Map<String,Object> findMyServicios(Usuario usuario){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<String>();
        if (usuario.getEntrenador()!=null){
            List<Servicio> myServicios= servicioRepository.findMyServicios(usuario.getEntrenador().getId());
            response.put("servicios", myServicios);
        }else{
            errores.add("Los perfiles que no son de tipo entrenador no pueden tener servicios");
            response.put("errores", errores);

        }
        return response;
    }


    public  Map<String,Object> constructAndSave(Servicio servicio, Usuario usuario){
        boolean editMode= servicio.getId()!=null;
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<String>();
        if(!editMode){
            servicio.setEntrenador(usuario.getEntrenador());
        }
            if(servicio.getTarifas().size()<1){
                errores.add("Debes asignar al menos una tarifa");
                response.put("errores", errores);
            }else{
                
                List<Tarifa> tarifaList= servicio.getTarifas();
                servicio.setTarifas(null);
                Servicio savedServicio= this.save(servicio);
                List<Tarifa> savedTarifas= new ArrayList<Tarifa>();
             for (int i=0;i<tarifaList.size();i++) {
                    
                    Map<String,Object> responseTarifa= tarifaService.constructAndSave(savedServicio, tarifaList.get(i));
                    if(responseTarifa.containsKey("errores")){
                        return responseTarifa;
                    }else{
                        savedTarifas.add( (Tarifa) responseTarifa.get("tarifa"));
                    }
                }
                servicio.setTarifas(savedTarifas);
                response.put("servicio", savedServicio);

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
