package gov.dhs.uscis.odos.service;

import gov.dhs.uscis.odos.service.dto.BuildingDTO;
import java.util.List;

/**
 * Service Interface for managing Building.
 */
public interface BuildingService {

    /**
     * Save a building.
     *
     * @param buildingDTO the entity to save
     * @return the persisted entity
     */
    BuildingDTO save(BuildingDTO buildingDTO);

    /**
     * Get all the buildings.
     *
     * @return the list of entities
     */
    List<BuildingDTO> findAll();

    /**
     * Get the "id" building.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BuildingDTO findOne(Long id);

    /**
     * Delete the "id" building.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    List<BuildingDTO> findByName(String buildingName);
}
