package gov.dhs.uscis.odos.service.mapper;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.time.DateUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.dhs.uscis.odos.domain.ConferenceRoomSchedule;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomScheduleDTO;


/**
 * Mapper for the entity ConferenceRoomSchedule and its DTO
 * ConferenceRoomScheduleDTO.
 */
@Named
public class ConferenceRoomScheduleMapper implements EntityMapper<ConferenceRoomScheduleDTO, ConferenceRoomSchedule> {

	@Inject
	private Mapper mapper;
	
	private static final Logger log  = LoggerFactory.getLogger(ConferenceRoomScheduleMapper.class);
	
	private static final String DATE_FORMAT  = "yyyy-MM-dd HH:mm";

	@Override
	public ConferenceRoomSchedule toEntity(ConferenceRoomScheduleDTO dto) {
		String startDate = dto.getRoomScheduleStartTime();
		String endDate = dto.getRoomScheduleEndTime();
		dto.setRoomScheduleStartTime(null);
		dto.setRoomScheduleEndTime(null);
		ConferenceRoomSchedule conferenceRoomSchedule  =  mapper.map(dto, ConferenceRoomSchedule.class);
		
		conferenceRoomSchedule.setRoomScheduleStartTime(convertDateString(startDate, DATE_FORMAT));
		conferenceRoomSchedule.setRoomScheduleEndTime(convertDateString(endDate, DATE_FORMAT));
		return conferenceRoomSchedule;
		
	}

	@Override
	public ConferenceRoomScheduleDTO toDto(ConferenceRoomSchedule entity) {
		if (entity == null) return null;
		ConferenceRoomScheduleDTO conferenceRoomScheduleDTO = mapper.map(entity, ConferenceRoomScheduleDTO.class);
		if (entity.getRoomScheduleStartTime() != null) {
			conferenceRoomScheduleDTO.setRoomScheduleStartTime(convertDateValue(entity.getRoomScheduleStartTime(), DATE_FORMAT));
		}
		if (entity.getRoomScheduleEndTime() != null) {
			conferenceRoomScheduleDTO.setRoomScheduleEndTime(convertDateValue(entity.getRoomScheduleEndTime(), DATE_FORMAT));
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
	
	private Date convertDateString(String dateStr, String format) {
		Date dateValue = null;
		try {
			dateValue = DateUtils.parseDate(dateStr, format);
		}
		catch(ParseException e) {
			log.error("Error parsing date value " + dateStr, e);
			throw new RuntimeException(e);
		}
		return dateValue;
	}
	
	private String convertDateValue(Date dateValue, String format) {
		Instant instant = dateValue.toInstant();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		return localDateTime.toString();
	}

}
