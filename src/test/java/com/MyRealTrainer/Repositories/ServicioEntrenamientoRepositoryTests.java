package com.MyRealTrainer.Repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.MyRealTrainer.model.Entrenador;
import com.MyRealTrainer.model.Servicio;
import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.repository.EntrenadorRepository;
import com.MyRealTrainer.repository.ServicioEntrenamientoRepository;
import com.MyRealTrainer.repository.UsuarioRepository;

@ActiveProfiles("test") 
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
      Servicio servicio= new Servicio();
      servicio.fillFields();

      Usuario user= new Usuario();
      user.fillFields();
      user= usuarioRepository.save(user);

      Entrenador entrenador= new Entrenador();
      entrenador.fillFields();
      entrenador.setUsuario(user);
      entrenador=entrenadorRepository.save(entrenador);

      servicio.setEntrenador(entrenador);
      servicio=servicioRepository.save(servicio);
     
      List<Servicio> result = new ArrayList<>();
      // ACT 
      servicioRepository.findMyServicios(entrenador.getId()).forEach(e -> result.add(e));

      // ASSERT
      assertEquals(result.size(), 1);
      assertEquals(result.get(0).getTitulo(),servicio.getTitulo());
      
      
   }
}