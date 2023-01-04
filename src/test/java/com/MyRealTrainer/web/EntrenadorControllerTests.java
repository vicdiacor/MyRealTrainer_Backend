package com.MyRealTrainer.web;

import com.MyRealTrainer.SecurityConfig.WithMockCustomUser;
import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.LugarEntrenamiento;
import com.MyRealTrainer.model.TipoLugar;
import com.MyRealTrainer.model.Usuario;

import com.MyRealTrainer.service.CustomUserDetailsService;
import com.MyRealTrainer.service.EntrenadorService;
import com.MyRealTrainer.service.UsuarioService;
import com.MyRealTrainer.service.UtilService;

import com.fasterxml.jackson.databind.ObjectMapper;



import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = EntrenadorController.class)
public class EntrenadorControllerTests {

	@MockBean
	EntrenadorService entrenadorService;

	@MockBean
	private UsuarioService usuarioService;

	@SpyBean
	private UtilService utilService;

	@MockBean
	CustomUserDetailsService customUserDetailsService;
	
	@Autowired
  	private ObjectMapper objectMapper;

	@MockBean
	DataSource dataSource;

	@MockBean
    private AuthenticationManager authenticationManager;

	  
	@Autowired
	MockMvc mockMvc;

	private Entrenador entrenador;

	private Usuario usuario;

	public static final String emailUsuario= "user@email.com";

	@BeforeAll
	void init() {
		entrenador= new Entrenador();
		entrenador.fillFields();
		entrenador.setId(1l);

		usuario= new Usuario();
		usuario.fillFields();
		usuario.setId(1l);


	}

	@DisplayName("Create entrenador success")
	@Order(1)
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
    @Test
	void testCreateSuccess() throws Exception {
		// Arrange

		Entrenador expectedEntrenador= new Entrenador();
		BeanUtils.copyProperties(entrenador, expectedEntrenador);
		expectedEntrenador.setUsuario(usuario);

		LugarEntrenamiento testLugar = new LugarEntrenamiento("Mi gimnasio", TipoLugar.MI_GIMNASIO, entrenador);
		expectedEntrenador.setLugares(List.of(testLugar));

		Map<String,Object> expectedRepsonse=new HashMap<>();
		expectedRepsonse.put("entrenador", expectedEntrenador);

		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.of(usuario));
		when(entrenadorService.createNewEntrenador(entrenador, usuario)).thenReturn(expectedRepsonse);
		// Test and Assert
		mockMvc.perform(post("/entrenadores/"+emailUsuario).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(entrenador)))
		.andExpect(status().isOk());
	}

	@DisplayName("Create entrenador binding errors")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
    @Test
	void testCreateBindingErrors() throws Exception {
		// Arrange

		Entrenador emptyEntrenador= new Entrenador();
		
		// Test and Assert
		mockMvc.perform(post("/entrenadores/"+emailUsuario).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(emptyEntrenador)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores").exists());;
	}

	@DisplayName("Create entrenador with inexistent user")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
    @Test
	void testCreateUserNotExists() throws Exception {
		// Arrange
	
		usuario.fillFields();
		entrenador.fillFields();
		usuario.setEntrenador(entrenador);
		
		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.empty());

		// Test and Assert
		mockMvc.perform(post("/entrenadores/"+emailUsuario).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(entrenador)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("Este usuario no existe"));
	}


	@DisplayName("Try to create a Entrenador for another user")
	@Order(1)
	@WithMockCustomUser(username ="another@email.com" , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
    @Test
	void testTryToCreateEntrenadorToAnotherUser() throws Exception {
		// Arrange
		usuario.fillFields();
		entrenador.fillFields();
		usuario.setEntrenador(entrenador);

		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.of(usuario));
		// Test and Assert
		mockMvc.perform(post("/entrenadores/"+emailUsuario).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(entrenador)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("No tienes permiso para acceder"));;
	}

	
}