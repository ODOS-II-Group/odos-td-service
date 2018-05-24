package gov.dhs.uscis.odos.service;

import java.util.List;

import gov.dhs.uscis.odos.service.dto.ConferenceRoomDTO;

/**
 * Service Interface for managing ConferenceRoom.
 */
public interface ConferenceRoomService {

    /**
     * Save a conference room.
     *
     * @param conferenceRoomDTO the entity to save
     * @return the persisted entity
     */
    ConferenceRoomDTO save(ConferenceRoomDTO conferenceRoomDTO);

    /**
     * Get all the conference rooms.
     *
     * @return the list of entities
     */
    List<ConferenceRoomDTO> findAll();

    /**
     * Get the "id" conference room.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ConferenceRoomDTO findOne(Long id);

    /**
     * Delete the "id" conference room.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
