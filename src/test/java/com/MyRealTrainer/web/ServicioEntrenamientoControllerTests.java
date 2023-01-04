package com.MyRealTrainer.web;

import com.MyRealTrainer.SecurityConfig.WithMockCustomUser;

import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.LugarEntrenamiento;
import com.MyRealTrainer.model.Servicio;
import com.MyRealTrainer.model.Tarifa;
import com.MyRealTrainer.model.Usuario;

import com.MyRealTrainer.service.CustomUserDetailsService;
import com.MyRealTrainer.service.ServicioEntrenamientoService;
import com.MyRealTrainer.service.UsuarioService;
import com.MyRealTrainer.service.UtilService;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = ServicioEntrenamientoController.class)
public class ServicioEntrenamientoControllerTests {

	@MockBean
	ServicioEntrenamientoService servicioEntrenamientoService;

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

	private Servicio servicio;

	private Usuario usuario;

	private Entrenador entrenador;

	public static final String emailUsuario= "user@email.com";

	@BeforeAll
	void init() {
	
		usuario= new Usuario();
		usuario.fillFields();
		usuario.setId(1l);

		entrenador= new Entrenador();
		entrenador.fillFields();
		entrenador.setUsuario(usuario);

		usuario.setEntrenador(entrenador);

		LugarEntrenamiento lugar= new LugarEntrenamiento();
		lugar.fillFields();
		lugar.setId(1l);


		Tarifa tarifa1= new Tarifa();
		tarifa1.fillFields();
		tarifa1.setTitulo("Tarifa 1");
		tarifa1.setLugares(List.of(lugar));

		Tarifa tarifa2= new Tarifa();
		tarifa2.fillFields();
		tarifa2.setPrecio(20.0);
		tarifa2.setTitulo("Tarifa 2");
		tarifa2.setLugares(List.of(lugar));

		servicio= new Servicio();
		servicio.fillFields();
		servicio.setId(1l);
		servicio.setTarifas(List.of(tarifa1,tarifa2));
		servicio.setEntrenador(entrenador);

	}

	@DisplayName("Create servicio binding errors")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(1)
    @Test
	void testCreateBindingErrors() throws Exception {
		// Arranges

		Servicio emptyServicio= new Servicio();
		
		// Test and Assert
		mockMvc.perform(post("/servicios/"+emailUsuario).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(emptyServicio)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores").exists());
	}

	
	@DisplayName("Create servicio success")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(2)
    @Test
	void testCreateServicioSuccess() throws Exception {
		// Arrange
		Map<String,Object> responseService= new HashMap<>();
		responseService.put("servicio", servicio);

		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.of(usuario));
		when(servicioEntrenamientoService.constructAndSave(servicio, usuario)).thenReturn(responseService);

		// Test and Assert
		mockMvc.perform(post("/servicios/"+emailUsuario).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(servicio)))
		.andExpect(status().isOk());
	}

	@DisplayName("Errors trying create a servoce for an inexistent user")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(3)
    @Test
	void testErrorsInexistentUser() throws Exception {
		// Arrange

		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.empty());

		// Test and Assert
		mockMvc.perform(post("/servicios/"+emailUsuario).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(servicio)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("Este usuario no existe"));;
	}

	@DisplayName("Errors trying to save the service in the database")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(4)
    @Test
	void testErrorsDatabase() throws Exception {
		// Arrange
		Map<String,Object> responseService= new HashMap<>();
		responseService.put("errores", List.of("Debes asignar al menos una tarifa"));

		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.of(usuario));
		when(servicioEntrenamientoService.constructAndSave(any(Servicio.class), any(Usuario.class))).thenReturn(responseService);

		// Test and Assert
		mockMvc.perform(post("/servicios/"+emailUsuario).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(servicio)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("Debes asignar al menos una tarifa"));
	}

	@DisplayName("Errors trying to create a service for another user")
	@WithMockCustomUser(username ="anotherUser@email.com" , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(5)
    @Test
	void testCreateServicioForAnotherUser() throws Exception {
		// Arrange
		

		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.of(usuario));

		// Test and Assert
		mockMvc.perform(post("/servicios/"+emailUsuario).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(servicio)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("No tienes permiso para crear un servicio para este usuario"));;
	}

	@DisplayName("Errors trying to find the services of an inexistent user")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(6)
    @Test
	void testGetServiciosOfInexistentUser() throws Exception {
		// Arrange
		

		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.empty());

		// Test and Assert
		mockMvc.perform(get("/servicios/"+emailUsuario))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("Este usuario no existe"));;
	}

	@DisplayName("Test get all my services success")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(7)
    @Test
	void testGetAllMyServicesSucess() throws Exception {
		// Arrange
		Map<String,Object> responseService= new HashMap<>();
		responseService.put("servicios", List.of(servicio));

		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.of(usuario));
		when(servicioEntrenamientoService.findMyServicios(usuario)).thenReturn(responseService);

		// Test and Assert
		mockMvc.perform(get("/servicios/"+emailUsuario))
		.andExpect(status().isOk());
	}

	@DisplayName("Test get all my services database error")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(8)
    @Test
	void testGetMyServicesDatabaseError() throws Exception {
		// Arrange
		Map<String,Object> responseService= new HashMap<>();
		responseService.put("errores", List.of("Los perfiles que no son de tipo entrenador no pueden tener servicios"));

		when(usuarioService.findByEmail(emailUsuario)).thenReturn(Optional.of(usuario));
		when(servicioEntrenamientoService.findMyServicios(usuario)).thenReturn(responseService);

		// Test and Assert
		mockMvc.perform(get("/servicios/"+emailUsuario))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("Los perfiles que no son de tipo entrenador no pueden tener servicios"));
	}

	@DisplayName("Test binding errors trying to update a service")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(9)
    @Test
	void testUpdateBindingErrors() throws Exception {

		


		Servicio emptyServicio = new Servicio();
		// Test and Assert
		mockMvc.perform(put("/servicios/"+servicio.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(emptyServicio)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores").exists());
	}

	

	@DisplayName("Test errors trying to update an  inexistent service")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(10)
    @Test
	void testUpdateInexistentService() throws Exception {
		
		when(servicioEntrenamientoService.findById(servicio.getId())).thenReturn(Optional.empty());
		// Test and Assert
		mockMvc.perform(put("/servicios/"+servicio.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(servicio)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("Este servicio no existe"));
	}

	@DisplayName("Test update a service success")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(11)
    @Test
	void testUpdateSuccess() throws Exception {
		Servicio oldService= new Servicio();
		oldService.setEntrenador(entrenador);

		Map<String,Object> responseService= new HashMap<>();
		responseService.put("servicio", servicio);
		
		when(servicioEntrenamientoService.findById(servicio.getId())).thenReturn(Optional.of(oldService));
		when(servicioEntrenamientoService.editingMyOwnTarifas(any(Servicio.class), any(Servicio.class))).thenReturn(true);
		when(servicioEntrenamientoService.constructAndSave(servicio, usuario)).thenReturn(responseService);

		// Test and Asserts
		mockMvc.perform(put("/servicios/"+servicio.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(servicio)))
		.andExpect(status().isOk());
	}

	@DisplayName("Test database errors trying to update a service")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(12)
    @Test
	void testDatabaseErrorsUpdate() throws Exception {
		Servicio oldService= new Servicio();
		oldService.setEntrenador(entrenador);

		Map<String,Object> responseService= new HashMap<>();
		responseService.put("errores", List.of("Debes asignar al menos una tarifa"));
		
		when(servicioEntrenamientoService.findById(servicio.getId())).thenReturn(Optional.of(oldService));
		when(servicioEntrenamientoService.editingMyOwnTarifas(any(Servicio.class), any(Servicio.class))).thenReturn(true);
		when(servicioEntrenamientoService.constructAndSave(any(Servicio.class), any(Usuario.class))).thenReturn(responseService);

		// Test and Asserts
		mockMvc.perform(put("/servicios/"+servicio.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(servicio)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("Debes asignar al menos una tarifa"));
	}

	@DisplayName("Test failure trying to update a service of another user")
	@WithMockCustomUser(username ="another@email.com" , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(13)
    @Test
	void testUpdateServiceAnotherUser() throws Exception {
	
		
		when(servicioEntrenamientoService.findById(servicio.getId())).thenReturn(Optional.of(servicio));

		// Test and Asserts
		mockMvc.perform(put("/servicios/"+servicio.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(servicio)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("No tienes permiso para editar un servicio para este usuario"));
	}

	@DisplayName("Test errors trying to update a tarifa of another user")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(14)
    @Test
	void testUpdateTarifaError() throws Exception {
		Servicio oldService= new Servicio();
		oldService.setEntrenador(entrenador);

		Map<String,Object> responseService= new HashMap<>();
		responseService.put("servicio", servicio);
		
		when(servicioEntrenamientoService.findById(servicio.getId())).thenReturn(Optional.of(oldService));
		when(servicioEntrenamientoService.editingMyOwnTarifas(any(Servicio.class), any(Servicio.class))).thenReturn(false);

		// Test and Asserts
		mockMvc.perform(put("/servicios/"+servicio.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(servicio)))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("Estás intentando modificar o asociar a tus servicios tarifas de otros usuarios"));
	}

	@DisplayName("Test errors trying to delete an  inexistent service")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(15)
    @Test
	void testDeleteInexistentService() throws Exception {
		
		when(servicioEntrenamientoService.findById(servicio.getId())).thenReturn(Optional.empty());
		// Test and Assert
		mockMvc.perform(delete("/servicios/"+servicio.getId()))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("Este servicio no existe"));
	}

	@DisplayName("Test errors trying to delete a service of another user")
	@WithMockCustomUser(username ="another@email.com" , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(16)
    @Test
	void testTryingToDeleteServiceAnotherUser() throws Exception {
		
		when(servicioEntrenamientoService.findById(servicio.getId())).thenReturn(Optional.of(servicio));
		// Test and Assert
		mockMvc.perform(delete("/servicios/"+servicio.getId()))
		.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errores[0]").value("No tienes permiso para eliminar un servicio de otro usuario"));
	}

	@DisplayName("Test delete a service success")
	@WithMockCustomUser(username =emailUsuario , roles = {"ROLE_CLIENTE","ROLE_ENTRENADOR"})
	@Order(17)
    @Test
	void testDeleteServiceSuccess() throws Exception {
		
		when(servicioEntrenamientoService.findById(servicio.getId())).thenReturn(Optional.of(servicio));
		// Test and Assert
		mockMvc.perform(delete("/servicios/"+servicio.getId()))
		.andExpect(status().isOk());
	}


	

	
}