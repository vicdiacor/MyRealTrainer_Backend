package com.MyRealTrainer.Model;



import com.MyRealTrainer.model.LugarEntrenamiento;
import com.MyRealTrainer.model.TipoLugar;
import com.MyRealTrainer.service.UtilService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;


@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LugarEntrenamientoTests {

	private LugarEntrenamiento defaultLugar;

	
	@BeforeAll
	void init(){
	
		defaultLugar= new LugarEntrenamiento();
		defaultLugar.fillFields();
		
	}

	@Test
	@Order(1)
	@DisplayName("Validation success")
	void testSuccess() {
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<LugarEntrenamiento>> c = v.validate(defaultLugar);
		
		//ASSERT
		assertThat(c.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("Validation failure when 'titulo' is empty")
	void testNotBlankTitulo() {
		
		//ARRANGE
	
		defaultLugar.setTitulo("");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<LugarEntrenamiento>> c = v.validateProperty(defaultLugar, "titulo");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<LugarEntrenamiento> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("no debe estar vacío");
	}

	
	@Test
	@DisplayName("Validation failure when 'titulo' has more than 80 characters")
	void testMax80Titulo() {
		
		//ARRANGE
	
		defaultLugar.setTitulo("This title has  81 characters: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<LugarEntrenamiento>> c = v.validateProperty(defaultLugar, "titulo");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<LugarEntrenamiento> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 80");
	}

	@Test
	@DisplayName("Validation failure when 'description' contains more than 300 characters")
	void testMax300Description() {
		
		//ARRANGE
	
		defaultLugar.setDescripcion("This description contains 301 characters: awdaaawdawdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<LugarEntrenamiento>> c = v.validateProperty(defaultLugar, "descripcion");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<LugarEntrenamiento> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 300");
	}

	@Test
	@DisplayName("Validation failure when 'tipoLugar' is null")
	void testNotNullTipoLugar() {
		
		//ARRANGE
	
		defaultLugar.setTipoLugar(null);
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<LugarEntrenamiento>> c = v.validateProperty(defaultLugar, "tipoLugar");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<LugarEntrenamiento> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("no debe ser nulo");
	}

	@Test
	@DisplayName("Validation failure when 'tipoLugar' isn´t a TipoLugar.enumerate type")
	void testNotEnumeratedTipoDuracion() {
		
		// ACT
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			defaultLugar.setTipoLugar(TipoLugar.valueOf("NewType"));
		});
		
		// ASSERT
		assertEquals(exception.getMessage(), "No enum constant com.MyRealTrainer.model.TipoLugar.NewType");

	}


	
}
