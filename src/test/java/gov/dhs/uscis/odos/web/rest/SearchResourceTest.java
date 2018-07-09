package gov.dhs.uscis.odos.web.rest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import gov.dhs.uscis.odos.service.BuildingService;
import gov.dhs.uscis.odos.service.ConferenceRoomScheduleService;
import gov.dhs.uscis.odos.service.dto.BuildingDTO;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomScheduleDTO;

@RunWith(MockitoJUnitRunner.class)
public class SearchResourceTest {

	private MockMvc restBuildingMockMvc;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Mock
	private BuildingService buildingService;
	
	@Mock
	private ConferenceRoomScheduleService crsService;

	@InjectMocks
	private SearchResource searchResource;

	@Before
	public void setup() {
		this.restBuildingMockMvc = MockMvcBuilders.standaloneSetup(searchResource).build();
		ReflectionTestUtils.setField(searchResource, "buildingService", buildingService);
		ReflectionTestUtils.setField(searchResource, "crsService", crsService);
	}

	@Test
	public void testSearchBuildingByName() throws Exception {
		List<BuildingDTO> list = new ArrayList<BuildingDTO>();
		list.add(createBuildingDTO(1L, "Test", "Test Building"));
		when(buildingService.findByName("Test")).thenReturn(list);
		
		restBuildingMockMvc.perform(get("/api/search/building/Test").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(list))).andExpect(status().isOk());

		verify(buildingService, times(1)).findByName("Test");
		verifyNoMoreInteractions(buildingService);
	}

	@Test
	public void testConfRoomScheduleByRequestor() throws Exception {
		List<ConferenceRoomScheduleDTO> list = new ArrayList<ConferenceRoomScheduleDTO>();
		list.add(new ConferenceRoomScheduleDTO());
		when(crsService.findByRequestId("Test")).thenReturn(list);
		
		restBuildingMockMvc.perform(get("/api/search/user/Test").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(list))).andExpect(status().isOk());

		verify(crsService, times(1)).findByRequestId("Test");
		verifyNoMoreInteractions(crsService);
	}


	private BuildingDTO createBuildingDTO(Long id, String name, String desc) {
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setBuildingId(id);
		buildingDTO.setBuildingName(name);
		buildingDTO.setBuildingDesc(desc);
		
		return buildingDTO;
	}
	
}
