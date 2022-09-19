package com.MyRealTrainer.Model;



import com.MyRealTrainer.model.Tarifa;
import com.MyRealTrainer.model.TipoDuracion;
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
public class TarifaValidatorTests {

	private Tarifa defaultTarifa;

	
	
	@BeforeAll
	void init(){

		defaultTarifa= new Tarifa();
		defaultTarifa.fillFields();
		
	}

	@Test
	@Order(1)
	@DisplayName("Validation success")
	void testSuccess() {
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Tarifa>> c = v.validate(defaultTarifa);
		
		//ASSERT
		assertThat(c.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("Validation failure when 'titulo' is empty")
	void testNotBlankTitulo() {
		
		//ARRANGE
	
		defaultTarifa.setTitulo("");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Tarifa>> c = v.validateProperty(defaultTarifa, "titulo");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Tarifa> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("no debe estar vacío");
	}

	
	@Test
	@DisplayName("Validation failure when 'titulo' has more than 80 characters")
	void testMax80Titulo() {
		
		//ARRANGE
	
		defaultTarifa.setTitulo("This title has  81 characters: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Tarifa>> c = v.validateProperty(defaultTarifa, "titulo");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Tarifa> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 80");
	}

	@Test
	@DisplayName("Validation failure when 'precio' is null")
	void testNotNullPrecio() {
		
		//ARRANGE
	
		defaultTarifa.setPrecio(null);
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Tarifa>> c = v.validateProperty(defaultTarifa, "precio");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Tarifa> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("no debe ser nulo");
	}

	@Test
	@DisplayName("Validation failure when 'precio' is negative")
	void testNotNegativePrecio() {
		
		//ARRANGE
	
		defaultTarifa.setPrecio(-1.0);
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Tarifa>> c = v.validateProperty(defaultTarifa, "precio");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Tarifa> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("debe ser mayor que o igual a 0");
	}
	

	@Test
	@DisplayName("Validation failure when 'precio' has more than 2 decimals")
	void testPrecioMoreThan2Fraction() {
		
		//ARRANGE
	
		defaultTarifa.setPrecio(1.123);
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Tarifa>> c = v.validateProperty(defaultTarifa, "precio");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Tarifa> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("valor numérico fuera de límites (se esperaba <5 dígitos>.<2 dígitos>)");
	}

	@Test
	@DisplayName("Validation failure when 'precio' has more than 5 integer digits")
	void testPrecioMoreThan5Digits() {
		
		//ARRANGE
	
		defaultTarifa.setPrecio(123456.0);
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Tarifa>> c = v.validateProperty(defaultTarifa, "precio");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Tarifa> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("valor numérico fuera de límites (se esperaba <5 dígitos>.<2 dígitos>)");
	}

	
	@Test
	@DisplayName("Validation failure when 'tipoDuracion' is null")
	void testNotNullTipoDuracion() {
		
		//ARRANGE
	
		defaultTarifa.setTipoDuracion(null);
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Tarifa>> c = v.validateProperty(defaultTarifa, "tipoDuracion");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Tarifa> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("no debe ser nulo");
	}

	@Test
	@DisplayName("Validation failure when 'tipoDuracion' isn´t a TipoDuracion.enumerate type")
	void testNotEnumeratedTipoDuracion() {
		
		// ACT
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			defaultTarifa.setTipoDuracion(TipoDuracion.valueOf("NewType"));
		});
		
		// ASSERT
		assertEquals(exception.getMessage(), "No enum constant com.MyRealTrainer.model.TipoDuracion.NewType");

	}

	@Test
	@DisplayName("Validation failure when 'duracion' is null")
	void testNotNullDuracion() {
		
		//ARRANGE
	
		defaultTarifa.setDuracion(null);
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Tarifa>> c = v.validateProperty(defaultTarifa, "duracion");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Tarifa> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("no debe ser nulo");
	}
	
	@Test
	@DisplayName("Validation failure when 'duracion' is negative")
	void testNotNegativeDuracion() {
		
		//ARRANGE
	
		defaultTarifa.setDuracion(-1.0);
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Tarifa>> c = v.validateProperty(defaultTarifa, "duracion");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Tarifa> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("debe ser mayor que o igual a 0");
	}

	@Test
	@DisplayName("Validation failure when 'limitaciones' has more than 500 characters")
	void testMax500Limitaciones() {
		
		//ARRANGE
	
		defaultTarifa.setLimitaciones("This text contains 501 characters:aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Tarifa>> c = v.validateProperty(defaultTarifa, "limitaciones");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Tarifa> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 500");
	}
}
