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

import gov.dhs.uscis.odos.service.ConferenceRoomEquipmentService;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomEquipmentDTO;
import gov.dhs.uscis.odos.web.rest.errors.BadRequestAlertException;
import gov.dhs.uscis.odos.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing ConferenceRoomEquipment.
 */
@RestController
@RequestMapping("/api")
public class ConferenceRoomEquipmentResource {

    private final Logger log = LoggerFactory.getLogger(ConferenceRoomEquipmentResource.class);

    private static final String ENTITY_NAME = "conferenceroomequip";

    private final ConferenceRoomEquipmentService conferenceRoomEquipService;

    public ConferenceRoomEquipmentResource(ConferenceRoomEquipmentService conferenceroomequipService) {
        this.conferenceRoomEquipService = conferenceroomequipService;
    }
   

    /**
     * POST  /conferenceroomequip : Create a new conferenceroomequip.
     *
     * @param conferenceroomequipDTO the conferenceroomequipDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conferenceroomequipDTO, or with status 400 (Bad Request) if the conferenceroomequip has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conferenceroomequip")
    @Timed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ConferenceRoomEquipmentDTO> createConferenceRoomEquipment(@RequestBody ConferenceRoomEquipmentDTO conferenceRoomEquipDTO) throws URISyntaxException {
        log.debug("REST request to save ConferenceRoomEquipment : {}", conferenceRoomEquipDTO);
        if (conferenceRoomEquipDTO.getConferenceRoomEquipId() != null) {
            throw new BadRequestAlertException("A new conferenceroomequip cannot already have an ID", ENTITY_NAME, "id exists");
        }
        ConferenceRoomEquipmentDTO result = conferenceRoomEquipService.save(conferenceRoomEquipDTO);
        return ResponseEntity.created(new URI("/api/conferenceroomequip/" + result.getConferenceRoomEquipId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getConferenceRoomEquipId().toString()))
            .body(result);
    }

    /**
     * PUT  /conferenceroomequip : Updates an existing conferenceroomequip.
     *
     * @param conferenceroomequipDTO the conferenceroomequipDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conferenceroomequipDTO,
     * or with status 400 (Bad Request) if the conferenceroomequipDTO is not valid,
     * or with status 500 (Internal Server Error) if the conferenceroomequipDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conferenceroomequip")
    @Timed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ConferenceRoomEquipmentDTO> updateConferenceRoomEquipment(@RequestBody ConferenceRoomEquipmentDTO conferenceroomequipDTO) throws URISyntaxException {
        log.debug("REST request to update ConferenceRoomEquipment : {}", conferenceroomequipDTO);
        if (conferenceroomequipDTO.getConferenceRoomEquipId() == null) {
            return createConferenceRoomEquipment(conferenceroomequipDTO);
        }
        ConferenceRoomEquipmentDTO result = conferenceRoomEquipService.save(conferenceroomequipDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conferenceroomequipDTO.getConferenceRoomEquipId().toString()))
            .body(result);
    }

    /**
     * GET  /conferenceroomequip : get all the conferenceroomequip.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of conferenceroomequip in body
     */
    @GetMapping("/conferenceroomequip")
    @Timed
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')") 
    public List<ConferenceRoomEquipmentDTO> getAllConferenceRoomEquipments() {
        log.debug("REST request to get all ConferenceRoomEquipments");
        return conferenceRoomEquipService.findAll();
        }

    /**
     * GET  /conferenceroomequip/:id : get the "id" conferenceroomequip.
     *
     * @param id the id of the conferenceroomequipDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conferenceroomequipDTO, or with status 404 (Not Found)
     */
    @GetMapping("/conferenceroomequip/{id}")
    @Timed
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')") 
    public ResponseEntity<ConferenceRoomEquipmentDTO> getConferenceRoomEquipment(@PathVariable Long id) {
        log.debug("REST request to get ConferenceRoomEquipment : {}", id);
        ConferenceRoomEquipmentDTO conferenceroomequipDTO = conferenceRoomEquipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(conferenceroomequipDTO));
    }

    /**
     * DELETE  /conferenceroomequip/:id : delete the "id" conferenceroomequip.
     *
     * @param id the id of the conferenceroomequipDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conferenceroomequip/{id}")
    @Timed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteConferenceRoomEquipment(@PathVariable Long id) {
        log.debug("REST request to delete ConferenceRoomEquipment : {}", id);
        conferenceRoomEquipService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
