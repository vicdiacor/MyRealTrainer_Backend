package com.MyRealTrainer.web;


import java.util.Collections;

import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.model.Rol;
import com.MyRealTrainer.model.TipoRol;
import com.MyRealTrainer.payload.LoginDto;
import com.MyRealTrainer.repository.UsuarioRepository;
import com.MyRealTrainer.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

   

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<>();

        // checks if email exists in database
        if(usuarioRepository.existsByEmail(usuario.getEmail())){
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
        user.setFechaNacimiento(usuario.getFechaNacimiento());
        user.setLocalidad(usuario.getLocalidad());

        // URL Foto perfil

        Optional<Rol> roles = rolRepository.findByTipoRol(TipoRol.CLIENTE);
        if(roles.isPresent()){
            user.setRoles(Collections.singleton(roles.get()));
        }else{
            Rol rolCliente= new Rol();
            rolCliente.setTipoRol(TipoRol.CLIENTE);
            rolRepository.save(rolCliente);
            user.setRoles(Collections.singleton(rolCliente));

        }
        
        usuarioRepository.save(user);
        return new ResponseEntity<>("Usuario registrado correctamente", HttpStatus.OK);

    }    
}
