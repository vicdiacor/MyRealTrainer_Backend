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

import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.LugarEntrenamiento;
import com.MyRealTrainer.model.Servicio;
import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.service.EntrenadorService;
import com.MyRealTrainer.service.LugarEntrenamientoService;
import com.MyRealTrainer.service.ServicioEntrenamientoService;
import com.MyRealTrainer.service.UsuarioService;
import com.MyRealTrainer.service.UtilService;

@RestController
@RequestMapping("/servicios")
public class ServicioEntrenamientoController {
    
    @Autowired
    private ServicioEntrenamientoService servicioEntrenamientoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UtilService utilService;
    
    @SuppressWarnings("rawtypes")
    @PreAuthorize("hasAnyRole('ROLE_ENTRENADOR','ROLE_ADMIN','ROLE_CLIENTE')")
    @PostMapping("/{email}")
    public ResponseEntity createServicio(@PathVariable String email,  @Valid @RequestBody Servicio newServicio, BindingResult binding) {
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
        Map<String,Object> responseService= servicioEntrenamientoService.constructAndSave(newServicio, usuario.get());
        
        if (responseService.containsKey("errores")){
            errores.addAll((List<String>) responseService.get("errores"));
            response.put("errores",errores);
            return ResponseEntity.badRequest().body(response);

        }else{
            return ResponseEntity.ok(responseService.get("servicio"));
        }

        
    }else{
        errores.add("No tienes permiso para crear un servicio para este usuario");
        response.put("errores", errores);
        return ResponseEntity.badRequest().body(response);
    }
    }

    
    @SuppressWarnings("rawtypes")
    @PreAuthorize("hasAnyRole('ROLE_ENTRENADOR','ROLE_ADMIN','ROLE_CLIENTE')")
    @GetMapping("/{email}")
    public ResponseEntity findMyServicios(@PathVariable String email) {
    Map<String,Object> response = new HashMap<>();
    List<String> errores = new ArrayList<String>();
    Optional<Usuario> usuario = usuarioService.findByEmail(email);
    if(!usuario.isPresent()){
        errores.add("Este usuario no existe");
        response.put("errores", errores);
	    return ResponseEntity.badRequest().body(response);

    }else if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || usuario.get().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())){
        Map<String,Object> responseService= servicioEntrenamientoService.findMyServicios(usuario.get());
        if (responseService.containsKey("errores")){
            errores.addAll((List<String>) responseService.get("errores"));
            response.put("errores",errores);
            return ResponseEntity.badRequest().body(response);

        }else{
            return ResponseEntity.ok(responseService.get("servicios"));
        }

        
    }else{
        errores.add("No tienes permiso para crear un servicio para este usuario");
        response.put("errores", errores);
        return ResponseEntity.badRequest().body(response);
    }
    }

    @SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTRENADOR','ROLE_CLIENTE')")
    @PutMapping("/{id}")
    public ResponseEntity updateServicio(@PathVariable Long id, @Valid @RequestBody Servicio editedService, BindingResult binding) {
    Map<String,Object> response = new HashMap<>();
    List<String> errores = new ArrayList<String>();
    Optional<Servicio> oldService = servicioEntrenamientoService.findById(id);
    if(!oldService.isPresent()){
        errores.add("Este servicio no existe");
        response.put("errores", errores);
	    return ResponseEntity.badRequest().body(response);

    }else if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || oldService.get().getEntrenador().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())){
        boolean editingMyOwnTarifas= servicioEntrenamientoService.editingMyOwnTarifas(oldService.get(), editedService);
        if(editingMyOwnTarifas){
            servicioEntrenamientoService.deleteMissingTarifas(editedService,oldService.get());
            BeanUtils.copyProperties(editedService, oldService.get(),"id","entrenador");
            Map<String,Object> responseService= servicioEntrenamientoService.constructAndSave(oldService.get(), oldService.get().getEntrenador().getUsuario());
            
            if (responseService.containsKey("errores")){
                errores.addAll((List<String>) responseService.get("errores"));
                response.put("errores",errores);
                return ResponseEntity.badRequest().body(response);
    
            }else{
                return ResponseEntity.ok(responseService.get("servicio"));
            }
        }else{
            errores.add("Estás intentando modificar o asociar a tus servicios tarifas de otros usuarios");
            response.put("errores", errores);
            return ResponseEntity.badRequest().body(response);
        }
        
    }else{
        errores.add("No tienes permiso para editar un servicio para este usuario");
        response.put("errores", errores);
        return ResponseEntity.badRequest().body(response);
    }
  
}

@SuppressWarnings("rawtypes")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTRENADOR','ROLE_CLIENTE')")
@DeleteMapping("/{id}")
 public ResponseEntity deletePlaza(@PathVariable Long id) {
    Map<String,Object> response = new HashMap<>();
    List<String> errores = new ArrayList<String>();
    Optional<Servicio> oldService = servicioEntrenamientoService.findById(id);
    if(!oldService.isPresent()){
        errores.add("Este servicio no existe");
        response.put("errores", errores);
	    return ResponseEntity.badRequest().body(response);

    }else if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || oldService.get().getEntrenador().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())){
        servicioEntrenamientoService.deleteById(oldService.get().getId());
        return ResponseEntity.ok("Servicio eliminando con éxito");
       
    }else{
        errores.add("No tienes permiso para eliminar un servicio de otro usuario");
        response.put("errores", errores);
        return ResponseEntity.badRequest().body(response);
    }
 }
   
}
