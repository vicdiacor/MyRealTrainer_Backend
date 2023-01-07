package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.RutinaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.MyRealTrainer.model.Bloque;
import com.MyRealTrainer.model.Entrenamiento;
import com.MyRealTrainer.model.Rutina;
import com.MyRealTrainer.model.Serie;
import com.MyRealTrainer.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;

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

    @Autowired
    private EjercicioService ejercicioService;




  
    @Transactional
    public Rutina save(Rutina rutina){ 
        return rutinaRepository.save(rutina);
    }

    @Transactional
    public void deleteById(Long id){ 
        rutinaRepository.deleteById(id);
    }

    public Optional<Rutina> findById(Long id){ 
        return rutinaRepository.findById(id);
    }

    
    
  
    public Map<String,Set<Long>> getEntrenamientoBloqueSerieIds(Rutina rutina){
        Map<String,Set<Long>> res = new HashMap<String,Set<Long>>();

        // Get Ids of entrenamiento,bloques and series inside the old rutina
        Set<Long> entrenamientosId= new HashSet<Long>();
        Set<Long> bloquesId= new HashSet<Long>();
        Set<Long> seriesId= new HashSet<Long>();
        for (Entrenamiento entrenamiento : rutina.getEntrenamientos()) {
            entrenamientosId.add(entrenamiento.getId());
            for (Bloque bloque : entrenamiento.getBloques()){
                bloquesId.add(bloque.getId());
                for (Serie serie : bloque.getSeries()){
                    seriesId.add(serie.getId());
                }
            }
        }

        res.put("entrenamientosId", entrenamientosId);
        res.put("bloquesId", bloquesId);
        res.put("seriesId",seriesId);

        return res;
    } 

    public Map<String,Object> constructAndSaveNewRutina(Rutina rutina,Usuario usuario){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<String>();

        Map<String,Object> myEjerciciosIdMap = ejercicioService.getMyEjerciciosId(usuario);

        if(myEjerciciosIdMap.containsKey("myEjerciciosId")){
            Set<Long> myEjerciciosId = (Set<Long>) myEjerciciosIdMap.get("myEjerciciosId");
                
            if(rutina.getEntrenamientos().size()<1){
                errores.add("Debes asignar como mínimo un entrenamiento a la rutina");
                response.put("errores", errores);
                return response;
            }

            rutina.setId(null);
            rutina.setEntrenador(usuario.getEntrenador());
            rutina.setContrato(null);

            for (Entrenamiento entrenamiento : rutina.getEntrenamientos()){
                if(entrenamiento.getBloques().size()<1){
                    errores.add("Debes asignar como mínimo un un bloque de series al entrenamiento");
                    response.put("errores", errores);
                    return response;
                }
                entrenamiento.setId(null);
                entrenamiento.setRutina(rutina);
                for (Bloque bloque : entrenamiento.getBloques()){
                    if(myEjerciciosId.contains(bloque.getEjercicio().getId())){
                        if(bloque.getSeries().size()<1){
                            errores.add("Debes asignar como mínimo una serie a cada bloque de series");
                            response.put("errores", errores);
                            return response;
                        }
                        bloque.setId(null);
                        bloque.setEntrenamiento(entrenamiento);
                        for (Serie serie : bloque.getSeries()){
                            serie.setId(null);
                            serie.setBloque(bloque);
                        }
                         
                    }else{
                        errores.add("No puedes asignar a tu rutina ejercicios privados de otros entrenadores");
                        response.put("errores", errores);
                        return response;
                    }
                }
            }
            Rutina savedRutina = this.save(rutina);
            response.put("rutina", savedRutina);
            return response;

        }else{
           return myEjerciciosIdMap;
        }
       

    }

    public void deleteMissingEntrenamientosBloquesOrSeries(List<Entrenamiento> oldEntrenamientos, Set<Long> newEntrenamientoIds,Set<Long> newBloqueIds,Set<Long> newSerieIds){
        List <Entrenamiento> preservedEntrenamientos = new ArrayList<Entrenamiento>();

        // Delete missing entrenamientos in the new entrenamientos list
        for (Entrenamiento entrenamiento : oldEntrenamientos){
            if(!newEntrenamientoIds.contains(entrenamiento.getId())){
                try{
                    entrenamientoService.deleteById(entrenamiento.getId());
                }catch(Exception exception){ // Always returns an exception, but if we try it again, it works
                    entrenamientoService.deleteById(entrenamiento.getId());
                }
                
            }else{
                preservedEntrenamientos.add(entrenamiento);
            }

        }

        // Delete missing bloques from preservedEntrenamientos
        List <Bloque> preservedBloques = new ArrayList<Bloque>();
        for (Entrenamiento entrenamiento : preservedEntrenamientos){
            for (Bloque bloque : entrenamiento.getBloques()){
                if(!newBloqueIds.contains(bloque.getId())){
                    try{
                        bloqueService.deleteById(bloque.getId());
                    }catch(Exception exception){ // Always returns an exception, but if we try it again, it works
                        bloqueService.deleteById(bloque.getId());
                    }
                   
                }else{
                    preservedBloques.add(bloque);
                }
    
            }
        }

        // Delete missing Series from preserved bloques
        for (Bloque bloque : preservedBloques){
            for (Serie serie : bloque.getSeries()){
                if(!newSerieIds.contains(serie.getId())){
                    try{
                        serieService.deleteById(Long.valueOf(serie.getId()));
                    }catch(Exception exception){ // Always returns an exception, but if we try it again, it works
                        serieService.deleteById(Long.valueOf(serie.getId()));
                    }
                }
            }
        }

    }

    public Map<String,Object> constructAndSaveEditedRutina(Rutina rutina, Rutina databaseRutina,Usuario usuario){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<String>();

      

        Map<String,Set<Long>> oldEntrenamientoBloqueSerieIds = this.getEntrenamientoBloqueSerieIds(databaseRutina);
        Set<Long> oldEntrenamientosId= oldEntrenamientoBloqueSerieIds.get("entrenamientosId");
        Set<Long> oldBloquesId= oldEntrenamientoBloqueSerieIds.get("bloquesId");
        Set<Long> oldSeriesId= oldEntrenamientoBloqueSerieIds.get("seriesId");
        
        Set<Long> newEntrenamientoIds = new HashSet<Long>();
        Set<Long> newBloqueIds = new HashSet<Long>();
        Set<Long> newSerieIds = new HashSet<Long>();

        // Old rutina copy
        Rutina oldRutina = new Rutina();
        BeanUtils.copyProperties(databaseRutina, oldRutina);

        // Copy changes from editedRutina to database-rutina
        BeanUtils.copyProperties(rutina, databaseRutina,"id","entrenador","contrato");
        // Copy of updated rutina with the correct fields
        Rutina newRutina = new Rutina();
        newRutina.setId(databaseRutina.getId());
        newRutina.setEntrenamientos(null);
        


        Map<String,Object> myEjerciciosIdMap = ejercicioService.getMyEjerciciosId(usuario);
        
        if(myEjerciciosIdMap.containsKey("myEjerciciosId")){
            Set<Long> myEjerciciosId = (Set<Long>) myEjerciciosIdMap.get("myEjerciciosId");

            if(databaseRutina.getEntrenamientos().size()<1){
                errores.add("Debes asignar como mínimo un entrenamiento a la rutina");
                response.put("errores", errores);
                return response;
            }

            for (Entrenamiento entrenamiento : databaseRutina.getEntrenamientos()){

                
                if(entrenamiento.getBloques().size()<1){
                    errores.add("Debes asignar como mínimo un un bloque de series al entrenamiento");
                    response.put("errores", errores);
                    return response;
                }
                
                if(entrenamiento.getId()!=null && !oldEntrenamientosId.contains(entrenamiento.getId())){
                    errores.add("No puedes asignar a esta rutina entrenamientos de otras rutinas");
                    response.put("errores", errores);
                    return response;
                }
                if(entrenamiento.getId()!=null){
                    if(newEntrenamientoIds.contains(entrenamiento.getId())){
                        errores.add("No puedes asignar varios entrenamientos con el mismo identificador");
                        response.put("errores", errores);
                        return response;
                    }else{
                        newEntrenamientoIds.add(entrenamiento.getId());

                    }
                }

                entrenamiento.setRutina(newRutina);

                for (Bloque bloque : entrenamiento.getBloques()){
                    if(myEjerciciosId.contains(bloque.getEjercicio().getId())){
                        if(bloque.getId()!=null && !oldBloquesId.contains(bloque.getId())){
                            errores.add("No puedes asignar a los entrenamientos, bloques de entrenamientos de otras rutinas");
                            response.put("errores", errores);
                            return response;
                        }
                        
                        if(bloque.getSeries().size()<1){
                            errores.add("Debes asignar como mínimo una serie a cada bloque de series");
                            response.put("errores", errores);
                            return response;
                        }

                        if(bloque.getId()!=null){
                            if(newBloqueIds.contains(bloque.getId())){
                                errores.add("No puedes asignar varios bloques con el mismo identificador");
                                response.put("errores", errores);
                                return response;
                            }else{
                                newBloqueIds.add(bloque.getId());
                            }
                        }
                       
                        bloque.setEntrenamiento(entrenamiento);

                        for (Serie serie : bloque.getSeries()){
                            if(serie.getId()!=null && !oldSeriesId.contains(serie.getId())){
                                errores.add("No puedes asignar a los bloques, series pertenecientes a bloques de entrenamiento de otras rutinas");
                                response.put("errores", errores);
                                return response;
                            }
                            if(serie.getId()!=null){
                                if(newSerieIds.contains(serie.getId())){
                                    errores.add("No puedes asignar varios series con el mismo identificador");
                                    response.put("errores", errores);
                                    return response;
                                }else{
                                    newSerieIds.add(serie.getId());
                                }
                                
                            }
                           
                            serie.setBloque(bloque);
                        }
                         
                    }else{
                        errores.add("No puedes asignar a tu rutina ejercicios privados de otros entrenadores");
                        response.put("errores", errores);
                        return response;
                    }
                }
            }
    
            this.deleteMissingEntrenamientosBloquesOrSeries(oldRutina.getEntrenamientos(), newEntrenamientoIds, newBloqueIds, newSerieIds);
            try{
                Rutina savedRutina = this.save(databaseRutina);
                response.put("rutina", savedRutina);
                return response;
            }catch(Exception exception){ // Always returns an exception, but if we try it again, it works
                Rutina savedRutina = this.save(databaseRutina);
                response.put("rutina", savedRutina);
                return response;
            }
            

        }else{
           return myEjerciciosIdMap;
        }
       
    }

    public Map<String,Object> findMyRutinas(Usuario usuario){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<String>();
        if (usuario.getEntrenador()!=null){
            List<Rutina> myRutinas= rutinaRepository.findMyRutinas(usuario.getEntrenador().getId());
            response.put("rutinas", myRutinas);
        }else{
            errores.add("Los perfiles que no son de tipo entrenador no pueden tener rutinas");
            response.put("errores", errores);

        }
        return response;
    }

    public void prueba(){
      Serie serie = new Serie();
      serie.setId(Long.valueOf(11));   
        //bloqueService.deleteById(Long.valueOf(3));
        this.prueba2(serie);
    }

    public void prueba2(Serie serie){
        serieService.deleteById(serie.getId());
    }

    
}
