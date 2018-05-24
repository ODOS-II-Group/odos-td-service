package gov.dhs.uscis.odos.service.impl;

import static org.junit.Assert.assertEquals;

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

import gov.dhs.uscis.odos.domain.ConferenceRoom;
import gov.dhs.uscis.odos.repository.ConferenceRoomRepository;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomDTO;
import gov.dhs.uscis.odos.service.mapper.ConferenceRoomMapper;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceRoomServiceImplTest {
	
	@InjectMocks
	ConferenceRoomServiceImpl conferenceRoomServiceImpl;
	@Mock
    private  ConferenceRoomRepository conferenceRoomRepository;
	@Mock
    private ConferenceRoomMapper conferenceRoomMapper;
	
	private ConferenceRoomDTO conferenceRoomDTO;
	
	private ConferenceRoom conferenceRoom; 

	@Mock
	private Mapper mapper;
	private List<ConferenceRoom> confRoomList;

	@Before
	public void setup() {
		conferenceRoomDTO = new ConferenceRoomDTO();
		conferenceRoomDTO.setConferenceRoomId(10L);
		conferenceRoom = new ConferenceRoom();
		confRoomList = new ArrayList<>();
		confRoomList.add(conferenceRoom);
		Mockito.when(conferenceRoomMapper.toEntity(conferenceRoomDTO)).thenReturn(conferenceRoom);
		Mockito.when(conferenceRoomRepository.save(conferenceRoom)).thenReturn(conferenceRoom);
		Mockito.when(conferenceRoomMapper.toDto(conferenceRoom)).thenReturn(conferenceRoomDTO);
		Mockito.when(conferenceRoomRepository.findAll()).thenReturn(confRoomList);
		Mockito.when(conferenceRoomRepository.findOne(10L)).thenReturn(conferenceRoom);
	}
	
	
	
	@Test
	public void testSave() {
		ConferenceRoomDTO returnDto = conferenceRoomServiceImpl.save(conferenceRoomDTO);
		long equipId =returnDto.getConferenceRoomId();
		assertEquals(10L, equipId);
		Mockito.verify(conferenceRoomRepository).save(conferenceRoom);
	}
	@Test
	public void testFindAll() {
		List<ConferenceRoomDTO> returnList = conferenceRoomServiceImpl.findAll();
		assertEquals(1, returnList.size());
		Mockito.verify(conferenceRoomRepository).findAll();
	}
	
	@Test
	public void testFindOne() {
		ConferenceRoomDTO dtoReturn = conferenceRoomServiceImpl.findOne(10L);
		long equipId =dtoReturn.getConferenceRoomId();
		assertEquals(10L, equipId);
		Mockito.verify(conferenceRoomRepository).findOne(10L);
	}
	

	@Test
	public void testDelete() {
		conferenceRoomServiceImpl.delete(10L);
		Mockito.verify(conferenceRoomRepository).delete(10L);
	}


}
