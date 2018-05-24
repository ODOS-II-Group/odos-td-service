package gov.dhs.uscis.odos.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dhs.uscis.odos.domain.ConferenceRoomEquipment;
import gov.dhs.uscis.odos.repository.ConferenceRoomEquipmentRepository;
import gov.dhs.uscis.odos.service.ConferenceRoomEquipmentService;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomEquipmentDTO;
import gov.dhs.uscis.odos.service.mapper.ConferenceRoomEquipmentMapper;

/**
 * Service Implementation for managing Equipment.
 */
@Service
@Transactional
public class ConferenceRoomEquipmentServiceImpl implements ConferenceRoomEquipmentService {

    private final Logger log = LoggerFactory.getLogger(ConferenceRoomEquipmentServiceImpl.class);

    @Inject
    private  ConferenceRoomEquipmentRepository conferenceRoomEquipRepository;
    
    @Inject
    private ConferenceRoomEquipmentMapper conferenceRoomEquipMapper;


    /**
     * Save a conference room equipment.
     *
     * @param conferenceRoomEquipmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConferenceRoomEquipmentDTO save(ConferenceRoomEquipmentDTO conferenceRoomEquipmentDTO) {
    	log.debug("Request to save ConferenceRoomEquipment : {}", conferenceRoomEquipmentDTO);
    	ConferenceRoomEquipment conferenceRoomEquip = conferenceRoomEquipRepository.save(conferenceRoomEquipMapper.toEntity(conferenceRoomEquipmentDTO));
        return conferenceRoomEquipMapper.toDto(conferenceRoomEquip);
    }

    /**
     * Get all the conference room equipment.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConferenceRoomEquipmentDTO> findAll() {
        log.debug("Request to get all ConferenceRoomEquipment");
        return conferenceRoomEquipRepository.findAll().stream()
            .map(conferenceRoomEquipMapper::toDto)
           .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one conference room equipment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConferenceRoomEquipmentDTO findOne(Long id) {
        log.debug("Request to get ConferenceRoomEquipment : {}", id);
        ConferenceRoomEquipment conferenceRoomEquip = conferenceRoomEquipRepository.findOne(id);
        return conferenceRoomEquipMapper.toDto(conferenceRoomEquip);
    }

    /**
     * Delete the conference room equipment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConferenceRoomEquipment : {}", id);
        conferenceRoomEquipRepository.delete(id);
    }
}
