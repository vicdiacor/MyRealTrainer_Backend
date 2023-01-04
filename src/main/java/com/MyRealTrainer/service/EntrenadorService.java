package com.MyRealTrainer.service;

import com.MyRealTrainer.repository.EntrenadorRepository;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.MyRealTrainer.model.Entrenador;

import com.MyRealTrainer.model.Role;
import com.MyRealTrainer.model.Usuario;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntrenadorService {

	@Autowired
    private EntrenadorRepository entrenadorRepository;
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LugarEntrenamientoService lugarService;

    @Autowired
    private RoleService roleService;
    
    


	public Optional<Entrenador> findById(Long id){ 
        return entrenadorRepository.findById(id);
    }



    public Entrenador updateEntrenador(Entrenador updatedEntrenador, Entrenador currenEntrenador){
        BeanUtils.copyProperties(updatedEntrenador, currenEntrenador, "id");
        this.save(currenEntrenador);
        return currenEntrenador;
        
    }

    
    public  Map<String,Object>  createNewEntrenador(Entrenador entrenador, Usuario usuario){
        Map<String,Object> response = new HashMap<>();
        List<String> errores = new ArrayList<String>();
        if (usuario.getEntrenador()!=null){
            errores.add("No puedes crear un perfil de entrenador si ya tenías uno previamente");
            response.put("errores", errores);
        }else{
            Optional<Role> roles = roleService.findByName("ROLE_ENTRENADOR");
            if(roles.isPresent()){
                Set<Role> rolesUsuario = usuario.getRoles();
                rolesUsuario.add(roles.get());
                usuario.setRoles(rolesUsuario);
            }else{
                Role rolEntrenador= new Role();
                rolEntrenador.setName("ROLE_ENTRENADOR");
                roleService.save(rolEntrenador);
                Set<Role> rolesUsuario = usuario.getRoles();
                rolesUsuario.add(rolEntrenador);
                usuario.setRoles(rolesUsuario);
    
            }
            entrenador.setUsuario(usuario);
            Entrenador savedEntrenador= this.save(entrenador);
            savedEntrenador=lugarService.assignDefaultLugares(savedEntrenador);
            response.put("entrenador", savedEntrenador);
        }
        return response;
    } 

    @Transactional
    public Entrenador save(Entrenador entrenador){ 
        return entrenadorRepository.save(entrenador);
    }

    @Transactional
    public void deleteById(Long id){ 
        entrenadorRepository.deleteById(id);
    }
    
}
