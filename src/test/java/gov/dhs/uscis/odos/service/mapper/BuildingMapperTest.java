package gov.dhs.uscis.odos.service.mapper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import gov.dhs.uscis.odos.domain.Building;
import gov.dhs.uscis.odos.service.ConferenceRoomService;
import gov.dhs.uscis.odos.service.dto.BuildingDTO;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomDTO;

@RunWith(MockitoJUnitRunner.class)
public class BuildingMapperTest {
	
	private Building building;
	private BuildingDTO buildingDTO;
		
	
	@InjectMocks
	BuildingMapper buildingMapper;
	@Mock
	private ConferenceRoomService conferenceRoomService;
	private List<ConferenceRoomDTO> conferenceRooms;

	@Mock
	private Mapper mapper;

	@Before
	public void setup() {
		buildingDTO = new BuildingDTO();
		building = new Building();
		conferenceRooms = new ArrayList<ConferenceRoomDTO>();
		Mockito.when(mapper.map(building, BuildingDTO.class)).thenReturn(buildingDTO);
		Mockito.when(mapper.map(buildingDTO, Building.class)).thenReturn(building);
		Mockito.when(conferenceRoomService.findAll()).thenReturn(conferenceRooms);
		
	}	

	@Test
	public void testToEntity() {
		assertNotNull(buildingMapper.toEntity(buildingDTO));
	}

	@Test
	public void testToDto() {
		BuildingDTO dtoReturn=  buildingMapper.toDto(building);
		assertEquals(buildingDTO, dtoReturn);
		assertEquals(buildingDTO.getConferenceRooms(), conferenceRooms);
		
	}
	@Test
	public void testToDtoforNull() {
		Building entity = null;
		BuildingDTO dtoReturn=  buildingMapper.toDto(entity);
		assertNull(dtoReturn);
		
	}


	@Test
	public void testToEntityFromList() {
		List<BuildingDTO> entityList = new ArrayList<BuildingDTO>();
		entityList.add(buildingDTO);
		List<Building> dtoList=  buildingMapper.toEntity(entityList);
		assertEquals(dtoList.size(), 1);
	}


	@Test
	public void testToDtoFromList() {
		List<Building> entityList = new ArrayList<Building>();
		entityList.add(building);
		List<BuildingDTO> dtoList=  buildingMapper.toDto(entityList);
		assertEquals(dtoList.size(), 1);
	}

}
