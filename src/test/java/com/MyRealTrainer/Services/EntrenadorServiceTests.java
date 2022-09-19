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
import com.MyRealTrainer.repository.EntrenadorRepository;
import com.MyRealTrainer.repository.LugarEntrenamientoRepository;
import com.MyRealTrainer.repository.UsuarioRepository;
import com.MyRealTrainer.service.DireccionService;
import com.MyRealTrainer.service.EntrenadorService;
import com.MyRealTrainer.service.LugarEntrenamientoService;
import com.MyRealTrainer.service.UsuarioService;

@SpringBootTest 
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EntrenadorServiceTests {
    @Autowired
	private EntrenadorService entrenadorService;

	@MockBean
	private EntrenadorRepository entrenadorRepository;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private LugarEntrenamientoService lugarService;


    private Entrenador entrenador;

    @BeforeAll
    public  void init(){
        // Arrange
        entrenador= new Entrenador();
        entrenador.setId(1l);
        entrenador.fillFields();
    }

    
    @Test
    @DisplayName("Test find entrenador by id")
    public void testFindById(){
                
        // Act
		entrenadorService.findById(this.entrenador.getId());

        // Assert
        Mockito.verify(entrenadorRepository, Mockito.times(1)).findById(this.entrenador.getId());
    }

     
    @Test
    @DisplayName("Test delete entrenador by id")
    public void testDeleteById(){
                
        // Act
		entrenadorService.deleteById(this.entrenador.getId());

        // Assert
        Mockito.verify(entrenadorRepository, Mockito.times(1)).deleteById(this.entrenador.getId());
    }

    @Test
    @DisplayName("Test save entrenador")
    public void testSave(){
                
        // Act
		entrenadorService.save(this.entrenador);

        // Assert
        Mockito.verify(entrenadorRepository, Mockito.times(1)).save(this.entrenador);
    }

    @Test
    @DisplayName("Test update entrenador")
    public void testUpdate(){
        
        Entrenador editedEntrenador= new Entrenador();
        editedEntrenador.setId(6l); // This field should be ignored
        editedEntrenador.setDescripcionExperiencia("A new description");

        Entrenador expectedEntrenador= this.entrenador;
        expectedEntrenador.setDescripcionExperiencia("A new description");
        Mockito.when(entrenadorRepository.save(expectedEntrenador)).thenReturn(expectedEntrenador);

        // Act
		Entrenador savedEntrenador= entrenadorService.updateEntrenador(editedEntrenador,this.entrenador);

        // Assert
        assertEquals(savedEntrenador, expectedEntrenador);
    }

    @Test
    @DisplayName("Test create new entrenador having one")
    public void testCreateNewEntrenadorHavingOne(){
        
        Usuario usuario= new Usuario();
        usuario.fillFields();

        entrenador.fillFields();
        usuario.setEntrenador(entrenador);

        Entrenador newEntrenador= new Entrenador();

        // Act
		Map<String,Object> response= entrenadorService.createNewEntrenador(newEntrenador,usuario);
        List<String> errores= (List) response.get("errores");
        // Assert
        assertEquals(errores.get(0), "No puedes crear un perfil de entrenador si ya tenías uno previamente");
    }


    
    @Test
    @DisplayName("Test create new entrenador success")
    public void testCreateNewEntrenadorSuccess(){
        
        Usuario usuario= new Usuario();
        usuario.fillFields();

        entrenador.fillFields();

        Entrenador entrenadorWillSave= entrenador;
        entrenadorWillSave.setUsuario(usuario);
        

        LugarEntrenamiento lugarTest= new LugarEntrenamiento("Test Place", TipoLugar.MI_GIMNASIO, entrenadorWillSave);
        List<LugarEntrenamiento> listLugares= List.of(lugarTest);

        Entrenador entrenadorAtEnd= entrenadorWillSave;
        entrenadorAtEnd.setLugares(listLugares);

        Mockito.when(entrenadorRepository.save(entrenadorWillSave)).thenReturn(entrenadorWillSave);
        Mockito.when(lugarService.assignDefaultLugares(entrenadorWillSave)).thenReturn(entrenadorAtEnd);

        // Act
		Map<String,Object> response= entrenadorService.createNewEntrenador(entrenador,usuario);
        
        // Assert
        assertEquals((Entrenador) response.get("entrenador"), entrenadorAtEnd);
    }







    

}
