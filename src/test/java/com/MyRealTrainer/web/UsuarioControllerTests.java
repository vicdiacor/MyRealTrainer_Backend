package com.MyRealTrainer.web;

import com.MyRealTrainer.SecurityConfig.WithMockCustomUser;
import com.MyRealTrainer.model.Role;
import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.service.CustomUserDetailsService;
import com.MyRealTrainer.service.RoleService;
import com.MyRealTrainer.service.UsuarioService;
import com.MyRealTrainer.service.UtilService;
import com.fasterxml.jackson.databind.ObjectMapper;



import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = UsuarioController.class)
public class UsuarioControllerTests {

	@MockBean
	UsuarioService usuarioService;
	
	@MockBean
    private AuthenticationManager authenticationManager;

	
    @MockBean
    private RoleService roleService;

	
	@MockBean
	CustomUserDetailsService customUserDetailsService;

	@MockBean
	DataSource dataSource;
    
	@Autowired
	MockMvc mockMvc;

	@Autowired
  	private ObjectMapper objectMapper;

	@SpyBean
	private UtilService utilService;



 	private Role role_cliente;

	private Usuario usuario1;
	
	@BeforeAll
	void init() {
		
		

		role_cliente= new Role();
		role_cliente.setName("ROLE_CLIENTE");
		role_cliente.setId(2l);

		// Usuario 1 (Cliente)
		usuario1 = new Usuario(1l);
		usuario1.setNombre("Usuario1");
		usuario1.setEmail("usuario1@email.com");
		usuario1.setApellidos("Apellido");
		usuario1.setFechaNacimiento(Date.valueOf("2000-04-02"));
		usuario1.setLocalidad("Sevilla");
		usuario1.setPassword("No-Encoded-Password");
		usuario1.setRoles(Set.of(role_cliente));

	}

	
	@DisplayName("Getting information about my profile as a cliente")
	@WithMockCustomUser(username = "usuario1@email.com", roles = {"ROLE_CLIENTE"})
    @Test
	void testGetMyProfile() throws Exception {
		// Arrange
				
		when(usuarioService.findByEmail(usuario1.getEmail())).thenReturn(Optional.of(usuario1));

		// Test and Assert
		mockMvc.perform(get("/usuarios/email/"+usuario1.getEmail()))
		.andExpect(status().isOk());
	}

	@DisplayName("Failure trying to Getting information about another profile as a cliente")
	@WithMockCustomUser(username = "differentUser@email.com", roles = {"ROLE_CLIENTE"})
    @Test
	void testGetAnotherProfile() throws Exception {
		// Arrange
				
		when(usuarioService.findByEmail(usuario1.getEmail())).thenReturn(Optional.of(usuario1));

		// Test and Assert
		mockMvc.perform(get("/usuarios/email/"+usuario1.getEmail()))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("No tienes permiso para acceder"));
	}

	@DisplayName("Getting information about another profile as a ADMIN")
	@WithMockCustomUser(username = "admin@email.com", roles = {"ROLE_ADMIN"})
    @Test
	void testGetAnotherProfileAdmin() throws Exception {
		// Arrange
				
		when(usuarioService.findByEmail(usuario1.getEmail())).thenReturn(Optional.of(usuario1));

		// Test and Assert
		mockMvc.perform(get("/usuarios/email/"+usuario1.getEmail()))
		.andExpect(status().isOk());
	}

	@DisplayName("Getting information about an nonexistent profile")
	@WithMockCustomUser(username = "admin@email.com", roles = {"ROLE_ADMIN"})
    @Test
	void testGetNonexistentProfile() throws Exception {
		// Arrange
				
		when(usuarioService.findByEmail(usuario1.getEmail())).thenReturn(Optional.empty());

		// Test and Assert
		mockMvc.perform(get("/usuarios/email/"+usuario1.getEmail()))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("Este usuario no existe"));	}
	
}