package gov.dhs.uscis.odos.service;

import java.util.List;

import gov.dhs.uscis.odos.service.dto.EquipmentDTO;

/**
 * Service Interface for managing Equipment.
 */
public interface EquipmentService {

    /**
     * Save an equipment.
     *
     * @param equipmentDTO the entity to save
     * @return the persisted entity
     */
    EquipmentDTO save(EquipmentDTO equipmentDTO);

    /**
     * Get all the equipment.
     *
     * @return the list of entities
     */
    List<EquipmentDTO> findAll();

    /**
     * Get the "id" equipment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EquipmentDTO findOne(Long id);

    /**
     * Delete the "id" equipment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
