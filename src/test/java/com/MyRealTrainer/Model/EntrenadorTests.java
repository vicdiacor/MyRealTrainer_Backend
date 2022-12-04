package com.MyRealTrainer.Model;

import com.MyRealTrainer.model.Entrenador;
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
public class EntrenadorTests {

	private Entrenador defaultEntrenador;

	
	@BeforeAll
	void init(){
	
		defaultEntrenador= new Entrenador();
		defaultEntrenador.fillFields();
		
	}

	@Test
	@Order(1)
	@DisplayName("Validation success")
	void testSuccess() {
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Entrenador>> c = v.validate(defaultEntrenador);
		
		//ASSERT
		assertThat(c.size()).isEqualTo(0);
	}
	
	@Test
	@DisplayName("Validation failure when 'formacion' is blank")
	void testNotBlankFormacion() {
		
		//ARRANGE
	
		defaultEntrenador.setFormacion("");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Entrenador>> c = v.validateProperty(defaultEntrenador, "formacion");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Entrenador> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("no debe estar vacío");
	}

	@Test
	@DisplayName("Validation failure when 'formacion' has more than 400 characters")
	void testMax400Formacion() {
		
		//ARRANGE
	
		defaultEntrenador.setFormacion("This text contains 401 characters: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Entrenador>> c = v.validateProperty(defaultEntrenador, "formacion");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Entrenador> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 400");
	}

	@Test
	@DisplayName("Validation failure when 'descripcionSobreMi' has more than 500 characters")
	void testMax500DescripcionSobreMi() {
		
		//ARRANGE
	
		defaultEntrenador.setDescripcionSobreMi("This text contains 501 characters: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Entrenador>> c = v.validateProperty(defaultEntrenador, "descripcionSobreMi");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Entrenador> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 500");
	}

	@Test
	@DisplayName("Validation failure when 'descripcionExperiencia' has more than 500 characters")
	void testMax500DescripcionExperiencia() {
		
		//ARRANGE
	
		defaultEntrenador.setDescripcionExperiencia("This text contains 501 characters: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Entrenador>> c = v.validateProperty(defaultEntrenador, "descripcionExperiencia");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Entrenador> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 500");
	}

}
