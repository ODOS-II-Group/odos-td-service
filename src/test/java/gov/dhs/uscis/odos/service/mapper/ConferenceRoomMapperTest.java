package gov.dhs.uscis.odos.service.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import gov.dhs.uscis.odos.domain.ConferenceRoom;
import gov.dhs.uscis.odos.domain.ConferenceRoomEquipment;
import gov.dhs.uscis.odos.domain.Equipment;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomDTO;
import gov.dhs.uscis.odos.service.dto.EquipmentDTO;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceRoomMapperTest {
	
	private ConferenceRoom conferenceRoom;
	private Building building;
	private ConferenceRoomDTO conferenceRoomDTO;
	private ConferenceRoomEquipment conferenceRoomEquipment;
	private List<ConferenceRoomEquipment> conferenceRoomEquipments;
	private Equipment equipment;
	private EquipmentDTO equipmentDTO;
		
	@InjectMocks
	ConferenceRoomMapper conferenceRoomMapper;
	@Mock
	private Mapper mapper;

	@Before
	public void setup() {
		conferenceRoomDTO = new ConferenceRoomDTO();
		equipmentDTO = new EquipmentDTO();
		conferenceRoom = new ConferenceRoom();
		building = new Building();
		building.setBuildingId(1L);
		conferenceRoom.setBuilding(building);
		conferenceRoomEquipment = new ConferenceRoomEquipment();
		conferenceRoomEquipments = new ArrayList<>();
		equipment = new Equipment();
		conferenceRoomEquipment.setEquipment(equipment);
		conferenceRoomEquipments.add(conferenceRoomEquipment);
		conferenceRoom.setConferenceRoomEquipments(conferenceRoomEquipments);
		Mockito.when(mapper.map(conferenceRoom, ConferenceRoomDTO.class)).thenReturn(conferenceRoomDTO);
		Mockito.when(mapper.map(conferenceRoomDTO, ConferenceRoom.class)).thenReturn(conferenceRoom);
		Mockito.when(mapper.map(conferenceRoomDTO, ConferenceRoom.class)).thenReturn(conferenceRoom);
		Mockito.when(mapper.map(equipmentDTO, Equipment.class)).thenReturn(equipment);
	}	

	@Test
	public void testToEntity() {
		assertNotNull(conferenceRoomMapper.toEntity(conferenceRoomDTO));
	}

	@Test
	public void testToDto() {
		ConferenceRoomDTO dtoReturn=  conferenceRoomMapper.toDto(conferenceRoom);
		assertEquals(conferenceRoomDTO, dtoReturn);
		
	}

	@Test
	public void testToEntityFromList() {
		List<ConferenceRoomDTO> entityList = new ArrayList<ConferenceRoomDTO>();
		entityList.add(conferenceRoomDTO);
		List<ConferenceRoom> dtoList=  conferenceRoomMapper.toEntity(entityList);
		assertEquals(dtoList.size(), 1);
	}


	@Test
	public void testToDtoFromList() {
		List<ConferenceRoom> entityList = new ArrayList<ConferenceRoom>();
		entityList.add(conferenceRoom);
		List<ConferenceRoomDTO> dtoList=  conferenceRoomMapper.toDto(entityList);
		assertEquals(dtoList.size(), 1);
	}


}
