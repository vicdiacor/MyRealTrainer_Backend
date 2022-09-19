package com.MyRealTrainer.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.MyRealTrainer.model.Direccion;
import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.LugarEntrenamiento;
import com.MyRealTrainer.model.TipoLugar;
import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.repository.LugarEntrenamientoRepository;
import com.MyRealTrainer.repository.UsuarioRepository;
import com.MyRealTrainer.service.DireccionService;
import com.MyRealTrainer.service.LugarEntrenamientoService;
import com.MyRealTrainer.service.UsuarioService;

@SpringBootTest 
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LugarEntrenamientoServiceTests {
    @Autowired
	private LugarEntrenamientoService lugarService;

	@MockBean
	private LugarEntrenamientoRepository lugarRepository;

    @MockBean
    private DireccionService direccionService;

    private LugarEntrenamiento lugar1;


    @BeforeAll
    public  void init(){
        // Arrange

        lugar1 = new LugarEntrenamiento();
        lugar1.fillFields();
        lugar1.setId(1l);
    }

   

    @Test
    @DisplayName("Test find lugares by id")
    public void testFindById(){
                
        // Act
		lugarService.findById(this.lugar1.getId());

        // Assert
        Mockito.verify(lugarRepository, Mockito.times(1)).findById(this.lugar1.getId());
    }


    @Test
    @DisplayName("Test save lugares")
    public void testSave(){

        // Act
		lugarService.save(this.lugar1);

        // Assert
        Mockito.verify(lugarRepository, Mockito.times(1)).save(this.lugar1);

    }

    @Test
    @DisplayName("Test delete lugar by id")
    public void testDeleteById(){
      
        // Act
		lugarService.deleteById(this.lugar1.getId());

        // Assert
        Mockito.verify(lugarRepository, Mockito.times(1)).deleteById(this.lugar1.getId());
    }

    @Test
    @DisplayName("Test create a map (id:Lugar) from a List<Lugar>")
    public void testCreateMapLugares(){
        LugarEntrenamiento lugar2= new LugarEntrenamiento();
        lugar2.setId(2l);
        lugar2.fillFields();
        lugar2.setTitulo("Lugar 2");

        LugarEntrenamiento lugar3= new LugarEntrenamiento();
        lugar3.setId(3l);
        lugar3.fillFields();
        lugar3.setTitulo("Lugar 3");

    
        Map<Long,LugarEntrenamiento> expectedMap= new HashMap<Long,LugarEntrenamiento>();
        expectedMap.put(2l, lugar2);
        expectedMap.put(3l, lugar3);

        // Act
		Map<Long,LugarEntrenamiento> responseMap=lugarService.createMapLugares(List.of(lugar2,lugar3));

        // Assert
        assertEquals(expectedMap, responseMap);
    }

    @Test
    @DisplayName("Test error trying to create a Lugar without a created Entrenador profile")
    public void testCreateLugarWithoutEntrenador(){
        Usuario usuarioWithoutEntrenador= new Usuario();
        usuarioWithoutEntrenador.fillFields();

        // Act
		Map<String,Object> response=lugarService.createNewLugar(this.lugar1, usuarioWithoutEntrenador);
        List<String> errores= (List) response.get("errores");
        // Assert
        assertEquals(errores.get(0), "No puedes crear lugares de entrenamiento si no tienes creado un perfil de entrenador");

    }

    @Test
    @DisplayName("Test error trying to create a Lugar with an address number direction but without a calle field")
    public void testCreateLugarNumberDireccionWithoutCalleName(){
        
        Entrenador entrenador = new Entrenador();
        entrenador.fillFields();

        Usuario usuario= new Usuario();
        usuario.fillFields();
        usuario.setEntrenador(entrenador);
        

        Direccion direccion = new Direccion();
        direccion.fillFields();
        direccion.setCalle("");
        this.lugar1.setDireccion(direccion);

        Mockito.when(direccionService.direccionWithoutCalle(direccion)).thenReturn(true);


        // Act
		Map<String,Object> response=lugarService.createNewLugar(this.lugar1, usuario);
        List<String> errores= (List) response.get("errores");
        // Assert
        assertEquals(errores.get(0), "No puedes crear una dirección con número o bloque sin incorporar una calle");

    }

    @Test
    @DisplayName("Test trying to create a Lugar without an associated Direccion")
    public void testCreateLugarWithoutDireccion(){
        
        Entrenador entrenador = new Entrenador();
        entrenador.fillFields();

        Usuario usuario= new Usuario();
        usuario.fillFields();
        usuario.setEntrenador(entrenador);
        this.lugar1.setDireccion(null);

        Mockito.when(lugarRepository.save(this.lugar1)).thenReturn(this.lugar1);


        // Act
		Map<String,Object> response=lugarService.createNewLugar(this.lugar1, usuario);
        
        // Assert
        assertEquals(response.get("lugar"), this.lugar1);

    }

    @Test
    @DisplayName("Test trying to create a Lugar with an empty Direccion")
    public void testCreateLugarWithEmptyDireccion(){
        
        Entrenador entrenador = new Entrenador();
        entrenador.fillFields();

        Usuario usuario= new Usuario();
        usuario.fillFields();
        usuario.setEntrenador(entrenador);

        Direccion direccion = new Direccion();
        this.lugar1.setDireccion(direccion);

        Mockito.when(lugarRepository.save(this.lugar1)).thenReturn(this.lugar1);
        Mockito.when(direccionService.isEmpty(direccion)).thenReturn(true);

        // Act
		Map<String,Object> response=lugarService.createNewLugar(this.lugar1, usuario);
        
        // Assert
        assertEquals(response.get("lugar"), this.lugar1);
        verify(direccionService,never()).save(direccion);
        verify(direccionService,times(1)).isEmpty(direccion);

    }

    @Test
    @DisplayName("Test trying to create a Lugar with a correct associated Direccion")
    public void testCreateLugarWithDireccion(){
        
        Entrenador entrenador = new Entrenador();
        entrenador.fillFields();

        Usuario usuario= new Usuario();
        usuario.fillFields();
        usuario.setEntrenador(entrenador);

        Direccion direccion = new Direccion();
        direccion.fillFields();
       
        LugarEntrenamiento lugar2= new LugarEntrenamiento();
        lugar2.setId(2l);
        lugar2.fillFields();
        lugar2.setTitulo("Lugar 2");
        lugar2.setDireccion(direccion);

        LugarEntrenamiento savedLugar= new LugarEntrenamiento();
        savedLugar.setId(2l);
        savedLugar.fillFields();
        savedLugar.setTitulo("Lugar 2");
        savedLugar.setDireccion(null);

        Direccion direccionAfterSave= new Direccion();
        direccion.fillFields();
        direccionAfterSave.setLugar(savedLugar);

        LugarEntrenamiento lugarEndService= new LugarEntrenamiento();
        lugarEndService.setId(2l);
        lugarEndService.fillFields();
        lugarEndService.setTitulo("Lugar 2");
        lugarEndService.setDireccion(direccionAfterSave);

        

        Mockito.when(lugarRepository.save(savedLugar)).thenReturn(savedLugar);
        Mockito.when(direccionService.isEmpty(direccion)).thenReturn(false);
        Mockito.when(direccionService.save(direccion)).thenReturn(direccionAfterSave);



        // Act
		Map<String,Object> response=lugarService.createNewLugar(lugar2, usuario);
        
        // Assert
        assertEquals(response.get("lugar"), lugarEndService);
        verify(lugarRepository,times(1)).save(savedLugar);
        verify(direccionService,times(1)).save(direccionAfterSave);

    }

    @Test
    @DisplayName("Test trying to assign default Lugares to a Entrenador")
    public void testAssignDefaultLugaresToEntrenador(){
        // ARRANGE
        Entrenador entrenador= new Entrenador();
        entrenador.fillFields();


        LugarEntrenamiento lugarGimnasio= new LugarEntrenamiento("Mi gimnasio", TipoLugar.MI_GIMNASIO, entrenador);

        LugarEntrenamiento lugarAireLibre= new LugarEntrenamiento("Aire libre", TipoLugar.AIRE_LIBRE, entrenador);
                       
        LugarEntrenamiento lugarDomicilio= new LugarEntrenamiento("Tu domicilio", TipoLugar.TU_DOMICILIO, entrenador);
                       
        LugarEntrenamiento lugarTelematico= new LugarEntrenamiento("Telemático", TipoLugar.TELEMATICO, entrenador);

        List<LugarEntrenamiento> listLugares= List.of(lugarGimnasio,lugarAireLibre,lugarDomicilio,lugarTelematico);

        Entrenador entrenadorAfterService= new Entrenador();
        entrenador.fillFields();
        entrenadorAfterService.setLugares(listLugares);

        Mockito.when(lugarRepository.save(lugarGimnasio)).thenReturn(lugarGimnasio);
        Mockito.when(lugarRepository.save(lugarAireLibre)).thenReturn(lugarAireLibre);
        Mockito.when(lugarRepository.save(lugarDomicilio)).thenReturn(lugarDomicilio);
        Mockito.when(lugarRepository.save(lugarTelematico)).thenReturn(lugarTelematico);

        // ACT
        Entrenador response= lugarService.assignDefaultLugares(entrenador);

        // ASSERT
        assertEquals(response, entrenadorAfterService);
        Mockito.verify(lugarRepository,times(4)).save(any(LugarEntrenamiento.class));


        


    }



   


    

}
