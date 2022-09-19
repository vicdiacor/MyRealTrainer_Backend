package com.MyRealTrainer.Services;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.repository.UsuarioRepository;
import com.MyRealTrainer.service.UsuarioService;

@SpringBootTest 
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioServiceTest {
    @Autowired
	private UsuarioService usuarioService;

	@MockBean
	private UsuarioRepository usuarioRepository;

    private Usuario usuario1;

    private Usuario usuario2;

    @BeforeAll
    public  void init(){
        // Arrange

        Usuario user1 = new Usuario(1l);
        user1.setEmail("user1@email.com");
        user1.setNombre("Nombre 1");
        Usuario user2 = new Usuario(2l);
        user2.setEmail("user2@email.com");
        user2.setNombre("Nombre 2");
        this.usuario1=user1;
        this.usuario2=user2;
    }

    @Test
    @DisplayName("Test find all users")
    public void testFindAll(){
        // Act
		List<Usuario> allUsers = usuarioService.findAll();
        // Assert
        Mockito.verify(usuarioRepository, Mockito.times(1)).findAll();

    }

    @Test
    @DisplayName("Test find users by id")
    public void testFindById(){
        // Act
		Optional<Usuario> user2 = usuarioService.findById(this.usuario2.getId());
        // Assert
        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(this.usuario2.getId());

    }

    @Test
    @DisplayName("Test find users by email")
    public void testFindByEmail(){
        
        // Act
		Optional<Usuario> user1 = usuarioService.findByEmail(this.usuario1.getEmail());
        // Assert
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByEmail(this.usuario1.getEmail());
    }

    @Test
    @DisplayName("Test find users by nombre or email")
    public void testFindByNombreOrEmail(){
      
        // Act
		Usuario user1 = usuarioService.findByNameOrEmail(this.usuario1.getEmail(),this.usuario1.getEmail());
        // Assert
     
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByNombreOrEmail(this.usuario1.getEmail(),this.usuario1.getEmail());
    }

    @Test
    @DisplayName("Test usuario exists by email")
    public void testExistsByEmail(){

        // Act
		Boolean user2Exists = usuarioService.existsByEmail(this.usuario2.getEmail());
        // Assert
      
        Mockito.verify(usuarioRepository, Mockito.times(1)).existsByEmail(this.usuario2.getEmail());
    }
    
    @Test
    @DisplayName("Test update usuario ignoring the 'id' field")
    public void testUpdate(){
         // Arrange
        Usuario editedUsuario= this.usuario2;
        editedUsuario.setEmail("newEmail@email.com");
        editedUsuario.setNombre("New nombre");
        editedUsuario.setApellidos("New apellidos");
        editedUsuario.setId(10l); // Id cant change

        Usuario savedUsuario= this.usuario2;
        savedUsuario.setEmail("newEmail@email.com");
        savedUsuario.setNombre("New nombre");
        savedUsuario.setApellidos("New apellidos");
        savedUsuario.setId(2l); // Original id

       

        // Act
		Usuario updatedSavedUsuario = usuarioService.updateUser(editedUsuario,this.usuario2);

        // Assert
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(savedUsuario);
    }

    @Test
    @DisplayName("Test save usuario")
    public void testSave(){
       
        // Act
		Usuario savedUsuario = usuarioService.save(usuario2);

        // Assert
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(this.usuario2);
    }

    @Test
    @DisplayName("Test delete usuario by id")
    public void testDeleteById(){
      
        // Act
		usuarioService.deleteById(this.usuario1.getId());
        // Assert
     
        Mockito.verify(usuarioRepository, Mockito.times(1)).deleteById(this.usuario1.getId());
    }
    

}
