package com.MyRealTrainer.Services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.MyRealTrainer.model.Role;
import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.repository.UsuarioRepository;
import com.MyRealTrainer.service.CustomUserDetailsService;
import com.MyRealTrainer.service.UsuarioService;


@SpringBootTest
class CustomUserServiceTest {


	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@MockBean
	private UsuarioService usuarioService;

	@MockBean
	private UsuarioRepository usuarioRepository;

	private Usuario usuario;

	

	@Test
	@DisplayName("Test loadByUsername")
	void testLoadByUsername() {

		// ARRANGE
		Usuario usuario = new Usuario(1l);
		usuario.setEmail("test@test.com");
		usuario.setNombre("Alberto");
		usuario.setPassword("TestingPassword");
		Role rol = new Role();
		rol.setId(1l);
		rol.setName("ROLE_ADMIN");
		usuario.setRoles(Set.of(rol));
		this.usuario=usuario;
		Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(this.usuario));
		
		// Act
		UserDetails userDetailsResponse = customUserDetailsService.loadUserByUsername(usuario.getEmail());

		// Assert
		Assertions.assertNotNull(userDetailsResponse,"The user-details response is null");
		Assertions.assertSame(userDetailsResponse.getUsername(),usuario.getEmail(), "The email obtained is different");
		Assertions.assertEquals(userDetailsResponse.getAuthorities().toString(),usuario.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()).toString(), "The authorities are different");
		Assertions.assertSame(userDetailsResponse.getPassword(),usuario.getPassword(), "The password is different");
		

	}



	
	
}
