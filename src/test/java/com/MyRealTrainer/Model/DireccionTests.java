package com.MyRealTrainer.Model;



import com.MyRealTrainer.model.Direccion;

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
public class DireccionTests {

	private Direccion defaultDireccion;

	
	@BeforeAll
	void init(){

		defaultDireccion= new Direccion();
		defaultDireccion.fillFields();

	}

	@Test
	@Order(1)
	@DisplayName("Validation success")
	void testSuccess() {
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Direccion>> c = v.validate(defaultDireccion);
		
		//ASSERT
		assertThat(c.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("Validation failure when 'calle' has more than 100 characters")
	void testMax100Calle() {
		
		//ARRANGE
	
		defaultDireccion.setCalle("This text contains 101 characters: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Direccion>> c = v.validateProperty(defaultDireccion, "calle");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Direccion> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 100");
	}

	@Test
	@DisplayName("Validation failure when 'numero' has more than 5 characters")
	void testMax5Numero() {
		
		//ARRANGE
	
		defaultDireccion.setNumero("123456");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Direccion>> c = v.validateProperty(defaultDireccion, "numero");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Direccion> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 5");
	}

	
	@Test
	@DisplayName("Validation failure when 'piso' has more than 5 characters")
	void testMax5Piso() {
		
		//ARRANGE
	
		defaultDireccion.setPiso("123456");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Direccion>> c = v.validateProperty(defaultDireccion, "piso");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Direccion> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 5");
	}
	
	@Test
	@DisplayName("Validation failure when 'ciudad' has more than 100 characters")
	void testMax100Ciudad() {
		
		//ARRANGE
	
		defaultDireccion.setCiudad("This text contains 101 characters: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Direccion>> c = v.validateProperty(defaultDireccion, "ciudad");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Direccion> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 100");
	}

	@Test
	@DisplayName("Validation failure when 'provincia' has more than 100 characters")
	void testMax100Provincia() {
		
		//ARRANGE
	
		defaultDireccion.setProvincia("This text contains 101 characters: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Direccion>> c = v.validateProperty(defaultDireccion, "provincia");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Direccion> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 100");
	}

	@Test
	@DisplayName("Validation failure when 'codigoPostal' contains more than 5 digits")
	void testMoreThan5CodigoPostal() {
		
		//ARRANGE
	
		defaultDireccion.setCodigoPostal(123456);
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Direccion>> c = v.validateProperty(defaultDireccion, "codigoPostal");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Direccion> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("valor numérico fuera de límites (se esperaba <5 dígitos>.<0 dígitos>)");
	}

	

	


	
}
