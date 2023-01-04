package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.RutinaRepository;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import com.MyRealTrainer.model.Bloque;
import com.MyRealTrainer.model.Entrenamiento;
import com.MyRealTrainer.model.Rutina;
import com.MyRealTrainer.model.Serie;
import com.MyRealTrainer.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RutinaService {

	@Autowired
    private RutinaRepository rutinaRepository;

    @Autowired
    private EntrenamientoService entrenamientoService;

    @Autowired
    private BloqueService bloqueService;

    
    @Autowired
    private SerieService serieService;


  
    @Transactional
    public Rutina save(Rutina rutina){ 
        return rutinaRepository.save(rutina);
    }

    @Transactional
    public void deleteById(Long id){ 
        rutinaRepository.deleteById(id);
    }

  
    
    
    public Rutina setAllIdToNullExceptEjercicios(Rutina rutina){
            rutina.setId(null);
            
            rutina.getEntrenamientos().stream().forEach(entrenamiento -> {
                entrenamiento.setId(null);
                entrenamiento.getBloques().stream().forEach(bloque -> {
                    bloque.setId(null);
                    bloque.getSeries().stream().forEach(serie -> serie.setId(null));
                });

            });
            return rutina;        
    }

    public Map<String,Object> createNewRutina(Rutina rutina,Usuario usuario){

        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<String>();
       
        
        if(rutina.getEntrenamientos().size()<1){
            errores.add("Debes asignar como mínimo un entrenamiento a la rutina");
            response.put("errores", errores);
        }else{
            boolean entrenamientosWithoutBloquesOrSeries = rutina.getEntrenamientos().stream()
            .anyMatch(entrenamiento -> entrenamiento.getBloques().size() < 1 ||
                        entrenamiento.getBloques().stream().anyMatch(bloque -> bloque.getSeries().size() < 1));
            if(entrenamientosWithoutBloquesOrSeries){
                errores.add("Debes asignar como mínimo un bloque con al menos una serie a cada entrenamiento");
                response.put("errores", errores);
            }else{
                // Save Rutina
                rutina.setEntrenador(usuario.getEntrenador());
                List<Entrenamiento> entrenamientosList= rutina.getEntrenamientos();
                rutina.setEntrenamientos(null);
                rutina = this.save(rutina);
                List<Entrenamiento> savedEntrenamientos = new ArrayList<Entrenamiento>();

                // Save entrenamientos, bloques & series
                for (Entrenamiento entrenamiento : entrenamientosList) {
                    entrenamiento.setRutina(rutina);
                    entrenamiento.setBloques(null);
                    entrenamiento = entrenamientoService.save(entrenamiento);
                    List<Bloque> savedBloques = new ArrayList<Bloque>();
                    for (Bloque bloque : entrenamiento.getBloques()){
                        bloque.setEntrenamiento(entrenamiento);
                        bloque.setSeries(null);
                        bloque = bloqueService.save(bloque);
                        List<Serie> savedSeries = new ArrayList<Serie>();
                        for (Serie serie : bloque.getSeries()){
                            serie.setBloque(bloque);
                            savedSeries.add(serieService.save(serie));
                            }
                        bloque.setSeries(savedSeries);
                        savedBloques.add(bloque);
                        }
                    entrenamiento.setBloques(savedBloques);
                    savedEntrenamientos.add(entrenamiento);            
                    }

            rutina.setEntrenamientos(savedEntrenamientos);
            response.put("rutina", rutina);
            }
            
        }

        return response;
    }
    
}
