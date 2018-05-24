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

import gov.dhs.uscis.odos.service.EquipmentService;
import gov.dhs.uscis.odos.service.dto.EquipmentDTO;

@RunWith(MockitoJUnitRunner.class)
public class EquipmentResourceTest {

	private MockMvc restBuildingMockMvc;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Mock
	private EquipmentService equipmentService;

	@InjectMocks
	private EquipmentResource equipmentResource;

	@Before
	public void setup() {
		this.restBuildingMockMvc = MockMvcBuilders.standaloneSetup(equipmentResource).build();
		ReflectionTestUtils.setField(equipmentResource, "equipmentService", equipmentService);
	}

	@Test
	public void createEquipment_ShouldReturnHttpStatus201() throws Exception {
		EquipmentDTO equipmentDTO = createEquipDTO(null, "test", "123");
		EquipmentDTO equipmentDTOResult = new EquipmentDTO();
		equipmentDTOResult.setEquipmentId(1L);
		when(equipmentService.save(any(EquipmentDTO.class))).thenReturn(equipmentDTOResult);
		restBuildingMockMvc.perform(post("/api/equipment").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(equipmentDTO))).andExpect(status().isCreated());

		ArgumentCaptor<EquipmentDTO> dataCaptor = ArgumentCaptor.forClass(EquipmentDTO.class);

		verify(equipmentService, times(1)).save(dataCaptor.capture());
		verifyNoMoreInteractions(equipmentService);

	}

	@Test
	public void shouldThorwBadRequestAlertException() throws Exception {
		exception.expect(NestedServletException.class);
		exception.expectMessage(
				"400, A new equipment cannot already have an ID, message=error.id exists, params=equipment");
		EquipmentDTO equipmentDTO = new EquipmentDTO();
		equipmentDTO.setEquipmentId(1L);
		when(equipmentService.save(any(EquipmentDTO.class))).thenReturn(equipmentDTO);
		restBuildingMockMvc.perform(post("/api/equipment").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(equipmentDTO))).andExpect(status().isCreated());
	}
	
	@Test
	public void shouldUpdateEquipmentWithExistingID() throws Exception {
		EquipmentDTO equipmentDTO = createEquipDTO(1L, "test", "123");
		
		when(equipmentService.save(any(EquipmentDTO.class))).thenReturn(equipmentDTO);
		restBuildingMockMvc.perform(put("/api/equipment").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(equipmentDTO))).andExpect(status().isOk());

		ArgumentCaptor<EquipmentDTO> dataCaptor = ArgumentCaptor.forClass(EquipmentDTO.class);

		verify(equipmentService, times(1)).save(dataCaptor.capture());
		verifyNoMoreInteractions(equipmentService);
	}
	
	@Test
	public void shouldUpdateEquipmentWithNewID() throws Exception {
		EquipmentDTO equipmentDTO = createEquipDTO(null, "test", "123");
		
		EquipmentDTO equipmentDTOResult = new EquipmentDTO();
		equipmentDTOResult.setEquipmentId(1L);
		
		when(equipmentService.save(any(EquipmentDTO.class))).thenReturn(equipmentDTOResult);
		restBuildingMockMvc.perform(put("/api/equipment").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(equipmentDTO))).andExpect(status().isCreated());

		ArgumentCaptor<EquipmentDTO> dataCaptor = ArgumentCaptor.forClass(EquipmentDTO.class);

		verify(equipmentService, times(1)).save(dataCaptor.capture());
		verifyNoMoreInteractions(equipmentService);
	}
	
	@Test
	public void shouldRetrieveAllEquipments() throws Exception {
		List<EquipmentDTO> list = new ArrayList<EquipmentDTO>();
		list.add(createEquipDTO(1L, "test", "123"));
		
		when(equipmentService.findAll()).thenReturn(list);
		restBuildingMockMvc.perform(get("/api/equipment").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(list))).andExpect(status().isOk());

		verify(equipmentService, times(1)).findAll();
		verifyNoMoreInteractions(equipmentService);
	}
	
	@Test
	public void shouldRetrieveEquipmentByID() throws Exception {
		EquipmentDTO equipmentDTO = createEquipDTO(1L, "test", "123");
		
		when(equipmentService.findOne(1L)).thenReturn(equipmentDTO);
		restBuildingMockMvc.perform(get("/api/equipment/1").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(equipmentDTO))).andExpect(status().isOk());

		ArgumentCaptor<Long> dataCaptor = ArgumentCaptor.forClass(Long.class);
		
		verify(equipmentService, times(1)).findOne(dataCaptor.capture());
		verifyNoMoreInteractions(equipmentService);
	}
	
	@Test
	public void shouldDeleteEquipmentByID() throws Exception {
		EquipmentDTO equipmentDTO = createEquipDTO(1L, "test", "123");
		
		restBuildingMockMvc.perform(delete("/api/equipment/1").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(equipmentDTO))).andExpect(status().isOk());

		ArgumentCaptor<Long> dataCaptor = ArgumentCaptor.forClass(Long.class);
		
		verify(equipmentService, times(1)).delete(dataCaptor.capture());
		verifyNoMoreInteractions(equipmentService);
	}
	
	private EquipmentDTO createEquipDTO(Long id, String name, String desc) {
		EquipmentDTO equipmentDTO = new EquipmentDTO();
		equipmentDTO.setEquipmentId(id);
		equipmentDTO.setEquipmentName(name);
		equipmentDTO.setEquipmentDesc(desc);
		
		return equipmentDTO;
	}
	 
}
