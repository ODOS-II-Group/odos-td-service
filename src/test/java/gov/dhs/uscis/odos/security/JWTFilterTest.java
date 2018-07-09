package gov.dhs.uscis.odos.security;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import gov.dhs.uscis.odos.security.jwt.JWTConfigurer;
import gov.dhs.uscis.odos.security.jwt.JWTFilter;
import gov.dhs.uscis.odos.security.jwt.TokenProvider;

@RunWith(MockitoJUnitRunner.class)
public class JWTFilterTest {

	@Mock
	private TokenProvider tokenProvider;
	
	@InjectMocks
	private JWTFilter jwtFilter;
	
	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Mock
	private FilterChain filterChain;

	private static final String BEARER_TOKEN = "Bearer Test Token";
	private static final String TOKEN = "Test Token";
	
	@Test
	public void testDoFilter() throws Exception {
		Mockito.when(request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER)).thenReturn(BEARER_TOKEN);
		Mockito.when(tokenProvider.validateToken(TOKEN)).thenReturn(Boolean.TRUE);
		Mockito.when(tokenProvider.getAuthentication(TOKEN)).thenReturn(null);
		
		jwtFilter.doFilter(request, response, filterChain);
	}

	@Test
	public void testDoFilterNullToken() throws Exception {
		Mockito.when(request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER)).thenReturn(null);
		
		jwtFilter.doFilter(request, response, filterChain);
	}
	
	@Test
	public void testDoFilterNonBearerToken() throws Exception {
		Mockito.when(request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER)).thenReturn("TEST");
		
		jwtFilter.doFilter(request, response, filterChain);
	}
	
	@Test
	public void testDoFilterInvalidToken() throws Exception {
		Mockito.when(request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER)).thenReturn(BEARER_TOKEN);
		Mockito.when(tokenProvider.validateToken(TOKEN)).thenReturn(Boolean.FALSE);
		jwtFilter.doFilter(request, response, filterChain);
	}
	
}
