package gov.dhs.uscis.odos.web.rest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import gov.dhs.uscis.odos.service.BuildingService;
import gov.dhs.uscis.odos.service.dto.BuildingDTO;

@RunWith(MockitoJUnitRunner.class)
public class BuildingResourceTest {

	private MockMvc restBuildingMockMvc;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Mock
	private BuildingService buildingService;

	@InjectMocks
	private BuildingResource buildingResource;

	@Before
	public void setup() {
		this.restBuildingMockMvc = MockMvcBuilders.standaloneSetup(buildingResource).build();
		restBuildingMockMvc = MockMvcBuilders.standaloneSetup(buildingResource).build();
		ReflectionTestUtils.setField(buildingResource, "buildingService", buildingService);
	}

	@Test
	public void createBuilding_ShouldReturnHttpStatus201() throws Exception {
		BuildingDTO buildinDto = new BuildingDTO();
		BuildingDTO buildinDtorResult = new BuildingDTO();
		buildinDtorResult.setBuildingId(1L);
		when(buildingService.save(any(BuildingDTO.class))).thenReturn(buildinDtorResult);
		restBuildingMockMvc.perform(post("/api/buildings").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(buildinDto))).andExpect(status().isCreated());

		ArgumentCaptor<BuildingDTO> dataCaptor = ArgumentCaptor.forClass(BuildingDTO.class);

		verify(buildingService, times(1)).save(dataCaptor.capture());
		verifyNoMoreInteractions(buildingService);

	}

	@Test
	public void shouldThorwBadRequestAlertException() throws Exception {
		exception.expect(NestedServletException.class);
		exception.expectMessage(
				"400, A new building cannot already have an ID, message=error.id exists, params=building");
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setBuildingId(1L);
		when(buildingService.save(any(BuildingDTO.class))).thenReturn(buildingDTO);
		restBuildingMockMvc.perform(post("/api/buildings").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(buildingDTO))).andExpect(status().isCreated());
	}
}
