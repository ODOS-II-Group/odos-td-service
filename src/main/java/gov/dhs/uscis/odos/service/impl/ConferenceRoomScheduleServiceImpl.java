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
import java.util.GregorianCalendar;
import java.util.HashMap;

import java.util.Date;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Hours;
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
	public List<Integer> getRoomBookedTimeSlot(Long id, String date) {

		List<Integer> bookedSlot = new ArrayList<>();

		List<ConferenceRoomScheduleDTO> listBookedDTO = this.findAllByConferenceRoomIdAndDate(id, date);

		for (ConferenceRoomScheduleDTO bookedDTO : listBookedDTO) {

			Date start = DateUtil.convertDateTimeString(bookedDTO.getRoomScheduleStartTime());
			Date end = DateUtil.convertDateTimeString(bookedDTO.getRoomScheduleEndTime());

			DateTime startDate = new DateTime(start);
			DateTime endDate = new DateTime(end);

			int startHour = startDate.getHourOfDay();
			int startMinute = startDate.getMinuteOfHour();

			int endHour = endDate.getHourOfDay();
			int endMinute = endDate.getMinuteOfHour();

			bookedSlot.add(this.getBookSlot(startHour, startMinute, endHour, endMinute));

		}
		return bookedSlot;
	}

	private int getBookSlot(int startHour, int startMinute, int endHour, int endMinute) {


		int nextHour = startHour + 1;

		int bookedSlot = 0;

		switch (startHour) {

		case 9:
			bookedSlot = 1;
			if (startMinute == 0 && endMinute == 15) {

				return bookedSlot;

			} else if (startMinute == 15 && endMinute == 30) {

				return bookedSlot + 1;

			} else if (startMinute == 30 && endMinute == 45) {

				return bookedSlot + 2;

			} else if (endHour == nextHour && startMinute == 45 && endMinute == 0) {

				return bookedSlot + 3;

			}
			break;
		case 10:
			bookedSlot = 5;
			if (startMinute == 0 && endMinute == 15) {

				return bookedSlot;

			} else if (startMinute == 15 && endMinute == 30) {

				return bookedSlot + 1;

			} else if (startMinute == 30 && endMinute == 45) {

				return bookedSlot + 2;

			} else if (endHour == nextHour && startMinute == 45 && endMinute == 0) {

				return bookedSlot + 3;

			}
			break;
		case 11:
			bookedSlot = 9;
			if (startMinute == 0 && endMinute == 15) {

				return bookedSlot;

			} else if (startMinute == 15 && endMinute == 30) {

				return bookedSlot + 1;

			} else if (startMinute == 30 && endMinute == 45) {

				return bookedSlot + 2;

			} else if (endHour == nextHour && startMinute == 45 && endMinute == 0) {

				return bookedSlot + 3;

			}
			break;
		case 12:
			bookedSlot = 13;
			if (startMinute == 0 && endMinute == 15) {

				return bookedSlot;

			} else if (startMinute == 15 && endMinute == 30) {

				return bookedSlot + 1;

			} else if (startMinute == 30 && endMinute == 45) {

				return bookedSlot + 2;

			} else if (endHour == nextHour && startMinute == 45 && endMinute == 0) {

				return bookedSlot + 3;

			}
			break;
		case 13:
			bookedSlot = 17;
			if (startMinute == 0 && endMinute == 15) {

				return bookedSlot;

			} else if (startMinute == 15 && endMinute == 30) {

				return bookedSlot + 1;

			} else if (startMinute == 30 && endMinute == 45) {

				return bookedSlot + 2;

			} else if (endHour == nextHour && startMinute == 45 && endMinute == 0) {

				return bookedSlot + 3;

			}
			break;
		case 14:
			bookedSlot = 21;
			if (startMinute == 0 && endMinute == 15) {

				return bookedSlot;

			} else if (startMinute == 15 && endMinute == 30) {

				return bookedSlot + 1;

			} else if (startMinute == 30 && endMinute == 45) {

				return bookedSlot + 2;

			} else if (endHour == nextHour && startMinute == 45 && endMinute == 0) {

				return bookedSlot + 3;

			}
			break;
		case 15:
			bookedSlot = 25;
			if (startMinute == 0 && endMinute == 15) {

				return bookedSlot;

			} else if (startMinute == 15 && endMinute == 30) {

				return bookedSlot + 1;

			} else if (startMinute == 30 && endMinute == 45) {

				return bookedSlot + 2;

			} else if (endHour == nextHour && startMinute == 45 && endMinute == 0) {

				return bookedSlot + 3;

			}
			break;
		case 16:
			bookedSlot = 29;
			if (startMinute == 0 && endMinute == 15) {

				return bookedSlot;

			} else if (startMinute == 15 && endMinute == 30) {

				return bookedSlot + 1;

			} else if (startMinute == 30 && endMinute == 45) {

				return bookedSlot + 2;

			} else if (endHour == nextHour && startMinute == 45 && endMinute == 0) {

				return bookedSlot + 3;

			}
			break;
		case 17:
			bookedSlot = 32;
			if (startMinute == 0 && endMinute == 15) {

				return bookedSlot;

			} else if (startMinute == 15 && endMinute == 30) {

				return bookedSlot + 1;

			} else if (startMinute == 30 && endMinute == 45) {

				return bookedSlot + 2;

			} else if (endHour == nextHour && startMinute == 45 && endMinute == 0) {

				return bookedSlot + 3;

			}
			break;

		default:
			break;
		}

		return 0;

	}

	@Override
	public List<ConferenceRoomScheduleDTO> findAllByConferenceRoomIdAndDate(Long conferenceRoomId,
			String roomScheduleStartTime) {
		Date startDate = DateUtil.convertDateString(roomScheduleStartTime);
		return conferenceRoomScheduleRepository.findAllByConferenceRoomIdAndDate(conferenceRoomId, startDate).stream()
				.map(conferenceRoomScheduleMapper::toDto).collect(Collectors.toCollection(LinkedList::new));

	}
}
