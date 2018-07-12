package gov.dhs.uscis.odos.service.mapper;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.dhs.uscis.odos.domain.ConferenceRoomSchedule;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomScheduleDTO;
import gov.dhs.uscis.odos.util.DateUtil;


/**
 * Mapper for the entity ConferenceRoomSchedule and its DTO
 * ConferenceRoomScheduleDTO.
 */
@Named
public class ConferenceRoomScheduleMapper implements EntityMapper<ConferenceRoomScheduleDTO, ConferenceRoomSchedule> {

	@Inject
	private Mapper mapper;
	
	private static final Logger log  = LoggerFactory.getLogger(ConferenceRoomScheduleMapper.class);

	@Override
	public ConferenceRoomSchedule toEntity(ConferenceRoomScheduleDTO dto) {
		String startDate = dto.getRoomScheduleStartTime();
		String endDate = dto.getRoomScheduleEndTime();
		dto.setRoomScheduleStartTime(null);
		dto.setRoomScheduleEndTime(null);
		ConferenceRoomSchedule conferenceRoomSchedule  =  mapper.map(dto, ConferenceRoomSchedule.class);
		
		conferenceRoomSchedule.setRoomScheduleStartTime(DateUtil.convertDateString(startDate));
		conferenceRoomSchedule.setRoomScheduleEndTime(DateUtil.convertDateString(endDate));
		return conferenceRoomSchedule;
		
	}
	
	@Override
	public void toEntity(ConferenceRoomScheduleDTO dto, ConferenceRoomSchedule confRoomSchedule) {
		String startDate = dto.getRoomScheduleStartTime();
		String endDate = dto.getRoomScheduleEndTime();
		dto.setRoomScheduleStartTime(null);
		dto.setRoomScheduleEndTime(null);
		mapper.map(dto, confRoomSchedule);
		
		confRoomSchedule.setRoomScheduleStartTime(DateUtil.convertDateString(startDate));
		confRoomSchedule.setRoomScheduleEndTime(DateUtil.convertDateString(endDate));
		
	}

	@Override
	public ConferenceRoomScheduleDTO toDto(ConferenceRoomSchedule entity) {
		if (entity == null) return null;
		ConferenceRoomScheduleDTO conferenceRoomScheduleDTO = mapper.map(entity, ConferenceRoomScheduleDTO.class);
		if (entity.getRoomScheduleStartTime() != null) {
			conferenceRoomScheduleDTO.setRoomScheduleStartTime(DateUtil.convertDateValue(entity.getRoomScheduleStartTime()));
		}
		if (entity.getRoomScheduleEndTime() != null) {
			conferenceRoomScheduleDTO.setRoomScheduleEndTime(DateUtil.convertDateValue(entity.getRoomScheduleEndTime()));
		}
		conferenceRoomScheduleDTO.setConferenceRoomId(entity.getConferenceRoom().getConferenceRoomId());
		conferenceRoomScheduleDTO.setBuildingName(entity.getConferenceRoom().getBuilding().getBuildingName());
		conferenceRoomScheduleDTO.setConferenceRoomName(entity.getConferenceRoom().getRoomName());
		return conferenceRoomScheduleDTO;
	}

	@Override
	public List<ConferenceRoomSchedule> toEntity(List<ConferenceRoomScheduleDTO> dtoList) {
		
		return Collections.emptyList();
	}

	@Override
	public List<ConferenceRoomScheduleDTO> toDto(List<ConferenceRoomSchedule> entityList) {
		
		return Collections.emptyList();
	}

}
