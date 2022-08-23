package com.MyRealTrainer.web;


import java.util.Collections;
import java.util.Date;

import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.model.Role;
import com.MyRealTrainer.service.RoleService;
import com.MyRealTrainer.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

   

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<>();

        // checks if email exists in database
        if(usuarioService.existsByEmail(usuario.getEmail())){
            errores.add("Este email ya está registrado");
            response.put("errores", errores);
            return ResponseEntity.badRequest().body(response);
        }

        // create user object
        Usuario user = new Usuario();
        user.setNombre(usuario.getNombre());
        user.setEmail(usuario.getEmail());
        user.setPassword(passwordEncoder.encode(usuario.getPassword()));
        user.setApellidos(usuario.getApellidos());
        Date fechaNacimiento = usuario.getFechaNacimiento();
        fechaNacimiento.setHours(0);
        fechaNacimiento.setMinutes(0);
        fechaNacimiento.setSeconds(0);
        user.setFechaNacimiento(fechaNacimiento);
        user.setLocalidad(usuario.getLocalidad());
       
        // add URL Foto perfil

        // add Tipo de rol

        Optional<Role> roles = roleService.findByName("ROLE_CLIENTE");
        if(roles.isPresent()){
            user.setRoles(Collections.singleton(roles.get()));
        }else{
            Role rolCliente= new Role();
            rolCliente.setName("ROLE_CLIENTE");
            roleService.save(rolCliente);
            user.setRoles(Collections.singleton(rolCliente));

        }
        
        usuarioService.save(user);
        return new ResponseEntity<>("Usuario registrado correctamente", HttpStatus.OK);

    }    
}
