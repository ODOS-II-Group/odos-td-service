package gov.dhs.uscis.odos.service;

import gov.dhs.uscis.odos.service.dto.ConferenceRoomScheduleDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ConferenceRoomSchedule.
 */
public interface ConferenceRoomScheduleService {

    /**
     * Save a conferenceRoomSchedule.
     *
     * @param conferenceRoomScheduleDTO the entity to save
     * @return the persisted entity
     */
    ConferenceRoomScheduleDTO save(ConferenceRoomScheduleDTO conferenceRoomScheduleDTO);

    /**
     * Get all the conferenceRoomSchedules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ConferenceRoomScheduleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" conferenceRoomSchedule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ConferenceRoomScheduleDTO findOne(Long id);

    /**
     * Delete the "id" conferenceRoomSchedule.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    List<ConferenceRoomScheduleDTO> findByRequestId(String requestorId);
}
