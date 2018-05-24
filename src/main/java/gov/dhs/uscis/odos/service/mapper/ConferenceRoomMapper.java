package gov.dhs.uscis.odos.service.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.Validate;
import org.dozer.Mapper;

import gov.dhs.uscis.odos.domain.ConferenceRoom;
import gov.dhs.uscis.odos.domain.ConferenceRoomEquipment;
import gov.dhs.uscis.odos.domain.ConferenceRoomSchedule;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomDTO;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomScheduleDTO;
import gov.dhs.uscis.odos.service.dto.EquipmentDTO;

/**
 * Mapper for the entity ConferenceRoom and its DTO ConferenceRoomDTO.
 */
@Named
public class ConferenceRoomMapper implements EntityMapper<ConferenceRoomDTO, ConferenceRoom> {

	@Inject
	private Mapper mapper;
	
	@Inject
	private ConferenceRoomScheduleMapper crsMapper;

	@Override
	public ConferenceRoom toEntity(ConferenceRoomDTO dto) {	
		return mapper.map(dto, ConferenceRoom.class);
	}

	@Override
	public ConferenceRoomDTO toDto(ConferenceRoom entity) {
		List<EquipmentDTO> equipmentDTO = new ArrayList<>();
		Validate.notNull(entity, "The ConferenceRoom entity must not be null");
		ConferenceRoomDTO conferenceRoomDTO = mapper.map(entity, ConferenceRoomDTO.class);
		for (ConferenceRoomEquipment equipment : entity.getConferenceRoomEquipments()) {
			equipmentDTO.add(mapper.map(equipment.getEquipment(), EquipmentDTO.class));
		}
		conferenceRoomDTO.setEquipments(equipmentDTO);
		
		List<ConferenceRoomScheduleDTO> scheduleDTO = new ArrayList<>();
		for (ConferenceRoomSchedule schedule : entity.getConferenceRoomSchedule()) {
			scheduleDTO.add(crsMapper.toDto(schedule));
		}
		conferenceRoomDTO.setSchedule(scheduleDTO);
		conferenceRoomDTO.setBuildingId(entity.getBuilding().getBuildingId());
		
		return conferenceRoomDTO;
	}

	@Override
	public List<ConferenceRoom> toEntity(List<ConferenceRoomDTO> dtoList) {
		List<ConferenceRoom> conferenceRoomLists = new ArrayList<>();
		for (ConferenceRoomDTO conferenceRoomDTO : dtoList) {
			conferenceRoomLists.add(mapper.map(conferenceRoomDTO, ConferenceRoom.class));
		}
		return conferenceRoomLists;
	}

	@Override
	public List<ConferenceRoomDTO> toDto(List<ConferenceRoom> entityList) {
		List<ConferenceRoomDTO> conferenceRoomDTOList = new ArrayList<>();
		for (ConferenceRoom conferenceRoomEnty : entityList) {
			conferenceRoomDTOList.add(mapper.map(conferenceRoomEnty, ConferenceRoomDTO.class));
		}
		return conferenceRoomDTOList;
	}

}
