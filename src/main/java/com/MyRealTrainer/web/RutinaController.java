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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        
           
                Map<String,Object> responseService= rutinaService.constructAndSaveNewRutina(newRutina, usuario.get());
            
                if (responseService.containsKey("errores")){
                    errores.addAll((List<String>) responseService.get("errores"));
                    response.put("errores",errores);
                    return ResponseEntity.badRequest().body(response);
        
                }else{
                    return ResponseEntity.ok(responseService.get("rutina"));
                }
           
        
    }else{
        errores.add("No tienes permiso para crear una rutina para este usuario");
        response.put("errores", errores);
        return ResponseEntity.badRequest().body(response);
        }
    }

    @SuppressWarnings("rawtypes")
    @PreAuthorize("hasAnyRole('ROLE_ENTRENADOR','ROLE_ADMIN')")
    @GetMapping("/{email}")
    public ResponseEntity findMyRutinas(@PathVariable String email) {
    Map<String,Object> response = new HashMap<>();
    List<String> errores = new ArrayList<String>();
    Optional<Usuario> usuario = usuarioService.findByEmail(email);
    if(!usuario.isPresent()){
        errores.add("Este usuario no existe");
        response.put("errores", errores);
	    return ResponseEntity.badRequest().body(response);

    }else {
        Map<String,Object> responseService= rutinaService.findMyRutinas(usuario.get());
        if (responseService.containsKey("errores")){
            errores.addAll((List<String>) responseService.get("errores"));
            response.put("errores",errores);
            return ResponseEntity.badRequest().body(response);

        }else{
            return ResponseEntity.ok(responseService.get("rutinas"));
        }

        
        }
    }

    
    @SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTRENADOR')")
    @PutMapping("/{id}")
    public ResponseEntity updateRutina(@PathVariable Long id,  @Valid @RequestBody Rutina editedRutina, BindingResult binding) {
    Map<String,Object> response = new HashMap<>();
    List<String> errores = new ArrayList<String>();
    if(binding.hasErrors()){
        errores= utilService.getErrorMessages(binding, errores);
        response.put("errores", errores);
        return ResponseEntity.badRequest().body(response);
    }
    Optional<Rutina> oldRutina = rutinaService.findById(id);
    if(!oldRutina.isPresent()){
        errores.add("La rutina que intentas editar no existe");
        response.put("errores", errores);
	    return ResponseEntity.badRequest().body(response);

    }else if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || oldRutina.get().getEntrenador().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())){
             
            Map<String,Object> responseService= rutinaService.constructAndSaveEditedRutina(editedRutina,oldRutina.get(),oldRutina.get().getEntrenador().getUsuario());

            if (responseService.containsKey("errores")){
                errores.addAll((List<String>) responseService.get("errores"));
                response.put("errores",errores);
                return ResponseEntity.badRequest().body(response);
            
            }else{
                return ResponseEntity.ok(responseService.get("rutina"));
            }
                
    }else{
        errores.add("No tienes permiso para editar la rutina de este usuario");
        response.put("errores", errores);
        return ResponseEntity.badRequest().body(response);
        }
    }

     
    @SuppressWarnings("rawtypes")
    @PreAuthorize("hasAnyRole('ROLE_ENTRENADOR','ROLE_ADMIN')")
    @PutMapping("/prueba")
    public ResponseEntity prueba() {
        rutinaService.prueba();
        return ResponseEntity.ok("Perfe");

    }
    
   
}
