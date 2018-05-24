package gov.dhs.uscis.odos.service.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;

import gov.dhs.uscis.odos.domain.Equipment;
import gov.dhs.uscis.odos.service.dto.EquipmentDTO;

/**
 * Mapper for the entity Equipment and its DTO EquipmentDTO.
 */
@Named
public class EquipmentMapper implements EntityMapper<EquipmentDTO, Equipment> {

	@Inject
	private Mapper mapper;

	@Override
	public Equipment toEntity(EquipmentDTO dto) {
		return mapper.map(dto, Equipment.class);
	}

	@Override
	public EquipmentDTO toDto(Equipment entity) {
		EquipmentDTO equipmentDTO = mapper.map(entity, EquipmentDTO.class);
		return equipmentDTO;
	}

	@Override
	public List<Equipment> toEntity(List<EquipmentDTO> dtoList) {
		List<Equipment> equipmentLists = new ArrayList<>();
		for (EquipmentDTO equipmentDTO : dtoList) {
			equipmentLists.add(mapper.map(equipmentDTO, Equipment.class));
		}
		return equipmentLists;
	}

	@Override
	public List<EquipmentDTO> toDto(List<Equipment> entityList) {
		List<EquipmentDTO> equipmentDTOList = new ArrayList<>();
		for (Equipment equipmentEnty : entityList) {
			equipmentDTOList.add(mapper.map(equipmentEnty, EquipmentDTO.class));
		}
		return equipmentDTOList;
	}

}
