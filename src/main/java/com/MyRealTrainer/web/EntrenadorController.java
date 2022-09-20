package com.MyRealTrainer.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.service.EntrenadorService;
import com.MyRealTrainer.service.UsuarioService;
import com.MyRealTrainer.service.UtilService;

@RestController
@RequestMapping("/entrenadores")
public class EntrenadorController {
    
    @Autowired
    private EntrenadorService entrenadorService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UtilService utilService;
    
    @SuppressWarnings("rawtypes")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN')")
    @PostMapping("/{email}")
    public ResponseEntity createEntrenador(@PathVariable String email,  @Valid @RequestBody Entrenador newEntrenador, BindingResult binding) {
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
        
        Map<String,Object> responseService= entrenadorService.createNewEntrenador(newEntrenador,usuario.get());
        
        if (responseService.containsKey("errores")){
            errores.addAll((List<String>) responseService.get("errores"));
            response.put("errores",errores);
            return ResponseEntity.badRequest().body(response);

        }else{
            return ResponseEntity.ok(responseService.get("entrenador"));

        }
    }else{
        errores.add("No tienes permiso para acceder");
        response.put("errores", errores);
        return ResponseEntity.badRequest().body(response);
    }
    }

    

    
   
}
