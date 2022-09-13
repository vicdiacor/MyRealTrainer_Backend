package com.MyRealTrainer.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyRealTrainer.model.Ejercicio;
import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.LugarEntrenamiento;
import com.MyRealTrainer.model.Servicio;
import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.service.EjercicioService;
import com.MyRealTrainer.service.EntrenadorService;
import com.MyRealTrainer.service.LugarEntrenamientoService;
import com.MyRealTrainer.service.ServicioEntrenamientoService;
import com.MyRealTrainer.service.UsuarioService;
import com.MyRealTrainer.service.UtilService;

@RestController
@RequestMapping("/ejercicios")
public class EjercicioController {
    
    @Autowired
    private EjercicioService ejercicioService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UtilService utilService;
    
    @SuppressWarnings("rawtypes")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN')")
    @PostMapping("/{email}")
    public ResponseEntity createEjercicio(@PathVariable String email,  @Valid @RequestBody Ejercicio ejercicio, BindingResult binding) {
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
        Map<String,Object> responseService= ejercicioService.createEjercicio(ejercicio, usuario.get().getEntrenador());
        
        if (responseService.containsKey("errores")){
            errores.addAll((List<String>) responseService.get("errores"));
            response.put("errores",errores);
            return ResponseEntity.badRequest().body(response);

        }else{
            return ResponseEntity.ok(responseService.get("ejercicio"));
        }

        
    }else{
        errores.add("No tienes permiso para crear un ejercicio para este usuario");
        response.put("errores", errores);
        return ResponseEntity.badRequest().body(response);
    }
    }

    @SuppressWarnings("rawtypes")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN')")
    @GetMapping("/{email}")
    public ResponseEntity getEjerciciosByEmail(@PathVariable String email) {
    Map<String,Object> response = new HashMap<>();
    List<String> errores = new ArrayList<String>();
   
    Optional<Usuario> usuario = usuarioService.findByEmail(email);
    if(!usuario.isPresent()){
        errores.add("Este usuario no existe");
        response.put("errores", errores);
	    return ResponseEntity.badRequest().body(response);

    }else if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || usuario.get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())){
        Map<String,Object> responseService= ejercicioService.findByUsuario(usuario.get());
        
        if (responseService.containsKey("errores")){
            errores.addAll((List<String>) responseService.get("errores"));
            response.put("errores",errores);
            return ResponseEntity.badRequest().body(response);

        }else{
            return ResponseEntity.ok(responseService.get("ejercicios"));
        }
        

        
    }else{
        errores.add("No tienes permiso para acceder a los ejercicios de este usuario");
        response.put("errores", errores);
        return ResponseEntity.badRequest().body(response);
    }
    }
   
}
