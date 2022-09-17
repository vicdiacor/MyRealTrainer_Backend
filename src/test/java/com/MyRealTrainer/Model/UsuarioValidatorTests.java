package com.MyRealTrainer.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.MyRealTrainer.model.Role;
import com.MyRealTrainer.model.Usuario;
import com.MyRealTrainer.service.UtilService;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioValidatorTests {

	Usuario defaultUsuario;

	
	UtilService utilService;
	
	@BeforeAll
	void init(){
		utilService= new UtilService();
		defaultUsuario= new Usuario();
		defaultUsuario.setEmail("testing@email.com");
		defaultUsuario.setNombre("Testing name");
		defaultUsuario.setApellidos("Testing apellidos");
		Date fechaNacimiento= new Date();
		fechaNacimiento= utilService.addDate(fechaNacimiento, Calendar.YEAR, -19);
		defaultUsuario.setFechaNacimiento(fechaNacimiento);
		defaultUsuario.setLocalidad("Sevilla");
		defaultUsuario.setPassword("No-encoded-password");
		Role role_cliente= new Role();
		role_cliente.setName("ROLE_CLIENTE");
		role_cliente.setId(2l);
		defaultUsuario.setRoles(Set.of(role_cliente));
		

	}

	@Test
	@DisplayName("Validation failure when 'nombre' is empty")
	void testNotBlankNombre() {
		
		//ARRANGE
		Usuario invalidUsuario= this.defaultUsuario;
		invalidUsuario.setNombre("");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Usuario>> c = v.validateProperty(invalidUsuario, "nombre");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Usuario> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("no debe estar vacío");
	}

	@Test
	@DisplayName("Validation failure when 'nombre' lenght is more than 25")
	void testNombreMaxSize25() {
		
		//ARRANGE
		Usuario invalidUsuario= this.defaultUsuario;
		invalidUsuario.setNombre("This text has more than 25 characters");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Usuario>> c = v.validateProperty(invalidUsuario, "nombre");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Usuario> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 25");
	}

	@Test
	@DisplayName("Validation failure when 'email' is blank")
	void testNotBlankEmail() {
		
		//ARRANGE
		Usuario invalidUsuario= this.defaultUsuario;
		invalidUsuario.setEmail("");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Usuario>> c = v.validateProperty(invalidUsuario, "email");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Usuario> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("no debe estar vacío");
	}

	@Test
	@DisplayName("Validation failure when 'email' has more than 320 characters")
	void testEmailMaxSize320() {
		
		//ARRANGE
		Usuario invalidUsuario= this.defaultUsuario;
		invalidUsuario.setEmail("thisEmailHaveMoreThan320characters@emailaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.com");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Usuario>> c = v.validateProperty(invalidUsuario, "email");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(2);
		Iterator<ConstraintViolation<Usuario>> errorIterator = c.iterator();
		assertThat(errorIterator.next().getMessage()).isEqualTo("debe ser una dirección de correo electrónico con formato correcto");
		assertThat(errorIterator.next().getMessage()).isEqualTo("el tamaño debe estar entre 0 y 320");
	}

	@Test
	@DisplayName("Validation failure when 'email' isn´t in the email-format")
	void testEmailIncorrectFormat() {
		
		//ARRANGE
		Usuario invalidUsuario= this.defaultUsuario;
		invalidUsuario.setEmail("no-email-format");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Usuario>> c = v.validateProperty(invalidUsuario, "email");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Usuario> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("debe ser una dirección de correo electrónico con formato correcto");
	}

	@Test
	@DisplayName("Validation failure when 'apellidos' is blank")
	void testNotBlankApellidos() {
		
		//ARRANGE
		Usuario invalidUsuario= this.defaultUsuario;
		invalidUsuario.setApellidos("");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Usuario>> c = v.validateProperty(invalidUsuario, "apellidos");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Usuario> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("no debe estar vacío");
	}

	@Test
	@DisplayName("Validation failure when 'apellidos' has more than 50 characters")
	void testApellidosMaxSize50() {
		
		//ARRANGE
		Usuario invalidUsuario= this.defaultUsuario;
		invalidUsuario.setApellidos("This string has more than 50 characters______________");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Usuario>> c = v.validateProperty(invalidUsuario, "apellidos");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Usuario> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 50");
	}

	@Test
	@DisplayName("Validation failure when 'localidad' is blank")
	void testNotBlankLocalidad() {
		
		//ARRANGE
		Usuario invalidUsuario= this.defaultUsuario;
		invalidUsuario.setLocalidad("");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Usuario>> c = v.validateProperty(invalidUsuario, "localidad");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Usuario> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("no debe estar vacío");
	}

	@Test
	@DisplayName("Validation failure when 'localidad' has more than 100 characters")
	void testLocalidadMaxSize100() {
		
		//ARRANGE
		Usuario invalidUsuario= this.defaultUsuario;
		invalidUsuario.setLocalidad("This string has more than 100 characters_aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Usuario>> c = v.validateProperty(invalidUsuario, "localidad");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Usuario> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("el tamaño debe estar entre 0 y 100");
	}

	@Test
	@DisplayName("Validation failure when 'fechaNacimiento' is null")
	void testFechaNacimientoNonNull() {
		
		//ARRANGE
		Usuario invalidUsuario= this.defaultUsuario;
		invalidUsuario.setFechaNacimiento(null);
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Usuario>> c = v.validateProperty(invalidUsuario, "fechaNacimiento");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Usuario> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("no debe ser nulo");
	}

	@Test
	@DisplayName("Validation failure when 'fechaNacimiento' is in the future")
	void testFechaNacimientoFuture() {
		
		//ARRANGE
		Usuario invalidUsuario= this.defaultUsuario;
		Date futureDate= new Date();
		futureDate=utilService.addDate(futureDate, Calendar.DATE, 1);

		invalidUsuario.setFechaNacimiento(futureDate);
		
		//ACT
		Validator v = UtilService.createValidator();
		Set<ConstraintViolation<Usuario>> c = v.validateProperty(invalidUsuario, "fechaNacimiento");
		
		//ASSERT
		assertThat(c.size()).isEqualTo(1);
		ConstraintViolation<Usuario> con = c.iterator().next();
		assertThat(con.getMessage()).isEqualTo("debe ser una fecha pasada");
	}

	



	

	
	
	
	
}
