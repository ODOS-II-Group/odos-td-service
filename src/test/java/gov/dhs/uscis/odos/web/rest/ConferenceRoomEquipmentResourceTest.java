package gov.dhs.uscis.odos.web.rest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import gov.dhs.uscis.odos.service.ConferenceRoomEquipmentService;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomEquipmentDTO;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceRoomEquipmentResourceTest {

	private MockMvc restBuildingMockMvc;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Mock
	private ConferenceRoomEquipmentService conferenceRoomEquipService;

	@InjectMocks
	private ConferenceRoomEquipmentResource conferenceRoomEquipmentResource;

	@Before
	public void setup() {
		this.restBuildingMockMvc = MockMvcBuilders.standaloneSetup(conferenceRoomEquipmentResource).build();
		ReflectionTestUtils.setField(conferenceRoomEquipmentResource, "conferenceRoomEquipService", conferenceRoomEquipService);
	}

	@Test
	public void create_ShouldReturnHttpStatus201() throws Exception {
		ConferenceRoomEquipmentDTO conferenceRoomEquipmentDTO = createConfRoomEquipDTO(null);
		ConferenceRoomEquipmentDTO conferenceRoomEquipmentDTOResult = new ConferenceRoomEquipmentDTO();
		conferenceRoomEquipmentDTOResult.setConferenceRoomEquipId(1L);
		when(conferenceRoomEquipService.save(any(ConferenceRoomEquipmentDTO.class))).thenReturn(conferenceRoomEquipmentDTOResult);
		restBuildingMockMvc.perform(post("/api/conferenceroomequip").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(conferenceRoomEquipmentDTO))).andExpect(status().isCreated());

		ArgumentCaptor<ConferenceRoomEquipmentDTO> dataCaptor = ArgumentCaptor.forClass(ConferenceRoomEquipmentDTO.class);

		verify(conferenceRoomEquipService, times(1)).save(dataCaptor.capture());
		verifyNoMoreInteractions(conferenceRoomEquipService);

	}

	@Test
	public void shouldThorwBadRequestAlertException() throws Exception {
		exception.expect(NestedServletException.class);
		exception.expectMessage(
				"400, A new conferenceroomequip cannot already have an ID, message=error.id exists, params=conferenceroomequip");
		ConferenceRoomEquipmentDTO conferenceRoomEquipmentDTO = new ConferenceRoomEquipmentDTO();
		conferenceRoomEquipmentDTO.setConferenceRoomEquipId(1L);
		when(conferenceRoomEquipService.save(any(ConferenceRoomEquipmentDTO.class))).thenReturn(conferenceRoomEquipmentDTO);
		restBuildingMockMvc.perform(post("/api/conferenceroomequip").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(conferenceRoomEquipmentDTO))).andExpect(status().isCreated());
	}
	
	@Test
	public void shouldUpdateEquipmentWithExistingID() throws Exception {
		ConferenceRoomEquipmentDTO conferenceRoomEquipmentDTO = createConfRoomEquipDTO(1L);
		
		when(conferenceRoomEquipService.save(any(ConferenceRoomEquipmentDTO.class))).thenReturn(conferenceRoomEquipmentDTO);
		restBuildingMockMvc.perform(put("/api/conferenceroomequip").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(conferenceRoomEquipmentDTO))).andExpect(status().isOk());

		ArgumentCaptor<ConferenceRoomEquipmentDTO> dataCaptor = ArgumentCaptor.forClass(ConferenceRoomEquipmentDTO.class);

		verify(conferenceRoomEquipService, times(1)).save(dataCaptor.capture());
		verifyNoMoreInteractions(conferenceRoomEquipService);
	}
	
	@Test
	public void shouldUpdateEquipmentWithNewID() throws Exception {
		ConferenceRoomEquipmentDTO conferenceRoomEquipmentDTO = createConfRoomEquipDTO(null);
		
		ConferenceRoomEquipmentDTO conferenceRoomEquipmentDTOResult = new ConferenceRoomEquipmentDTO();
		conferenceRoomEquipmentDTOResult.setConferenceRoomEquipId(1L);
		
		when(conferenceRoomEquipService.save(any(ConferenceRoomEquipmentDTO.class))).thenReturn(conferenceRoomEquipmentDTOResult);
		restBuildingMockMvc.perform(put("/api/conferenceroomequip").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(conferenceRoomEquipmentDTO))).andExpect(status().isCreated());

		ArgumentCaptor<ConferenceRoomEquipmentDTO> dataCaptor = ArgumentCaptor.forClass(ConferenceRoomEquipmentDTO.class);

		verify(conferenceRoomEquipService, times(1)).save(dataCaptor.capture());
		verifyNoMoreInteractions(conferenceRoomEquipService);
	}
	
	@Test
	public void shouldRetrieveAllEquipments() throws Exception {
		List<ConferenceRoomEquipmentDTO> list = new ArrayList<ConferenceRoomEquipmentDTO>();
		list.add(createConfRoomEquipDTO(1L));
		
		when(conferenceRoomEquipService.findAll()).thenReturn(list);
		restBuildingMockMvc.perform(get("/api/conferenceroomequip").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(list))).andExpect(status().isOk());

		verify(conferenceRoomEquipService, times(1)).findAll();
		verifyNoMoreInteractions(conferenceRoomEquipService);
	}
	
	@Test
	public void shouldRetrieveEquipmentByID() throws Exception {
		ConferenceRoomEquipmentDTO conferenceRoomEquipmentDTO = createConfRoomEquipDTO(1L);
		
		when(conferenceRoomEquipService.findOne(1L)).thenReturn(conferenceRoomEquipmentDTO);
		restBuildingMockMvc.perform(get("/api/conferenceroomequip/1").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(conferenceRoomEquipmentDTO))).andExpect(status().isOk());

		ArgumentCaptor<Long> dataCaptor = ArgumentCaptor.forClass(Long.class);
		
		verify(conferenceRoomEquipService, times(1)).findOne(dataCaptor.capture());
		verifyNoMoreInteractions(conferenceRoomEquipService);
	}
	
	@Test
	public void shouldDeleteEquipmentByID() throws Exception {
		ConferenceRoomEquipmentDTO conferenceRoomEquipmentDTO = createConfRoomEquipDTO(1L);
		
		restBuildingMockMvc.perform(delete("/api/conferenceroomequip/1").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(conferenceRoomEquipmentDTO))).andExpect(status().isOk());

		ArgumentCaptor<Long> dataCaptor = ArgumentCaptor.forClass(Long.class);
		
		verify(conferenceRoomEquipService, times(1)).delete(dataCaptor.capture());
		verifyNoMoreInteractions(conferenceRoomEquipService);
	}
	
	private ConferenceRoomEquipmentDTO createConfRoomEquipDTO(Long id) {
		ConferenceRoomEquipmentDTO conferenceRoomEquipmentDTO = new ConferenceRoomEquipmentDTO();
		conferenceRoomEquipmentDTO.setConferenceRoomEquipId(id);
		
		return conferenceRoomEquipmentDTO;
	}
	 
}
