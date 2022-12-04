package com.MyRealTrainer.Repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.Servicio;
import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.repository.EntrenadorRepository;
import com.MyRealTrainer.repository.ServicioEntrenamientoRepository;
import com.MyRealTrainer.repository.UsuarioRepository;


@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest()
public class ServicioEntrenamientoRepositoryTests {
   @Autowired
   private ServicioEntrenamientoRepository servicioRepository;

   @Autowired
   private EntrenadorRepository entrenadorRepository;

   @Autowired
   private UsuarioRepository usuarioRepository;

   
   @Test
   public void testFindAll() {
      // ARRANGE
      Servicio servicio1= new Servicio();
      servicio1.fillFields();


      Servicio servicio2= new Servicio();
      servicio2.fillFields();
      servicio2.setTitulo("Another service");

      Usuario user1= new Usuario();
      user1.fillFields();
      user1= usuarioRepository.save(user1);

      Usuario user2= new Usuario();
      user2.fillFields();
      user2.setEmail("another@email.com");
      user2= usuarioRepository.save(user2);


      Entrenador entrenador1= new Entrenador();
      entrenador1.fillFields();
      entrenador1.setUsuario(user1);
      entrenador1=entrenadorRepository.save(entrenador1);

      Entrenador entrenador2= new Entrenador();
      entrenador2.fillFields();
      entrenador2.setUsuario(user2);
      entrenador2=entrenadorRepository.save(entrenador2);


      servicio1.setEntrenador(entrenador1);
      servicio1=servicioRepository.save(servicio1);
      
      servicio2.setEntrenador(entrenador2);
      servicio2=servicioRepository.save(servicio2);
     
      List<Servicio> result = new ArrayList<>();
      // ACT 
      servicioRepository.findMyServicios(entrenador1.getId()).forEach(e -> result.add(e));

      // ASSERT
      assertEquals(result.size(), 1);
      assertEquals(result.get(0).getTitulo(),servicio1.getTitulo());
      
      
   }
}