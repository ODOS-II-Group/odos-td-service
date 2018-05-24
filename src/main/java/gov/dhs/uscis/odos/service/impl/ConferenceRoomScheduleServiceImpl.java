package gov.dhs.uscis.odos.service.impl;

import gov.dhs.uscis.odos.service.ConferenceRoomScheduleService;
import gov.dhs.uscis.odos.domain.ConferenceRoomSchedule;
import gov.dhs.uscis.odos.repository.ConferenceRoomRepository;
import gov.dhs.uscis.odos.repository.ConferenceRoomScheduleRepository;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomScheduleDTO;
import gov.dhs.uscis.odos.service.mapper.ConferenceRoomScheduleMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    public ConferenceRoomScheduleServiceImpl(ConferenceRoomScheduleRepository conferenceRoomScheduleRepository, ConferenceRoomScheduleMapper conferenceRoomScheduleMapper) {
        this.conferenceRoomScheduleRepository = conferenceRoomScheduleRepository;
        this.conferenceRoomScheduleMapper = conferenceRoomScheduleMapper;
    }

    /**
     * Save a conferenceRoomSchedule.
     *
     * @param conferenceRoomScheduleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConferenceRoomScheduleDTO save(ConferenceRoomScheduleDTO conferenceRoomScheduleDTO) {
        log.debug("Request to save ConferenceRoomSchedule : {}", conferenceRoomScheduleDTO);
        ConferenceRoomSchedule conferenceRoomSchedule = conferenceRoomScheduleMapper.toEntity(conferenceRoomScheduleDTO);
        conferenceRoomSchedule.setConferenceRoom(
        		conferenceRoomRepository.findOne(conferenceRoomScheduleDTO.getConferenceRoomId()));
        conferenceRoomSchedule = conferenceRoomScheduleRepository.save(conferenceRoomSchedule);
        return conferenceRoomScheduleMapper.toDto(conferenceRoomSchedule);
    }

    /**
     * Get all the conferenceRoomSchedules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConferenceRoomScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConferenceRoomSchedules");
        return conferenceRoomScheduleRepository.findAll(pageable)
            .map(conferenceRoomScheduleMapper::toDto);
    }

    /**
     * Get one conferenceRoomSchedule by id.
     *
     * @param id the id of the entity
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
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConferenceRoomSchedule : {}", id);
        conferenceRoomScheduleRepository.delete(id);
    }

	@Override
	public List<ConferenceRoomScheduleDTO> findByRequestId(String requestorId) {
		return conferenceRoomScheduleRepository.findByRequestorId(requestorId)
				.stream().map(conferenceRoomScheduleMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}
}
