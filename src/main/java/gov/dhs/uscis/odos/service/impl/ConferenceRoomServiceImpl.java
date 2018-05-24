package gov.dhs.uscis.odos.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dhs.uscis.odos.domain.ConferenceRoom;
import gov.dhs.uscis.odos.repository.ConferenceRoomRepository;
import gov.dhs.uscis.odos.service.ConferenceRoomService;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomDTO;
import gov.dhs.uscis.odos.service.mapper.ConferenceRoomMapper;

/**
 * Service Implementation for managing ConferenceRoom.
 */
@Service
@Transactional
public class ConferenceRoomServiceImpl implements ConferenceRoomService {

    private final Logger log = LoggerFactory.getLogger(ConferenceRoomServiceImpl.class);

    @Inject
    private  ConferenceRoomRepository conferenceRoomRepository;
    
    @Inject
    private ConferenceRoomMapper conferenceRoomMapper;


    /**
     * Save a conference room.
     *
     * @param conferenceRoomDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConferenceRoomDTO save(ConferenceRoomDTO conferenceRoomDTO) {
    	log.debug("Request to save Conference Room : {}", conferenceRoomDTO);
    	ConferenceRoom conferenceRoom = conferenceRoomRepository.save(conferenceRoomMapper.toEntity(conferenceRoomDTO));
        return conferenceRoomMapper.toDto(conferenceRoom);
    }

    /**
     * Get all the conference room.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConferenceRoomDTO> findAll() {
        log.debug("Request to get all Conference Room");
        return conferenceRoomRepository.findAll().stream()
            .map(conferenceRoomMapper::toDto)
           .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one conference room by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConferenceRoomDTO findOne(Long id) {
        log.debug("Request to get Conference Room : {}", id);
        ConferenceRoom conferenceRoom = conferenceRoomRepository.findOne(id);
        return conferenceRoomMapper.toDto(conferenceRoom);
    }

    /**
     * Delete the conference room by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Conference Room : {}", id);
        conferenceRoomRepository.delete(id);
    }
}
