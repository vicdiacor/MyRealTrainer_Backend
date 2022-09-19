package com.MyRealTrainer.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.MyRealTrainer.model.LugarEntrenamiento;
import com.MyRealTrainer.model.Servicio;
import com.MyRealTrainer.model.Tarifa;
import com.MyRealTrainer.repository.TarifaRepository;
import com.MyRealTrainer.service.LugarEntrenamientoService;
import com.MyRealTrainer.service.TarifaService;

@SpringBootTest 
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TarifaServiceTests {
    @Autowired
	private TarifaService tarifaService;

	@MockBean
	private TarifaRepository tarifaRepository;

    @MockBean
    private LugarEntrenamientoService lugarService;

    private Tarifa tarifa;
    
    @BeforeAll
    public  void init(){
        // Arrange
        tarifa= new Tarifa();
        tarifa.setId(1l);
        tarifa.fillFields();
        
    }

    @Test
    @DisplayName("Test find tarifa by id")
    public void testFindById(){
                
        // Act
		tarifaService.findById(this.tarifa.getId());

        // Assert
        Mockito.verify(tarifaRepository, Mockito.times(1)).findById(this.tarifa.getId());
    }

    @Test
    @DisplayName("Test save tarifa")
    public void testSave(){
                
        // Act
		tarifaService.save(this.tarifa);

        // Assert
        Mockito.verify(tarifaRepository, Mockito.times(1)).save(this.tarifa);
    }

    @Test
    @DisplayName("Test delete tarifa by id")
    public void testDeleteById(){
                
        // Act
		tarifaService.deleteById(this.tarifa.getId());

        // Assert
        Mockito.verify(tarifaRepository, Mockito.times(1)).deleteById(this.tarifa.getId());
    }

    @Test
    @DisplayName("Test trying to save a tarifa to an inexistent lugar")
    public void testCreateToInexistentLugar(){
                
        // Arrange
		LugarEntrenamiento inexistentLugar= new LugarEntrenamiento();
        inexistentLugar.setId(1l);
        inexistentLugar.fillFields();

        tarifa.setLugares(List.of(inexistentLugar));

        Servicio servicio = new Servicio();
        servicio.setId(1l);
        servicio.fillFields();

        Mockito.when(lugarService.findById(inexistentLugar.getId())).thenReturn(Optional.empty());

        // Act
        Map<String,Object> response= tarifaService.constructAndSave(servicio, tarifa);
        List<String> errores= (List<String>) response.get("errores");

        // Assert
        assertEquals(errores.get(0), "Se está intentando asociar una tarifa a un lugar que no existe, cuyo id es: " + String.valueOf(inexistentLugar.getId()));

    }

    @Test
    @DisplayName("Test trying to save a new tarifa")
    public void testCreateNewTarifaSuccess(){
                
        // Arrange
		LugarEntrenamiento existentLugar= new LugarEntrenamiento();
        existentLugar.setId(1l);
        existentLugar.fillFields();

        tarifa.setLugares(List.of(existentLugar));

        Servicio servicio = new Servicio();
        servicio.setId(1l);
        servicio.fillFields();

        Tarifa tarifaAtEnd= tarifa;
        tarifaAtEnd.setServicio(servicio);

        Mockito.when(lugarService.findById(existentLugar.getId())).thenReturn(Optional.of(existentLugar));
        Mockito.when(tarifaRepository.save(tarifaAtEnd)).thenReturn(tarifaAtEnd);
        // Act
        Map<String,Object> response= tarifaService.constructAndSave(servicio, tarifa);

        // Assert
        assertEquals((Tarifa) response.get("tarifa"), tarifaAtEnd);

    }

}
