package com.MyRealTrainer.service;

import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.model.Rol;
import com.MyRealTrainer.repository.UsuarioRepository;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService  {

    @Autowired
    private UsuarioRepository UsuarioRepository;

  

    @Override
    public UserDetails loadUserByUsername(String nameOrEmail) throws UsernameNotFoundException {
        Usuario user = UsuarioRepository.findByNombreOrEmail(nameOrEmail, nameOrEmail)
        .orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email:" + nameOrEmail));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
         user.getPassword(), mapRolesToAuthorities(user.getRoles()));
}

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Rol> roles){
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }
    
}
