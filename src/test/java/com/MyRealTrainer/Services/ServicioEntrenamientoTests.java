package com.MyRealTrainer.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.MyRealTrainer.model.Entrenador;

import com.MyRealTrainer.model.Servicio;
import com.MyRealTrainer.model.Tarifa;
import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.repository.ServicioEntrenamientoRepository;
import com.MyRealTrainer.service.ServicioEntrenamientoService;
import com.MyRealTrainer.service.TarifaService;


@SpringBootTest 
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServicioEntrenamientoTests {

    @Autowired
	private ServicioEntrenamientoService entrenamientoService;

    @MockBean
    private ServicioEntrenamientoRepository servicioRepository;

    @MockBean
    private TarifaService tarifaService;
    
    private Servicio servicio;

    @BeforeAll
    public  void init(){
        // Arrange
       servicio= new Servicio();
       servicio.setId(1l);
       servicio.fillFields();
        
    }

    @Test
    @DisplayName("Test find servicio by id")
    public void testFindById(){
                
        // Act
		entrenamientoService.findById(this.servicio.getId());

        // Assert
        Mockito.verify(servicioRepository, Mockito.times(1)).findById(this.servicio.getId());
    }

    @Test
    @DisplayName("Test delete servicio by id")
    public void testDeleteById(){
                
        // Act
		entrenamientoService.deleteById(this.servicio.getId());

        // Assert
        Mockito.verify(servicioRepository, Mockito.times(1)).deleteById(this.servicio.getId());

    }

    @Test
    @DisplayName("Test save servicio")
    public void testSave(){
                
        // Act
		entrenamientoService.save(this.servicio);

        // Assert
        Mockito.verify(servicioRepository, Mockito.times(1)).save(this.servicio);

    }

  

    @Test
    @DisplayName("Test trying to edit tarifas that aren´t mine")
    public void testEditTarifasNotMine(){
        Servicio oldService= this.servicio;

        Tarifa myTarifa1= new Tarifa();
        myTarifa1.fillFields();
        myTarifa1.setTitulo("Tarifa 1");
        myTarifa1.setId(1l);

        Tarifa myTarifa2= new Tarifa();
        myTarifa2.fillFields();
        myTarifa2.setTitulo("Tarifa 2");
        myTarifa2.setId(2l);

        Tarifa tarifaNotMine= new Tarifa();
        tarifaNotMine.fillFields();
        tarifaNotMine.setTitulo("Tarifa 3");
        tarifaNotMine.setId(3l);

        oldService.setTarifas(List.of(myTarifa1,myTarifa2));
        
        Servicio editedService= new Servicio();
        editedService.fillFields();
        editedService.setTitulo("Edited service");
        editedService.setTarifas(List.of(myTarifa1,myTarifa2,tarifaNotMine));


        // Act
		boolean editingMyOwnTarifas= entrenamientoService.editingMyOwnTarifas(oldService, editedService);

        // Assert
        assertFalse(editingMyOwnTarifas);

    }

    @Test
    @DisplayName("Test  try to edit my own tarifas")
    public void testEditMyOwnTarifas(){
        Servicio oldService= this.servicio;

        Tarifa myTarifa1= new Tarifa();
        myTarifa1.fillFields();
        myTarifa1.setTitulo("Tarifa 1");
        myTarifa1.setId(1l);

        Tarifa myTarifa2= new Tarifa();
        myTarifa2.fillFields();
        myTarifa2.setTitulo("Tarifa 2");
        myTarifa2.setId(2l);

        Tarifa tarifaNotMine= new Tarifa();
        tarifaNotMine.fillFields();
        tarifaNotMine.setTitulo("Tarifa 3");
        tarifaNotMine.setId(3l);

        oldService.setTarifas(List.of(myTarifa1,myTarifa2));
        
        Servicio editedService= new Servicio();
        editedService.fillFields();
        editedService.setTitulo("Edited service");
        editedService.setTarifas(List.of(myTarifa1));


        // Act
		boolean editingMyOwnTarifas= entrenamientoService.editingMyOwnTarifas(oldService, editedService);

        // Assert
        assertTrue(editingMyOwnTarifas);

    }

    @Test
    @DisplayName("Test  tryng to get services of a null entrenador profile")
    public void testTryingToGetServicesWithNullEntrenador(){
        Usuario usuario= new Usuario();
        usuario.fillFields();


        // Act
		Map<String,Object> response= entrenamientoService.findMyServicios(usuario);
        List<String> errores= (List<String>) response.get("errores");
        // Assert
        assertEquals(errores.get(0), "Los perfiles que no son de tipo entrenador no pueden tener servicios");
    }

    @Test
    @DisplayName("Test  tryng to get services of a user")
    public void testGetMyServices(){
        Usuario usuario= new Usuario();
        usuario.fillFields();

        Entrenador entrenador= new Entrenador();
        entrenador.fillFields();
        usuario.setEntrenador(entrenador);

        Servicio returnedServicio= new Servicio();
        returnedServicio.fillFields();

        Mockito.when(servicioRepository.findMyServicios(entrenador.getId())).thenReturn(List.of(returnedServicio));

        // Act
		Map<String,Object> response= entrenamientoService.findMyServicios(usuario);
        List<Servicio> returnedServicios = (List<Servicio>) response.get("servicios");
        // Assert
        assertEquals(returnedServicios.size(), 1);
        assertEquals(returnedServicios.get(0), returnedServicio);
    }

    @Test
    @DisplayName("Test remove the missing tarifas of an edited service")
    public void testRemoveMissingTarifas(){
        servicio.fillFields();

        Tarifa tarifaToMaintain= new Tarifa();
        tarifaToMaintain.fillFields();
        tarifaToMaintain.setTitulo("Tarifa to maintain");
        tarifaToMaintain.setId(1l);

        Tarifa tarifaToRemove= new Tarifa();
        tarifaToRemove.fillFields();
        tarifaToRemove.setTitulo("Tarifa to remove");
        tarifaToRemove.setId(2l);

        servicio.setTarifas(List.of(tarifaToMaintain,tarifaToRemove));

        Servicio newService= new Servicio();
        newService.fillFields();
        newService.setTarifas(List.of(tarifaToMaintain));

        // Act
		entrenamientoService.deleteMissingTarifas(newService, servicio);
        
        // Assert
        verify(tarifaService,times(1)).deleteById(tarifaToRemove.getId());
    }

    @Test
    @DisplayName("Test trying to save a Servicio without Tarifas")
    public void testSaveServicioWitoutTarifas(){
        servicio.fillFields();
        servicio.setTarifas(List.of());
        
        Entrenador entrenador = new Entrenador();
        entrenador.fillFields();
        entrenador.setId(1l);

        Usuario usuario = new Usuario();
        usuario.fillFields();
        usuario.setId(1l);
        usuario.setEntrenador(entrenador);
        // Act
        Map<String,Object> response= entrenamientoService.constructAndSave(servicio, usuario);
        List<String> errores= (List<String>) response.get("errores");

        // Assert
        assertEquals(errores.get(0), "Debes asignar al menos una tarifa");
    }

    @Test
    @DisplayName("Test save Servicio success")
    public void testSaveServicioSuccess(){
        servicio.fillFields();

        Tarifa myTarifa1= new Tarifa();
        myTarifa1.fillFields();
        myTarifa1.setTitulo("Tarifa 1");
        myTarifa1.setId(1l);

        Tarifa myTarifa2= new Tarifa();
        myTarifa2.fillFields();
        myTarifa2.setTitulo("Tarifa 2");
        myTarifa2.setId(2l);
        
        servicio.setTarifas(List.of(myTarifa1,myTarifa2));

        Entrenador entrenador = new Entrenador();
        entrenador.fillFields();
        entrenador.setId(1l);

        Usuario usuario = new Usuario();
        usuario.fillFields();
        usuario.setId(1l);
        usuario.setEntrenador(entrenador);

        Map<String,Object> responseTarifa1= new HashMap<String,Object>();
        Tarifa tarifa1ToReturn= new Tarifa();
        tarifa1ToReturn.fillFields();
        tarifa1ToReturn.setId(myTarifa1.getId());
        tarifa1ToReturn.setServicio(servicio);
        tarifa1ToReturn.setTitulo("Tarifa 1");
        responseTarifa1.put("tarifa", tarifa1ToReturn);

        Map<String,Object> responseTarifa2= new HashMap<String,Object>();
        Tarifa tarifa2ToReturn= new Tarifa();
        tarifa2ToReturn.fillFields();
        tarifa2ToReturn.setId(myTarifa2.getId());
        tarifa2ToReturn.setTitulo("Tarifa 2");
        tarifa2ToReturn.setServicio(servicio);
        responseTarifa2.put("tarifa", tarifa2ToReturn);

        Servicio servicioToSave= new Servicio();
        servicioToSave.fillFields();
        servicioToSave.setTarifas(List.of());
        servicioToSave.setId(servicio.getId());
       
        Mockito.when(tarifaService.constructAndSave(servicioToSave, myTarifa1)).thenReturn(responseTarifa1);
        Mockito.when(tarifaService.constructAndSave(servicioToSave, myTarifa2)).thenReturn(responseTarifa2);

       
        Mockito.when(servicioRepository.save(any(Servicio.class))).thenReturn(servicioToSave);

        // Act
        Map<String,Object> response= entrenamientoService.constructAndSave(servicio, usuario);
        Servicio returnedServicio = (Servicio) response.get("servicio");
       

        // Assert
        Servicio finalExpectedServicio = servicioToSave;
        finalExpectedServicio.setTarifas(List.of(tarifa1ToReturn,tarifa2ToReturn));
       
        assertEquals(returnedServicio, finalExpectedServicio);
    }
    
    
    
    



    

}
