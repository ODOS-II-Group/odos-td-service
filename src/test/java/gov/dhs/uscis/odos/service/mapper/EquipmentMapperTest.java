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

import gov.dhs.uscis.odos.domain.Equipment;
import gov.dhs.uscis.odos.service.dto.EquipmentDTO;

@RunWith(MockitoJUnitRunner.class)
public class EquipmentMapperTest {
	
	private Equipment equipment;
	private EquipmentDTO equipmentDTO;
		
	
	@InjectMocks
	EquipmentMapper equipmentMapper;


	@Mock
	private Mapper mapper;

	@Before
	public void setup() {
		equipmentDTO = new EquipmentDTO();
		equipment = new Equipment();
		Mockito.when(mapper.map(equipment, EquipmentDTO.class)).thenReturn(equipmentDTO);
		Mockito.when(mapper.map(equipmentDTO, Equipment.class)).thenReturn(equipment);
		 
		
	}	

	@Test
	public void testToEntity() {
		assertNotNull(equipmentMapper.toEntity(equipmentDTO));
	}

	@Test
	public void testToDto() {
		EquipmentDTO dtoReturn=  equipmentMapper.toDto(equipment);
		assertEquals(equipmentDTO, dtoReturn);
		 
		
	}

	@Test
	public void testToEntityFromList() {
		List<EquipmentDTO> entityList = new ArrayList<EquipmentDTO>();
		entityList.add(equipmentDTO);
		List<Equipment> dtoList=  equipmentMapper.toEntity(entityList);
		assertEquals(dtoList.size(), 1);
	}


	@Test
	public void testToDtoFromList() {
		List<Equipment> entityList = new ArrayList<Equipment>();
		entityList.add(equipment);
		List<EquipmentDTO> dtoList=  equipmentMapper.toDto(entityList);
		assertEquals(dtoList.size(), 1);
	}

}
