package gov.dhs.uscis.odos.service.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;

import gov.dhs.uscis.odos.domain.ConferenceRoomEquipment;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomEquipmentDTO;

/**
 * Mapper for the entity ConferenceRoomEquipment and its DTO ConferenceRoomEquipmentDTO.
 */
@Named
public class ConferenceRoomEquipmentMapper implements EntityMapper<ConferenceRoomEquipmentDTO, ConferenceRoomEquipment> {

	@Inject
	private Mapper mapper;

	@Override
	public ConferenceRoomEquipment toEntity(ConferenceRoomEquipmentDTO dto) {
		return mapper.map(dto, ConferenceRoomEquipment.class);
	}

	@Override
	public ConferenceRoomEquipmentDTO toDto(ConferenceRoomEquipment entity) {
		ConferenceRoomEquipmentDTO conferenceRoomEquipDTO = mapper.map(entity, ConferenceRoomEquipmentDTO.class);
		return conferenceRoomEquipDTO;
	}

	@Override
	public List<ConferenceRoomEquipment> toEntity(List<ConferenceRoomEquipmentDTO> dtoList) {
		List<ConferenceRoomEquipment> equipmentLists = new ArrayList<>();
		for (ConferenceRoomEquipmentDTO equipmentDTO : dtoList) {
			equipmentLists.add(mapper.map(equipmentDTO, ConferenceRoomEquipment.class));
		}
		return equipmentLists;
	}

	@Override
	public List<ConferenceRoomEquipmentDTO> toDto(List<ConferenceRoomEquipment> entityList) {
		List<ConferenceRoomEquipmentDTO> conferenceRoomEquipDTOList = new ArrayList<>();
		for (ConferenceRoomEquipment conferenceRoomEquipEnty : entityList) {
			conferenceRoomEquipDTOList.add(mapper.map(conferenceRoomEquipEnty, ConferenceRoomEquipmentDTO.class));
		}
		return conferenceRoomEquipDTOList;
	}

}
