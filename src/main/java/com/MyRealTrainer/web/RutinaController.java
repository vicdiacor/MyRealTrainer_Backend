package com.MyRealTrainer.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyRealTrainer.model.Rutina;
import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.service.EjercicioService;
import com.MyRealTrainer.service.RutinaService;
import com.MyRealTrainer.service.UsuarioService;
import com.MyRealTrainer.service.UtilService;

@RestController
@RequestMapping("/rutinas")
public class RutinaController {
    
    @Autowired
    private RutinaService rutinaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UtilService utilService;
    
    @Autowired
    private EjercicioService ejercicioService;
    
    @SuppressWarnings("rawtypes")
    @PreAuthorize("hasAnyRole('ROLE_ENTRENADOR','ROLE_ADMIN')")
    @PostMapping("/{email}")
    public ResponseEntity createRutina(@PathVariable String email,  @Valid @RequestBody Rutina newRutina, BindingResult binding) {
    Map<String,Object> response = new HashMap<>();
    List<String> errores = new ArrayList<String>();
    if(binding.hasErrors()){
        errores= utilService.getErrorMessages(binding, errores);
        response.put("errores", errores);
        return ResponseEntity.badRequest().body(response);
    }
    Optional<Usuario> usuario = usuarioService.findByEmail(email);
   
    if(!usuario.isPresent()){
        errores.add("Este usuario no existe");
        response.put("errores", errores);
	    return ResponseEntity.badRequest().body(response);
        
    }else if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || usuario.get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())){
        
            if(ejercicioService.usingPublicOrMyEjercicios(newRutina, usuario.get())){
                newRutina = rutinaService.setAllIdToNullExceptEjercicios(newRutina);
                Map<String,Object> responseService= rutinaService.createNewRutina(newRutina, usuario.get());
            
                if (responseService.containsKey("errores")){
                    errores.addAll((List<String>) responseService.get("errores"));
                    response.put("errores",errores);
                    return ResponseEntity.badRequest().body(response);
        
                }else{
                    return ResponseEntity.ok(responseService.get("rutina"));
                }
        
            }else{
                errores.add("No puedes crear rutinas asociadas a ejercicios privados de otros entrenadores");
                response.put("errores", errores);
                return ResponseEntity.badRequest().body(response);
            }
           
        
    }else{
        errores.add("No tienes permiso para crear una rutina para este usuario");
        response.put("errores", errores);
        return ResponseEntity.badRequest().body(response);
    }
    }
    
   
}
