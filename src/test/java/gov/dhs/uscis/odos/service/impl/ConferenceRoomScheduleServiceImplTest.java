package gov.dhs.uscis.odos.service.impl;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import gov.dhs.uscis.odos.domain.ConferenceRoomSchedule;
import gov.dhs.uscis.odos.repository.ConferenceRoomScheduleRepository;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomScheduleDTO;
import gov.dhs.uscis.odos.service.mapper.ConferenceRoomScheduleMapper;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceRoomScheduleServiceImplTest {
	
	@InjectMocks
	ConferenceRoomScheduleServiceImpl conferenceRoomScheduleServiceImpl;
	@Mock
    private ConferenceRoomScheduleRepository conferenceRoomScheduleRepository;


	
	@Mock
    private  ConferenceRoomScheduleMapper conferenceRoomScheduleMapper;
	private List<ConferenceRoomSchedule> confRoomShecduleList;
	private ConferenceRoomScheduleDTO conferenceRoomScheduleDTO;
	
	private ConferenceRoomSchedule conferenceRoomSchedule;
	
	 
	
	@Mock
	Page<ConferenceRoomSchedule>  pageConfRoomSchedule;
	@Mock
	private Mapper mapper;
	@Mock
	Pageable pageable;

	@Before
	public void setup() {
		conferenceRoomScheduleDTO = new ConferenceRoomScheduleDTO();
		conferenceRoomScheduleDTO.setConferenceTitle("ODOS TEST");
		conferenceRoomScheduleDTO.setConferenceRoomId(1L);
		conferenceRoomSchedule = new ConferenceRoomSchedule();
		confRoomShecduleList = new ArrayList<>();
		confRoomShecduleList.add(conferenceRoomSchedule);
	
		Mockito.when(conferenceRoomScheduleMapper.toEntity(conferenceRoomScheduleDTO)).thenReturn(conferenceRoomSchedule);
		Mockito.when(conferenceRoomScheduleRepository.save(conferenceRoomSchedule)).thenReturn(conferenceRoomSchedule);
		Mockito.when(conferenceRoomScheduleMapper.toDto(conferenceRoomSchedule)).thenReturn(conferenceRoomScheduleDTO);
		Mockito.when(conferenceRoomScheduleRepository.findAll(pageable)).thenReturn(pageConfRoomSchedule);
		Mockito.when(conferenceRoomScheduleRepository.findOne(Mockito.anyLong())).thenReturn(conferenceRoomSchedule);
		
	}	

	@Test
	public void testFindByRequestId() {
		List<ConferenceRoomScheduleDTO> returnList = conferenceRoomScheduleServiceImpl.findByRequestId("1");
		assertNotNull(returnList);
		
	}
	

	@Test
	public void testFindAll() {
		conferenceRoomScheduleServiceImpl.findAll(pageable);
		Mockito.verify(conferenceRoomScheduleRepository).findAll(pageable);
	}
	
	@Test
	public void testFindOne() {
		ConferenceRoomScheduleDTO dtoReturn = conferenceRoomScheduleServiceImpl.findOne(10L);
		String title =dtoReturn.getConferenceTitle();
		assertEquals("ODOS TEST", title);
		Mockito.verify(conferenceRoomScheduleRepository).findOne(10L);
	}
	

	@Test
	public void testDelete() {
		conferenceRoomScheduleServiceImpl.delete(10L);
		Mockito.verify(conferenceRoomScheduleRepository).delete(10L);
	}



}
