package gov.dhs.uscis.odos.service.impl;

import gov.dhs.uscis.odos.service.ConferenceRoomScheduleService;
import gov.dhs.uscis.odos.domain.ConferenceRoomSchedule;
import gov.dhs.uscis.odos.repository.ConferenceRoomRepository;
import gov.dhs.uscis.odos.repository.ConferenceRoomScheduleRepository;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomScheduleDTO;
import gov.dhs.uscis.odos.service.mapper.ConferenceRoomScheduleMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import java.util.Date;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dhs.uscis.odos.domain.ConferenceRoomSchedule;
import gov.dhs.uscis.odos.repository.ConferenceRoomRepository;
import gov.dhs.uscis.odos.repository.ConferenceRoomScheduleRepository;
import gov.dhs.uscis.odos.service.ConferenceRoomScheduleService;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomScheduleDTO;
import gov.dhs.uscis.odos.service.mapper.ConferenceRoomScheduleMapper;
import gov.dhs.uscis.odos.util.DateUtil;

/**
 * Service Implementation for managing ConferenceRoomSchedule.
 */
@Service
@Transactional
public class ConferenceRoomScheduleServiceImpl implements ConferenceRoomScheduleService {

	@Inject
	ConferenceRoomRepository conferenceRoomRepository;

	private final Logger log = LoggerFactory.getLogger(ConferenceRoomScheduleServiceImpl.class);

	private final ConferenceRoomScheduleRepository conferenceRoomScheduleRepository;

	private final ConferenceRoomScheduleMapper conferenceRoomScheduleMapper;

	public ConferenceRoomScheduleServiceImpl(ConferenceRoomScheduleRepository conferenceRoomScheduleRepository,
			ConferenceRoomScheduleMapper conferenceRoomScheduleMapper) {
		this.conferenceRoomScheduleRepository = conferenceRoomScheduleRepository;
		this.conferenceRoomScheduleMapper = conferenceRoomScheduleMapper;
	}

	/**
	 * Save a conferenceRoomSchedule.
	 *
	 * @param conferenceRoomScheduleDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public ConferenceRoomScheduleDTO save(ConferenceRoomScheduleDTO conferenceRoomScheduleDTO) {
		log.debug("Request to save ConferenceRoomSchedule : {}", conferenceRoomScheduleDTO);
		ConferenceRoomSchedule conferenceRoomSchedule = conferenceRoomScheduleMapper
				.toEntity(conferenceRoomScheduleDTO);
		conferenceRoomSchedule
				.setConferenceRoom(conferenceRoomRepository.findOne(conferenceRoomScheduleDTO.getConferenceRoomId()));
		conferenceRoomSchedule = conferenceRoomScheduleRepository.save(conferenceRoomSchedule);
		return conferenceRoomScheduleMapper.toDto(conferenceRoomSchedule);
	}

	/**
	 * Get all the conferenceRoomSchedules.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<ConferenceRoomScheduleDTO> findAll(Pageable pageable) {
		log.debug("Request to get all ConferenceRoomSchedules");
		return conferenceRoomScheduleRepository.findAll(pageable).map(conferenceRoomScheduleMapper::toDto);
	}

	/**
	 * Get one conferenceRoomSchedule by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public ConferenceRoomScheduleDTO findOne(Long id) {
		log.debug("Request to get ConferenceRoomSchedule : {}", id);
		ConferenceRoomSchedule conferenceRoomSchedule = conferenceRoomScheduleRepository.findOne(id);
		return conferenceRoomScheduleMapper.toDto(conferenceRoomSchedule);
	}

	/**
	 * Delete the conferenceRoomSchedule by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete ConferenceRoomSchedule : {}", id);
		conferenceRoomScheduleRepository.delete(id);
	}

	@Override
	public List<ConferenceRoomScheduleDTO> findByRequestId(String requestorId) {
		return conferenceRoomScheduleRepository.findAllByRequestorId(requestorId).stream()
				.map(conferenceRoomScheduleMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public List<ConferenceRoomScheduleDTO> findAllByConferenceRoom(long conferenceRoomId) {
		return conferenceRoomScheduleRepository.findAllByConferenceRoomId(conferenceRoomId).stream()
				.map(conferenceRoomScheduleMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public int findAllScheduledRoomTodayById(Long id) {
		return conferenceRoomScheduleRepository.findAllScheduledRoomTodayByConferenceRoomI(id);
	}

	@Override
	public List<Integer> getRoomBookedTimeSlot() {
		List<ConferenceRoomScheduleDTO> test = Arrays.asList(new ConferenceRoomScheduleDTO());
		return null;
	}

	public static void main(String[] args) {
		List<Integer> slot = new ArrayList<>();
		// Map<Integer, List<E>> map = new HashMap<>;
		// 9 - 17
		// 00, 15 ,30 ,45,

		// 9,10,11,12,13,14,15,16,17

		Date st = new Date();
		DateTime startTime = new DateTime(st);
		
		int startHour = startTime.getHourOfDay();
		int startMinute = startTime.getMinuteOfHour();

		Date et = new Date();
		DateTime endTime = new DateTime(et);
		endTime.plusHours(1);
		int endHour = endTime.getHourOfDay();
		int endMinute = endTime.getMinuteOfHour();

		int slotCounter = 0;

		for (int hour = 9; hour < 24; hour++) {
			int endH = +hour;
			slotCounter ++;
			if (startHour == hour) {
				if (startMinute == 0 && endMinute == 15) {
					slot.add(slotCounter);
				} else if (startMinute == 15 && endMinute == 45) {
					slotCounter = +slotCounter;
					slot.add(slotCounter);
				} else if (startHour == hour && endHour == endH && startMinute == 45 && endMinute == 0) {
					slotCounter = +slotCounter;
					slot.add(slotCounter);
				}
			}

		}
	}

	@Override
	public List<ConferenceRoomScheduleDTO> findAllByConferenceRoomIdAndDate(Long conferenceRoomId,
			String roomScheduleStartTime, String roomScheduleEndTime) {
		Date startDate = DateUtil.convertDateString(roomScheduleStartTime);
		Date endDate = DateUtil.convertDateString(roomScheduleEndTime);
		return conferenceRoomScheduleRepository.findAllByConferenceRoomIdAndDate(conferenceRoomId, startDate, endDate)
				.stream().map(conferenceRoomScheduleMapper::toDto).collect(Collectors.toCollection(LinkedList::new));

	}
}
