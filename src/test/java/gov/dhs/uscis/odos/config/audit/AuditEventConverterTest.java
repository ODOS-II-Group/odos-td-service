package gov.dhs.uscis.odos.config.audit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import gov.dhs.uscis.odos.domain.PersistentAuditEvent;

@RunWith(MockitoJUnitRunner.class)
public class AuditEventConverterTest {
	
	@InjectMocks
	AuditEventConverter auditEventConverter;
	private PersistentAuditEvent persistentAuditEvent;
	
	 Iterable<PersistentAuditEvent> persistentAuditEvents;
	 Iterable<PersistentAuditEvent> persistentAuditEventNull;
	 List<PersistentAuditEvent> persistList = null;
	Map<String, Object> dataObject = new HashMap<>();
	
	@Mock
	WebAuthenticationDetails webAutenticationDetails;

	@Before
	public void setup() {
		persistentAuditEvent = new PersistentAuditEvent();
		 
		persistentAuditEvent.setAuditEventDate(Instant.now());
		persistentAuditEvent.setPrincipal("test");
		persistentAuditEvent.setAuditEventType("ODOS");
		dataObject.put("1", "ODOS");
		dataObject.put("2",webAutenticationDetails);
		dataObject.put("3",null );
		
		Map<String, String> data = new HashMap<>();
		data.put("1", "ODOS");
		persistentAuditEvent.setData(data);
		persistList = new ArrayList<>();
		persistList.add(persistentAuditEvent);
		persistentAuditEvents = new Iterable<PersistentAuditEvent>() {
			
			@Override
			public Iterator<PersistentAuditEvent> iterator() {
				// TODO Auto-generated method stub
				return persistList.iterator();
			}
		};
		 
	}
	@Test
	public void testConvertToAuditEvent() {
		
		List<AuditEvent> returnList = auditEventConverter.convertToAuditEvent(persistentAuditEvents);
		assertNotNull(returnList);
		
	}
	@Test
	public void testConvertToAuditEventNull() {
		
		List<AuditEvent> returnList =auditEventConverter.convertToAuditEvent(persistentAuditEventNull);
		assertEquals(0, returnList.size());
		
	}
	@Test
	public void testConvertDataToStrings() {
		
		Map<String, String> returnMap = auditEventConverter.convertDataToStrings(dataObject);
		assertNotNull(returnMap);
		
	}
}
