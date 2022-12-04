package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.LugarEntrenamientoRepository;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.MyRealTrainer.model.Direccion;
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
    private DireccionService direccionService;


	public Optional<LugarEntrenamiento> findById(Long id){ 
        return lugarRepository.findById(id);
    }

 
    public Entrenador assignDefaultLugares(Entrenador entrenador){
        List<LugarEntrenamiento> lugaresList= new ArrayList<LugarEntrenamiento>();
        List<String> lugaresTitle= Arrays.asList(new String[]{"Mi gimnasio","Aire libre","Tu domicilio","Telemático"});
        for (String title : lugaresTitle) {
            LugarEntrenamiento lugar= new LugarEntrenamiento();
            switch(title){
                case "Mi gimnasio": 
                        lugar= new LugarEntrenamiento("Mi gimnasio", TipoLugar.MI_GIMNASIO, entrenador);
                        break;
                case "Aire libre":
                        lugar= new LugarEntrenamiento("Aire libre", TipoLugar.AIRE_LIBRE, entrenador);
                        break;
                case "Tu domicilio":
                        lugar= new LugarEntrenamiento("Tu domicilio", TipoLugar.TU_DOMICILIO, entrenador);
                        break;
                case "Telemático":
                        lugar= new LugarEntrenamiento("Telemático", TipoLugar.TELEMATICO, entrenador);
                        break;
            }
            lugar.setEntrenador(entrenador);
            lugaresList.add(this.save(lugar));
        }
        entrenador.setLugares(lugaresList);
        return entrenador;
    }
	
    public  Map<String,Object> createNewLugar(LugarEntrenamiento lugar, Usuario usuario){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<String>();
       
        if (usuario.getEntrenador()!=null){
            Entrenador entrenador= usuario.getEntrenador();
            lugar.setEntrenador(entrenador);
            if(lugar.getDireccion()!=null && direccionService.direccionWithoutCalle(lugar.getDireccion())){
                errores.add("No puedes crear una dirección con número o bloque sin incorporar una calle");
                response.put("errores", errores);
            }else if(lugar.getDireccion()!=null && !direccionService.isEmpty(lugar.getDireccion())){
                Direccion direccion= lugar.getDireccion();

                lugar.setDireccion(null);
                LugarEntrenamiento savedLugar= this.save(lugar);

                direccion.setLugar(savedLugar);
                direccionService.save(direccion);

                savedLugar.setDireccion(direccion);
                response.put("lugar", savedLugar);

            }else{
                lugar.setDireccion(null);
                response.put("lugar", this.save(lugar));
            }
            
        }else{
            errores.add("No puedes crear lugares de entrenamiento si no tienes creado un perfil de entrenador");
            response.put("errores", errores);
        }
        return response;
    }

    // Creates a map with the format: {id: LugarEntrenamiento, .... ,}
    public Map<Long,LugarEntrenamiento> createMapLugares(List<LugarEntrenamiento> lugarList){
        HashMap<Long,LugarEntrenamiento> res= new HashMap<Long,LugarEntrenamiento>();
        for (LugarEntrenamiento lugarEntrenamiento : lugarList) {
            res.put(lugarEntrenamiento.getId(), lugarEntrenamiento);
        }
        return res;
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
