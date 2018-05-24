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

import gov.dhs.uscis.odos.domain.ConferenceRoomEquipment;
import gov.dhs.uscis.odos.repository.ConferenceRoomEquipmentRepository;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomEquipmentDTO;
import gov.dhs.uscis.odos.service.mapper.ConferenceRoomEquipmentMapper;

@RunWith(MockitoJUnitRunner.class)

public class ConferenceRoomEquipmentServiceImplTest {
	
	@InjectMocks
	ConferenceRoomEquipmentServiceImpl conferenceRoomEquipmentServiceImpl;
	@Mock
    private  ConferenceRoomEquipmentRepository conferenceRoomEquipRepository;
	@Mock
    private ConferenceRoomEquipmentMapper conferenceRoomEquipMapper;
	
	private ConferenceRoomEquipmentDTO conferenceRoomEquipmentDTO;
	
	private ConferenceRoomEquipment conferenceRoomEquip; 

	@Mock
	private Mapper mapper;
	private List<ConferenceRoomEquipment> confEquipList;

	@Before
	public void setup() {
		conferenceRoomEquipmentDTO = new ConferenceRoomEquipmentDTO();
		conferenceRoomEquipmentDTO.setConferenceRoomEquipId(10L);
		conferenceRoomEquip = new ConferenceRoomEquipment();
		confEquipList = new ArrayList<>();
		confEquipList.add(conferenceRoomEquip);
		Mockito.when(conferenceRoomEquipMapper.toEntity(conferenceRoomEquipmentDTO)).thenReturn(conferenceRoomEquip);
		Mockito.when(conferenceRoomEquipRepository.save(conferenceRoomEquip)).thenReturn(conferenceRoomEquip);
		Mockito.when(conferenceRoomEquipMapper.toDto(conferenceRoomEquip)).thenReturn(conferenceRoomEquipmentDTO);
		Mockito.when(conferenceRoomEquipRepository.findAll()).thenReturn(confEquipList);
		Mockito.when(conferenceRoomEquipRepository.findOne(10L)).thenReturn(conferenceRoomEquip);
	}	
	
	@Test
	public void testSave() {
		ConferenceRoomEquipmentDTO returnDto = conferenceRoomEquipmentServiceImpl.save(conferenceRoomEquipmentDTO);
		long equipId =returnDto.getConferenceRoomEquipId();
		assertEquals(10L, equipId);
		Mockito.verify(conferenceRoomEquipRepository).save(conferenceRoomEquip);
	}
	@Test
	public void testFindAll() {
		List<ConferenceRoomEquipmentDTO> returnList = conferenceRoomEquipmentServiceImpl.findAll();
		assertEquals(1, returnList.size());
		Mockito.verify(conferenceRoomEquipRepository).findAll();
	}
	
	@Test
	public void testFindOne() {
		ConferenceRoomEquipmentDTO dtoReturn = conferenceRoomEquipmentServiceImpl.findOne(10L);
		long equipId =dtoReturn.getConferenceRoomEquipId();
		assertEquals(10L, equipId);
		Mockito.verify(conferenceRoomEquipRepository).findOne(10L);
	}
	

	@Test
	public void testDelete() {
		conferenceRoomEquipmentServiceImpl.delete(10L);
		Mockito.verify(conferenceRoomEquipRepository).delete(10L);
	}


}
