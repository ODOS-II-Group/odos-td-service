package gov.dhs.uscis.odos.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import gov.dhs.uscis.odos.service.BuildingService;
import gov.dhs.uscis.odos.service.dto.BuildingDTO;
import gov.dhs.uscis.odos.web.rest.errors.BadRequestAlertException;
import gov.dhs.uscis.odos.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Building.
 */
@RestController
@RequestMapping("/api")
public class BuildingResource {

    private final Logger log = LoggerFactory.getLogger(BuildingResource.class);

    private static final String ENTITY_NAME = "building";

    private final BuildingService buildingService;

    public BuildingResource(BuildingService buildingService) {
        this.buildingService = buildingService;
    }
   

    /**
     * POST  /buildings : Create a new building.
     *
     * @param buildingDTO the buildingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buildingDTO, or with status 400 (Bad Request) if the building has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/buildings")
    @Timed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BuildingDTO> createBuilding(@RequestBody BuildingDTO buildingDTO) throws URISyntaxException {
        log.debug("REST request to save Building : {}", buildingDTO);
        if (buildingDTO.getBuildingId() != null) {
            throw new BadRequestAlertException("A new building cannot already have an ID", ENTITY_NAME, "id exists");
        }
        BuildingDTO result = buildingService.save(buildingDTO);
        return ResponseEntity.created(new URI("/api/buildings/" + result.getBuildingId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getBuildingId().toString()))
            .body(result);
    }

    /**
     * PUT  /buildings : Updates an existing building.
     *
     * @param buildingDTO the buildingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buildingDTO,
     * or with status 400 (Bad Request) if the buildingDTO is not valid,
     * or with status 500 (Internal Server Error) if the buildingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/buildings")
    @Timed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BuildingDTO> updateBuilding(@RequestBody BuildingDTO buildingDTO) throws URISyntaxException {
        log.debug("REST request to update Building : {}", buildingDTO);
        if (buildingDTO.getBuildingId() == null) {
            return createBuilding(buildingDTO);
        }
        BuildingDTO result = buildingService.save(buildingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, buildingDTO.getBuildingId().toString()))
            .body(result);
    }

    /**
     * GET  /buildings : get all the buildings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of buildings in body
     */
    @GetMapping("/buildings")
    @Timed
    public List<BuildingDTO> getAllBuildings() {
        log.debug("REST request to get all Buildings");
        return buildingService.findAll();
        }

    /**
     * GET  /buildings/:id : get the "id" building.
     *
     * @param id the id of the buildingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buildingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/buildings/{id}")
    @Timed
    public ResponseEntity<BuildingDTO> getBuilding(@PathVariable Long id) {
        log.debug("REST request to get Building : {}", id);
        BuildingDTO buildingDTO = buildingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(buildingDTO));
    }

    /**
     * DELETE  /buildings/:id : delete the "id" building.
     *
     * @param id the id of the buildingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/buildings/{id}")
    @Timed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        log.debug("REST request to delete Building : {}", id);
        buildingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
