package com.MyRealTrainer.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity getByEmail(@PathVariable String email) {
    Map<String,Object> response = new HashMap<>();
    List<String> errores = new ArrayList<String>();
    Optional<Usuario> usuario = usuarioService.findByEmail(email);
    if(!usuario.isPresent()){
        errores.add("Este usuario no existe");
        response.put("errores", errores);
	    return ResponseEntity.badRequest().body(response);

    }else if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || usuario.get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())){
        return ResponseEntity.ok(usuario);
    }else{
        errores.add("No tienes permiso para acceder");
        response.put("errores", errores);
        return ResponseEntity.badRequest().body(response);
    }
    }

       
}
