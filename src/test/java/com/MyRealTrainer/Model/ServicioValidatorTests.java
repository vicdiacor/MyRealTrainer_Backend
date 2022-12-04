package com.MyRealTrainer.Model;



import com.MyRealTrainer.model.Servicio;

import com.MyRealTrainer.service.UtilService;

import static org.assertj.core.api.Assertions.assertThat;


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
public class ServicioValidatorTests {

	private Servicio defaultServicio;

	
	
	@BeforeAll
	void init(){

		// Servicio
		defaultServicio= new Servicio();
		defaultServicio.fillFields();
	
		
		
	}

	@Test
	@Order(1)
	@DisplayName("Validation success")
	void testNotBlankNombre() {
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Servicio>> c = v.validate(defaultServicio);
		
		//ASSERT
		assertThat(c.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("Validation failure when 'titulo' is empty")
	void testNotBlankTitulo() {
		
		//ARRANGE
	
		defaultServicio.setTitulo("");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Servicio>> c = v.validateProperty(defaultServicio, "titulo");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Servicio> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("no debe estar vacío");
	}

	
	@Test
	@DisplayName("Validation failure when 'titulo' has more than 80 characters")
	void testMax80Titulo() {
		
		//ARRANGE
	
		defaultServicio.setTitulo("This title has  81 characters: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Servicio>> c = v.validateProperty(defaultServicio, "titulo");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Servicio> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 80");
	}

	@Test
	@DisplayName("Validation failure when 'descripcion' is empty")
	void testNotBlankDescripcion() {
		
		//ARRANGE
	
		defaultServicio.setDescripcion("");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Servicio>> c = v.validateProperty(defaultServicio, "descripcion");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Servicio> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("no debe estar vacío");
	}
	
	@Test
	@DisplayName("Validation failure when 'descripcion' has more than 500 characters")
	void testMax500Descripcion() {
		
		//ARRANGE
	
		defaultServicio.setDescripcion("This description has 501 characters:aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Servicio>> c = v.validateProperty(defaultServicio, "descripcion");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Servicio> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 500");
	}


	

	



	

	
	
	
	
}
