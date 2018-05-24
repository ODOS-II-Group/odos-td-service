package gov.dhs.uscis.odos.service.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
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
import gov.dhs.uscis.odos.domain.ConferenceRoomSchedule;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomScheduleDTO;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceRoomScheduleMapperTest {
	
	private ConferenceRoomSchedule conferenceRoomSchedule;
	private ConferenceRoomScheduleDTO conferenceRoomDTO;
		
	@InjectMocks
	ConferenceRoomScheduleMapper  conferenceRoomScheduleMapper;
	@Mock
	private Mapper mapper;

	@Before
	public void setup() {
		conferenceRoomDTO = new ConferenceRoomScheduleDTO();
		conferenceRoomDTO.setConferenceTitle("ODOS TEST");
		conferenceRoomDTO.setRoomScheduleStartTime("2018-04-19 12:00");
		conferenceRoomDTO.setRoomScheduleEndTime("2018-04-19 12:00");
		conferenceRoomSchedule = new ConferenceRoomSchedule();
		conferenceRoomSchedule.setConferenceTitle("ODOS TEST");
		conferenceRoomSchedule.setRoomScheduleEndTime(new Date());
		conferenceRoomSchedule.setRoomScheduleStartTime(new Date());
		ConferenceRoom conferenceRoom = new ConferenceRoom();
		conferenceRoom.setConferenceRoomId(10L);
		Building building = new Building();
		building.setBuildingName("BNAME");
		conferenceRoom.setBuilding(building);
		conferenceRoomSchedule.setConferenceRoom(conferenceRoom);
		Mockito.when(mapper.map(conferenceRoomDTO, ConferenceRoomSchedule.class)).thenReturn(conferenceRoomSchedule);
		Mockito.when(mapper.map(conferenceRoomSchedule, ConferenceRoomScheduleDTO.class)).thenReturn(conferenceRoomDTO);
	}	

	@Test
	public void testToEntity() {
		ConferenceRoomSchedule returnEntity = conferenceRoomScheduleMapper.toEntity(conferenceRoomDTO);
		assertNotNull(returnEntity);
	}
	@Test
	public void testToEntityWithException() {
		try {
		conferenceRoomDTO.setRoomScheduleStartTime("2018-04-19");
		conferenceRoomDTO.setRoomScheduleEndTime("2018-04-19");
		conferenceRoomScheduleMapper.toEntity(conferenceRoomDTO);
		fail();
		}
		catch(Exception e)
		{
		assertEquals("java.text.ParseException: Unable to parse the date: 2018-04-19", e.getMessage());	
		}
 
	}


	@Test
	public void testToDto() {
		ConferenceRoomScheduleDTO returnEntity = conferenceRoomScheduleMapper.toDto(conferenceRoomSchedule);
		assertNotNull(returnEntity);
	}


	@Test
	public void testToEntityFromList() {
		List<ConferenceRoomScheduleDTO> dtoList = new ArrayList<>();
		List<ConferenceRoomSchedule> returnEntityList = conferenceRoomScheduleMapper.toEntity(dtoList);
		assertEquals(0, returnEntityList.size());
		
	}


	@Test
	public void testToDtoFromList() {
		List<ConferenceRoomSchedule> dtoList = new ArrayList<>();
		List<ConferenceRoomScheduleDTO> returnEntityList= conferenceRoomScheduleMapper.toDto(dtoList);
		assertEquals(0, returnEntityList.size());
	}

}
