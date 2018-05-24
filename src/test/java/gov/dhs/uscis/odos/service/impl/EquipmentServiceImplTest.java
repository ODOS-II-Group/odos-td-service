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

import gov.dhs.uscis.odos.domain.Equipment;
import gov.dhs.uscis.odos.repository.EquipmentRepository;
import gov.dhs.uscis.odos.service.dto.EquipmentDTO;
import gov.dhs.uscis.odos.service.mapper.EquipmentMapper;

@RunWith(MockitoJUnitRunner.class)
public class EquipmentServiceImplTest {

	@InjectMocks
	EquipmentServiceImpl equipmentServiceImpl;
	@Mock
    private  EquipmentRepository equipmentRepository;
	@Mock
	private EquipmentMapper equipmentMapper;
	
	private EquipmentDTO equipmentDTO;
	
	private Equipment equipment; 

	@Mock
	private Mapper mapper;
	private List<Equipment> equipList;

	@Before
	public void setup() {
		equipmentDTO = new EquipmentDTO();
		equipmentDTO.setEquipmentName("50 inch TV");
		equipment = new Equipment();
		equipList = new ArrayList<>();
		equipList.add(equipment);
		Mockito.when(equipmentMapper.toEntity(equipmentDTO)).thenReturn(equipment);
		Mockito.when(equipmentRepository.save(equipment)).thenReturn(equipment);
		Mockito.when(equipmentMapper.toDto(equipment)).thenReturn(equipmentDTO);
		Mockito.when(equipmentRepository.findAll()).thenReturn(equipList);
		Mockito.when(equipmentRepository.findOne(10L)).thenReturn(equipment);
	}
	
	
	
	@Test
	public void testSave() {
		EquipmentDTO returnDto = equipmentServiceImpl.save(equipmentDTO);
		String equipName =returnDto.getEquipmentName();
		assertEquals("50 inch TV", equipName);
		Mockito.verify(equipmentRepository).save(equipment);
	}
	@Test
	public void testFindAll() {
		List<EquipmentDTO> returnList = equipmentServiceImpl.findAll();
		assertEquals(1, returnList.size());
		Mockito.verify(equipmentRepository).findAll();
	}
	
	@Test
	public void testFindOne() {
		EquipmentDTO dtoReturn = equipmentServiceImpl.findOne(10L);
		String equipName =dtoReturn.getEquipmentName();
		assertEquals("50 inch TV", equipName);
		Mockito.verify(equipmentRepository).findOne(10L);
	}
	

	@Test
	public void testDelete() {
		equipmentServiceImpl.delete(10L);
		Mockito.verify(equipmentRepository).delete(10L);
	}

}
