package gov.dhs.uscis.odos.web.rest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import gov.dhs.uscis.odos.service.ConferenceRoomService;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomDTO;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceRoomResourceTest {

	private MockMvc restBuildingMockMvc;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Mock
	private ConferenceRoomService conferenceRoomService;

	@InjectMocks
	private ConferenceRoomResource conferenceRoomResource;

	@Before
	public void setup() {
		this.restBuildingMockMvc = MockMvcBuilders.standaloneSetup(conferenceRoomResource).build();
		ReflectionTestUtils.setField(conferenceRoomResource, "conferenceRoomService", conferenceRoomService);
	}

	@Test
	public void createBuilding_ShouldReturnHttpStatus201() throws Exception {
		ConferenceRoomDTO conferenceRoomDto = createConfRoomDTO(null, "test", "123");
		ConferenceRoomDTO conferenceRoomDtoResult = new ConferenceRoomDTO();
		conferenceRoomDtoResult.setConferenceRoomId(1L);
		when(conferenceRoomService.save(any(ConferenceRoomDTO.class))).thenReturn(conferenceRoomDtoResult);
		restBuildingMockMvc.perform(post("/api/conferenceroom").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(conferenceRoomDto))).andExpect(status().isCreated());

		ArgumentCaptor<ConferenceRoomDTO> dataCaptor = ArgumentCaptor.forClass(ConferenceRoomDTO.class);

		verify(conferenceRoomService, times(1)).save(dataCaptor.capture());
		verifyNoMoreInteractions(conferenceRoomService);

	}

	@Test
	public void shouldThorwBadRequestAlertException() throws Exception {
		exception.expect(NestedServletException.class);
		exception.expectMessage(
				"400, A new conference room cannot already have an ID, message=error.id exists, params=conferenceroom");
		ConferenceRoomDTO conferenceRoomDTO = new ConferenceRoomDTO();
		conferenceRoomDTO.setConferenceRoomId(1L);
		when(conferenceRoomService.save(any(ConferenceRoomDTO.class))).thenReturn(conferenceRoomDTO);
		restBuildingMockMvc.perform(post("/api/conferenceroom").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(conferenceRoomDTO))).andExpect(status().isCreated());
	}
	
	@Test
	public void shouldUpdateConferenceRoomWithExistingID() throws Exception {
		ConferenceRoomDTO conferenceRoomDto = createConfRoomDTO(1L, "test", "123");
		
		when(conferenceRoomService.save(any(ConferenceRoomDTO.class))).thenReturn(conferenceRoomDto);
		restBuildingMockMvc.perform(put("/api/conferenceroom").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(conferenceRoomDto))).andExpect(status().isOk());

		ArgumentCaptor<ConferenceRoomDTO> dataCaptor = ArgumentCaptor.forClass(ConferenceRoomDTO.class);

		verify(conferenceRoomService, times(1)).save(dataCaptor.capture());
		verifyNoMoreInteractions(conferenceRoomService);
	}
	
	@Test
	public void shouldUpdateConferenceRoomWithNewID() throws Exception {
		ConferenceRoomDTO conferenceRoomDto = createConfRoomDTO(null, "test", "123");
		
		ConferenceRoomDTO conferenceRoomDtoResult = new ConferenceRoomDTO();
		conferenceRoomDtoResult.setConferenceRoomId(1L);
		
		when(conferenceRoomService.save(any(ConferenceRoomDTO.class))).thenReturn(conferenceRoomDtoResult);
		restBuildingMockMvc.perform(put("/api/conferenceroom").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(conferenceRoomDto))).andExpect(status().isCreated());

		ArgumentCaptor<ConferenceRoomDTO> dataCaptor = ArgumentCaptor.forClass(ConferenceRoomDTO.class);

		verify(conferenceRoomService, times(1)).save(dataCaptor.capture());
		verifyNoMoreInteractions(conferenceRoomService);
	}
	
	@Test
	public void shouldRetrieveAllConferenceRooms() throws Exception {
		List<ConferenceRoomDTO> list = new ArrayList<ConferenceRoomDTO>();
		list.add(createConfRoomDTO(1L, "test", "123"));
		
		when(conferenceRoomService.findAll()).thenReturn(list);
		restBuildingMockMvc.perform(get("/api/conferenceroom").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(list))).andExpect(status().isOk());

		verify(conferenceRoomService, times(1)).findAll();
		verifyNoMoreInteractions(conferenceRoomService);
	}
	
	@Test
	public void shouldRetrieveConferenceRoomByID() throws Exception {
		ConferenceRoomDTO conferenceRoomDTO = createConfRoomDTO(1L, "test", "123");
		
		when(conferenceRoomService.findOne(1L)).thenReturn(conferenceRoomDTO);
		restBuildingMockMvc.perform(get("/api/conferenceroom/1").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(conferenceRoomDTO))).andExpect(status().isOk());

		ArgumentCaptor<Long> dataCaptor = ArgumentCaptor.forClass(Long.class);
		
		verify(conferenceRoomService, times(1)).findOne(dataCaptor.capture());
		verifyNoMoreInteractions(conferenceRoomService);
	}
	
	@Test
	public void shouldDeleteConferenceRoomByID() throws Exception {
		ConferenceRoomDTO conferenceRoomDTO = createConfRoomDTO(1L, "test", "123");
		
		restBuildingMockMvc.perform(delete("/api/conferenceroom/1").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(conferenceRoomDTO))).andExpect(status().isOk());

		ArgumentCaptor<Long> dataCaptor = ArgumentCaptor.forClass(Long.class);
		
		verify(conferenceRoomService, times(1)).delete(dataCaptor.capture());
		verifyNoMoreInteractions(conferenceRoomService);
	}
	
	private ConferenceRoomDTO createConfRoomDTO(Long id, String name, String num) {
		ConferenceRoomDTO conferenceRoomDto = new ConferenceRoomDTO();
		conferenceRoomDto.setRoomName(name);
		conferenceRoomDto.setRoomNum(num);
		conferenceRoomDto.setConferenceRoomId(id);
		
		return conferenceRoomDto;
	}
	 
}
