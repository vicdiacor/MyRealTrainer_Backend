package com.MyRealTrainer.web;

import com.MyRealTrainer.SecurityConfig.WithMockCustomUser;
import com.MyRealTrainer.model.Direccion;
import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.LugarEntrenamiento;
import com.MyRealTrainer.model.Role;
import com.MyRealTrainer.model.TipoLugar;
import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.repository.EntrenadorRepository;
import com.MyRealTrainer.service.CustomUserDetailsService;
import com.MyRealTrainer.service.EntrenadorService;
import com.MyRealTrainer.service.LugarEntrenamientoService;
import com.MyRealTrainer.service.RoleService;
import com.MyRealTrainer.service.UsuarioService;
import com.MyRealTrainer.service.UtilService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
@WebMvcTest(controllers = LugarEntrenamientoController.class)
public class LugarEntrenamientoControllerTests {

	@MockBean
	LugarEntrenamientoService lugarService;

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

	private LugarEntrenamiento lugar;

	private Direccion direccion;

	private Usuario usuario;

	private Entrenador entrenador;

	public static final String emailUsuario= "user@email.com";

	@BeforeAll
	void init() {
		lugar= new LugarEntrenamiento();
		lugar.fillFields();
		lugar.setId(1l);

		direccion= new Direccion();
		direccion.fillFields();
		direccion.setId(1l);
		direccion.setLugar(lugar);
		lugar.setDireccion(direccion);

		usuario= new Usuario();
		usuario.fillFields();
		usuario.setId(1l);

		entrenador= new Entrenador();
		entrenador.fillFields();
		entrenador.setLugares(List.of(lugar));

		usuario.setEntrenador(entrenador);

		

	}

	@DisplayName("Create lugarEntrenamiento success")
	@Order(1)
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
    @Test
	void testCreateSuccess() throws Exception {
		// Arrange

		Map<String,Object> lugarServiceResponse= new HashMap<>();
		lugarServiceResponse.put("lugar", lugar);
		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.of(usuario));
		when(lugarService.createNewLugar(any(LugarEntrenamiento.class), any(Usuario.class))).thenReturn(lugarServiceResponse);

		// Test and Assert
		mockMvc.perform(post("/lugares/"+emailUsuario).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(lugar)))
		.andExpect(status().isOk());
	}

	@DisplayName("Create lugar binding errors")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
    @Test
	void testCreateBindingErrors() throws Exception {
		// Arrange

		LugarEntrenamiento emptyLugar= new LugarEntrenamiento();
		
		// Test and Assert
		mockMvc.perform(post("/lugares/"+emailUsuario).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(emptyLugar)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores").exists());;
	}

	@DisplayName("Create lugarEntrenamiento for an inexistent user")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
    @Test
	void testCreateUserNotExists() throws Exception {
		// Arrange
		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.empty());

		// Test and Assert
		mockMvc.perform(post("/lugares/"+emailUsuario).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(lugar)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("Este usuario no existe"));
	}


	@DisplayName("Try to create a LugarEntrenamiento as a cliente for another user")
	@WithMockCustomUser(username ="another@email.com" , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
    @Test
	void testTryToCreateEntrenadorToAnotherUser() throws Exception {
		// Arrange
	

		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.of(usuario));
		// Test and Assert
		mockMvc.perform(post("/lugares/"+emailUsuario).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(lugar)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("No tienes permiso para asociar este lugar de entrenamiento a este usuario"));
	}

	@DisplayName("Try to create a LugarEntrenamiento but LugarService returns errors")
	@WithMockCustomUser(username = emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
    @Test
	void testLugarServiceReturnsErrors() throws Exception {
		// Arrange
		Map<String,Object> responseService= new HashMap<>();
		responseService.put("errores", List.of("No puedes crear una dirección con número o bloque sin incorporar una calle"));

		when(lugarService.createNewLugar(any(LugarEntrenamiento.class), any(Usuario.class))).thenReturn(responseService);
		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.of(usuario));

		// Test and Assert
		mockMvc.perform(post("/lugares/"+emailUsuario).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(lugar)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("No puedes crear una dirección con número o bloque sin incorporar una calle"));
	}

	@DisplayName("Find lugares of an inexistent user")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
    @Test
	void testFindLugaresInexistentUser() throws Exception {
		// Arrange
		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.empty());

		// Test and Assert
		mockMvc.perform(get("/lugares/"+emailUsuario))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("Este usuario no existe"));
	}

	@DisplayName("Find find my lugares success")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
    @Test
	void testFindMyLugaresSuccess() throws Exception {
		// Arrange
		Map<Long,LugarEntrenamiento> lugaresMap= new HashMap<>();
		lugaresMap.put(lugar.getId(), lugar);

		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.of(usuario));
		when(lugarService.createMapLugares(usuario.getEntrenador().getLugares())).thenReturn(lugaresMap);

		// Test and Assert
		mockMvc.perform(get("/lugares/"+emailUsuario))
		.andExpect(status().isOk());
	}

	@DisplayName("Try to get the lugaresEntrenamiento of another user")
	@WithMockCustomUser(username ="another@email.com" , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
    @Test
	void testTryToGetLugaresAnotherUser() throws Exception {
		// Arrange
	
		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.of(usuario));
		// Test and Assert
		mockMvc.perform(get("/lugares/"+emailUsuario))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("No tienes permiso para obtener los lugares de las sesiones de este usuario"));
	}

	
}