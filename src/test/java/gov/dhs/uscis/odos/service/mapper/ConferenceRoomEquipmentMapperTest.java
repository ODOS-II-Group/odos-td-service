package gov.dhs.uscis.odos.service.mapper;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import gov.dhs.uscis.odos.domain.ConferenceRoom;
import gov.dhs.uscis.odos.domain.ConferenceRoomEquipment;
import gov.dhs.uscis.odos.domain.Equipment;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomEquipmentDTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceRoomEquipmentMapperTest {
	
private ConferenceRoomEquipment conferenceRoomEquipment;	
private Equipment equipment;
private ConferenceRoom conferenceRoom;
private ConferenceRoomEquipmentDTO conferenceRoomEquipDTO;
	
@InjectMocks
ConferenceRoomEquipmentMapper conferenceRoomEquipmentMapper;
@Mock
private Mapper mapper;

@Before
public void setup() {
	conferenceRoomEquipDTO = new ConferenceRoomEquipmentDTO();
	conferenceRoomEquipment = new ConferenceRoomEquipment();
	conferenceRoomEquipment.setConferenceRoomEquipId(1L);
	conferenceRoomEquipment.setEquipment(equipment);
	conferenceRoomEquipment.setConferenceRoom(conferenceRoom);
	Mockito.when(mapper.map(conferenceRoomEquipment, ConferenceRoomEquipmentDTO.class)).thenReturn(conferenceRoomEquipDTO);
	Mockito.when(mapper.map(conferenceRoomEquipDTO, ConferenceRoomEquipment.class)).thenReturn(conferenceRoomEquipment);
}

@Test
public void testToEntity() {
	assertNotNull(conferenceRoomEquipmentMapper.toEntity(conferenceRoomEquipDTO));
}


@Test
public void testToDto() {
	ConferenceRoomEquipmentDTO dtoReturn=  conferenceRoomEquipmentMapper.toDto(conferenceRoomEquipment);
	assertEquals(conferenceRoomEquipDTO, dtoReturn);
	
}
@Test
public void testToEntityFromList() {
	List<ConferenceRoomEquipmentDTO> entityList = new ArrayList<ConferenceRoomEquipmentDTO>();
	entityList.add(conferenceRoomEquipDTO);
	List<ConferenceRoomEquipment> dtoList=  conferenceRoomEquipmentMapper.toEntity(entityList);
	assertEquals(dtoList.size(), 1);
}

@Test
public void testToDtoFromList() {
	List<ConferenceRoomEquipment> entityList = new ArrayList<ConferenceRoomEquipment>();
	entityList.add(conferenceRoomEquipment);
	List<ConferenceRoomEquipmentDTO> dtoList=  conferenceRoomEquipmentMapper.toDto(entityList);
	assertEquals(dtoList.size(), 1);
}


}
