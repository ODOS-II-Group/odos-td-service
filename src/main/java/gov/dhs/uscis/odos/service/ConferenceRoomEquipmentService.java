package gov.dhs.uscis.odos.service;

import java.util.List;

import gov.dhs.uscis.odos.service.dto.ConferenceRoomEquipmentDTO;

/**
 * Service Interface for managing ConferenceRoomEquipment.
 */
public interface ConferenceRoomEquipmentService {

    /**
     * Save a conference room equipment.
     *
     * @param conferenceRoomDTO the entity to save
     * @return the persisted entity
     */
    ConferenceRoomEquipmentDTO save(ConferenceRoomEquipmentDTO conferenceRoomDTO);

    /**
     * Get all the conference room equipment.
     *
     * @return the list of entities
     */
    List<ConferenceRoomEquipmentDTO> findAll();

    /**
     * Get the "id" conference room equipment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ConferenceRoomEquipmentDTO findOne(Long id);

    /**
     * Delete the "id" conference room equipment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
