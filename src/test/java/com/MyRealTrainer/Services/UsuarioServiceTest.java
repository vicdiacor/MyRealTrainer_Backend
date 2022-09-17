package com.MyRealTrainer.Services;

import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;
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
        // ARRANGE
        Mockito.when(usuarioRepository.findAll()).thenReturn(List.of(this.usuario1,this.usuario2));
        // Act
		List<Usuario> allUsers = usuarioService.findAll();
        // Assert
		Assertions.assertEquals(2, allUsers.size(),"The list size must be 2");
    }

    @Test
    @DisplayName("Test find users by id")
    public void testFindById(){
        // ARRANGE
        Mockito.when(usuarioRepository.findById(this.usuario2.getId())).thenReturn(Optional.of(this.usuario2));

        // Act
		Optional<Usuario> user2 = usuarioService.findById(this.usuario2.getId());
        // Assert
		Assertions.assertEquals(user2.get(), this.usuario2,"The returned user isn´t the same");
    }

    @Test
    @DisplayName("Test find users by email")
    public void testFindByEmail(){
         // ARRANGE
         Mockito.when(usuarioRepository.findByEmail(this.usuario1.getEmail())).thenReturn(Optional.of(this.usuario1));

        // Act
		Optional<Usuario> user1 = usuarioService.findByEmail(this.usuario1.getEmail());
        // Assert
		Assertions.assertEquals(user1.get(), this.usuario1,"The returned user isn´t the same");
    }

    @Test
    @DisplayName("Test find users by nombre or email")
    public void testFindByNombreOrEmail(){
         // ARRANGE
         Mockito.when(usuarioRepository.findByNombreOrEmail(this.usuario1.getEmail(),this.usuario1.getEmail())).thenReturn(Optional.of(this.usuario1));

        // Act
		Usuario user1 = usuarioService.findByNameOrEmail(this.usuario1.getEmail(),this.usuario1.getEmail());
        // Assert
        Assertions.assertNotNull(user1);
		Assertions.assertEquals(user1, this.usuario1,"The returned user isn´t the same");
    }

    @Test
    @DisplayName("Test usuario exists by email")
    public void testExistsByEmail(){
         // ARRANGE
         Mockito.when(usuarioRepository.existsByEmail(this.usuario2.getEmail())).thenReturn(true);

        // Act
		Boolean user2Exists = usuarioService.existsByEmail(this.usuario2.getEmail());
        // Assert
      
		Assertions.assertEquals(user2Exists,true,"The user with id=2 doesn´t exist");
    }
    
    @Test
    @DisplayName("Test update usuario")
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

        Mockito.when(usuarioRepository.save(savedUsuario)).thenReturn(savedUsuario);

        // Act
		Usuario updatedSavedUsuario = usuarioService.updateUser(editedUsuario,this.usuario2);

        // Assert
		Assertions.assertEquals(updatedSavedUsuario,savedUsuario,"The user hasn´t the edited attributes");
    }

    @Test
    @DisplayName("Test save usuario")
    public void testSave(){
       

        Mockito.when(usuarioRepository.save(this.usuario2)).thenReturn(this.usuario2);

        // Act
		Usuario savedUsuario = usuarioService.save(usuario2);

        // Assert
		Assertions.assertEquals(savedUsuario,this.usuario2,"The saved user hasn´t the expected attributes");
    }

    

}
