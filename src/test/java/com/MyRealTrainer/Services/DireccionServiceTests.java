package com.MyRealTrainer.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.MyRealTrainer.model.Direccion;
import com.MyRealTrainer.repository.DireccionRepository;
import com.MyRealTrainer.service.DireccionService;

@SpringBootTest 
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DireccionServiceTests {
    @Autowired
	private DireccionService direccionService;

	@MockBean
	private DireccionRepository direccionRepository;

    Direccion direccion;
 
    @BeforeAll
    public  void init(){
        // Arrange
        direccion= new Direccion();
        direccion.fillFields();
        direccion.setId(1l);

    }

    @Test
    @DisplayName("Test find direccion by id")
    public void testFindById(){
        // Act
		direccionService.findById(this.direccion.getId());
        // Assert
        Mockito.verify(direccionRepository, Mockito.times(1)).findById(this.direccion.getId());

    }

    @Test
    @DisplayName("Test save direccion")
    public void testSave(){
        // Act
		direccionService.save(this.direccion);
        // Assert
        Mockito.verify(direccionRepository, Mockito.times(1)).save(this.direccion);

    }

    @Test
    @DisplayName("Test delete direccion by id")
    public void testDeleteById(){
        // Act
		direccionService.deleteById(this.direccion.getId());
        // Assert
        Mockito.verify(direccionRepository, Mockito.times(1)).deleteById(this.direccion.getId());

    }

    @Test
    @DisplayName("Test isEmpty with a complete Direccion")
    public void testIsEmptyWithCompleteDirection(){
        direccion.fillFields();
        // Act
		boolean isEmpty= direccionService.isEmpty(this.direccion);
        // Assert
        assertEquals(isEmpty, false);
    }

    @Test
    @DisplayName("Test isEmpty without calle")
    public void testIsEmptyWithoutCalle(){
        direccion.fillFields();
        direccion.setCalle("");
        // Act
		boolean isEmpty= direccionService.isEmpty(this.direccion);
        // Assert
        assertEquals(isEmpty, false);
    }

    @Test
    @DisplayName("Test isEmpty without: calle,ciudad")
    public void testIsEmptyWithoutCalleCiudad(){
        direccion.fillFields();
        direccion.setCalle("");
        direccion.setCiudad("");
        // Act
		boolean isEmpty= direccionService.isEmpty(this.direccion);
        // Assert
        assertEquals(isEmpty, false);
    }

    @Test
    @DisplayName("Test isEmpty without: calle,ciudad,codigoPostal")
    public void testIsEmptyWithoutCalleCiudadCodigoPostal(){
        direccion.fillFields();
        direccion.setCalle("");
        direccion.setCiudad("");
        direccion.setCodigoPostal(null);
        // Act
		boolean isEmpty= direccionService.isEmpty(this.direccion);
        // Assert
        assertEquals(isEmpty, false);
    }

    @Test
    @DisplayName("Test isEmpty without: calle,ciudad,codigoPostal,provincia")
    public void testIsEmptyWithEmptyDireccion(){
        direccion.fillFields();
        direccion.setCalle("");
        direccion.setCiudad("");
        direccion.setCodigoPostal(null);
        direccion.setProvincia("");
        // Act
		boolean isEmpty= direccionService.isEmpty(this.direccion);
        // Assert
        assertEquals(isEmpty, true);
    }


    @Test
    @DisplayName("Test Numero without calle")
    public void testNumeroWithoutCalle(){
        direccion.fillFields();
        direccion.setCalle("");
        direccion.setPiso("");
        
        // Act
		boolean isInvalid= direccionService.direccionWithoutCalle(this.direccion);
        // Assert
        assertEquals(isInvalid, true);
    }

    @Test
    @DisplayName("Test Piso without calle")
    public void testPisoWithoutCalle(){
        direccion.fillFields();
        direccion.setCalle("");
        direccion.setNumero("");
        
        // Act
		boolean isInvalid= direccionService.direccionWithoutCalle(this.direccion);
        // Assert
        assertEquals(isInvalid, true);
    }


    
    

}
