package gov.dhs.uscis.odos.web.rest.errors;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;
import org.zalando.problem.Status;

public class RestErrorsTest {

	@Test
	public void testErrorConstantsConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
	  Constructor<ErrorConstants> constructor = ErrorConstants.class.getDeclaredConstructor();
	  assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	  constructor.setAccessible(true);
	  constructor.newInstance();
	}
	
	@Test
	public void testInvalidPasswordException() {
		InvalidPasswordException ex = new InvalidPasswordException();
		assertTrue(ErrorConstants.INVALID_PASSWORD_TYPE.equals(ex.getType()));
		assertTrue("Incorrect password".equals(ex.getMessage()));
		assertTrue(Status.BAD_REQUEST.equals(ex.getStatus()));
	}
	
	@Test
	public void testInternalServerErrorException() {
		final String message = "Internal Server Error";
		InternalServerErrorException exception = new InternalServerErrorException(message);
		assertTrue(ErrorConstants.DEFAULT_TYPE.equals(exception.getType()));
		assertTrue(message.equals(exception.getMessage()));
		assertTrue(Status.INTERNAL_SERVER_ERROR.equals(exception.getStatus()));
	}
	
	@Test
	public void testEmailNotFoundException() {
		final String message = "Email address not registered";
		EmailNotFoundException exception = new EmailNotFoundException();
		assertTrue(ErrorConstants.EMAIL_NOT_FOUND_TYPE.equals(exception.getType()));
		assertTrue(message.equals(exception.getMessage()));
		assertTrue(Status.BAD_REQUEST.equals(exception.getStatus()));
	}
	
	@Test
	public void testLoginAlreadyUsedException() {
		final String message = "Login already in use";
		LoginAlreadyUsedException exception = new LoginAlreadyUsedException();
		assertTrue(ErrorConstants.LOGIN_ALREADY_USED_TYPE.equals(exception.getType()));
		assertTrue(message.equals(exception.getMessage()));
	}
	
	@Test
	public void testEmailAlreadyUsedException() {
		final String message = "Email address already in use";
		EmailAlreadyUsedException exception = new EmailAlreadyUsedException();
		assertTrue(ErrorConstants.EMAIL_ALREADY_USED_TYPE.equals(exception.getType()));
		assertTrue(message.equals(exception.getMessage()));
	}
	
}
