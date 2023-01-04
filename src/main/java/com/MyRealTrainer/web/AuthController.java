package com.MyRealTrainer.web;

import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.service.UsuarioService;
import com.MyRealTrainer.service.UtilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    

    @Autowired
    private UsuarioService usuarioService;

    

    
    @Autowired
    private UtilService utilService;

   

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Usuario usuario, BindingResult binding){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<>();
        if(binding.hasErrors()){
            errores= utilService.getErrorMessages(binding, errores);
            response.put("errores", errores);
            return ResponseEntity.badRequest().body(response);
        }else{
             // checks if email exists in database
            if(usuarioService.existsByEmail(usuario.getEmail())){
                errores.add("Este email ya está registrado");
                response.put("errores", errores);
                return ResponseEntity.badRequest().body(response);
            }else{
                Usuario newUser = usuarioService.registerNewUser(usuario);
                return ResponseEntity.ok(newUser);
            }

        }
    }    
}
