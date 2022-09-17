package com.MyRealTrainer.web;

import com.MyRealTrainer.model.Role;
import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.service.CustomUserDetailsService;
import com.MyRealTrainer.service.RoleService;
import com.MyRealTrainer.service.UsuarioService;
import com.MyRealTrainer.service.UtilService;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTests {

	@MockBean
	UsuarioService usuarioService;

	@MockBean
    private AuthenticationManager authenticationManager;

	@MockBean
	DataSource dataSource;

    @MockBean
    private RoleService roleService;

   
	@MockBean
    org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder;
    

	@MockBean
	CustomUserDetailsService customUserDetailsService;

	
	@Autowired
	MockMvc mockMvc;

	@Autowired
  	private ObjectMapper objectMapper;

	@SpyBean
	private UtilService utilService;

	Usuario usuario1;
	Usuario usuario2;
	
	Role role_cliente;
	Role role_admin;

	@BeforeAll
	void init() {
		
		role_admin= new Role();
		role_admin.setName("ROLE_ADMIN");
		role_admin.setId(1l);

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
		

		// Usuario 2 (Admin)
		usuario2 = new Usuario(2l);
		usuario2.setNombre("Admin");
		usuario2.setEmail("admin@email.com");
		usuario2.setRoles(Set.of(role_admin));
		
		when(usuarioService.save(usuario1)).thenReturn(usuario1);
		when(usuarioService.save(usuario1)).thenReturn(usuario1);
		when(passwordEncoder.encode(anyString())).thenReturn("Encoded_Password");
		

	}

	

	@DisplayName("Register Success with Role_Cliente present in the database")
    @Test
	void testRegisterSuccess() throws Exception {
		// Arrange
		when(usuarioService.existsByEmail(usuario1.getEmail())).thenReturn(false);
		when(roleService.findByName("ROLE_CLIENTE")).thenReturn(Optional.of(role_cliente));

		// Test and Assert
		mockMvc.perform(post("/api/auth/signup/").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(usuario1)))
		.andExpect(status().isOk());
	}

	
	@DisplayName("Register Success with NO Role_Cliente present in the database")
    @Test
	void testRegisterSuccessWithoutRoles() throws Exception {
		// Arrange
		when(usuarioService.existsByEmail(usuario1.getEmail())).thenReturn(false);
		when(roleService.findByName("ROLE_CLIENTE")).thenReturn(Optional.empty());

		// Test and Assert
		mockMvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(usuario1)))
		.andExpect(status().isOk());
	}

	
	@DisplayName("Register failure due to duplicate username")
    @Test
	void testRegisterDuplicateUser() throws Exception {

		// Arrange
		when(usuarioService.existsByEmail(usuario1.getEmail())).thenReturn(true);

		// Test and Assert
		mockMvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(usuario1)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("Este email ya está registrado"));
	}

	@DisplayName("Register failure due to binding errors: null user")
    @Test
	void testRegisterNombreError() throws Exception {
		// Arrange

		when(usuarioService.existsByEmail(usuario1.getEmail())).thenReturn(false);
		when(roleService.findByName("ROLE_CLIENTE")).thenReturn(Optional.of(role_cliente));
		Usuario badUsuario= new Usuario();
		
		
		// Test and Assert
		mockMvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(badUsuario)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores",hasSize(5)));
	}
	
	


	
	

	
}