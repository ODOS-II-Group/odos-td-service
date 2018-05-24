package gov.dhs.uscis.odos.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

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

import gov.dhs.uscis.odos.service.ConferenceRoomService;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomDTO;
import gov.dhs.uscis.odos.web.rest.errors.BadRequestAlertException;
import gov.dhs.uscis.odos.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Conference Room.
 */
@RestController
@RequestMapping("/api")
public class ConferenceRoomResource {

    private final Logger log = LoggerFactory.getLogger(ConferenceRoomResource.class);

    private static final String ENTITY_NAME = "conferenceroom";

    @Inject
    private  ConferenceRoomService conferenceRoomService;
   

    /**
     * POST  /conferenceroom : Create a new conference room.
     *
     * @param ConferenceRoomDTO the ConferenceRoomDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ConferenceRoomDTO, or with status 400 (Bad Request) if the conference room has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conferenceroom")
    @Timed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ConferenceRoomDTO> createConferenceRoom(@RequestBody ConferenceRoomDTO ConferenceRoomDTO) throws URISyntaxException {
        log.debug("REST request to save Conference Room : {}", ConferenceRoomDTO);
        if (ConferenceRoomDTO.getConferenceRoomId() != null) {
            throw new BadRequestAlertException("A new conference room cannot already have an ID", ENTITY_NAME, "id exists");
        }
        ConferenceRoomDTO result = conferenceRoomService.save(ConferenceRoomDTO);
        return ResponseEntity.created(new URI("/api/conferenceroom/" + result.getConferenceRoomId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getConferenceRoomId().toString()))
            .body(result);
    }

    /**
     * PUT  /conferenceroom : Updates an existing conference rooms.
     *
     * @param ConferenceRoomDTO the ConferenceRoomDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ConferenceRoomDTO,
     * or with status 400 (Bad Request) if the ConferenceRoomDTO is not valid,
     * or with status 500 (Internal Server Error) if the ConferenceRoomDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conferenceroom")
    @Timed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ConferenceRoomDTO> updateConferenceRoom(@RequestBody ConferenceRoomDTO ConferenceRoomDTO) throws URISyntaxException {
        log.debug("REST request to update Conference Room : {}", ConferenceRoomDTO);
        if (ConferenceRoomDTO.getConferenceRoomId() == null) {
            return createConferenceRoom(ConferenceRoomDTO);
        }
        ConferenceRoomDTO result = conferenceRoomService.save(ConferenceRoomDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ConferenceRoomDTO.getConferenceRoomId().toString()))
            .body(result);
    }

    /**
     * GET  /conferenceroom : get all the conference rooms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of conference rooms in body
     */
    @GetMapping("/conferenceroom")
    @Timed
    public List<ConferenceRoomDTO> getAllConferenceRooms() {
        log.debug("REST request to get all Conference Rooms");
        return conferenceRoomService.findAll();
        }

    /**
     * GET  /conferenceroom/:id : get the "id" conference room.
     *
     * @param id the id of the ConferenceRoomDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ConferenceRoomDTO, or with status 404 (Not Found)
     */
    @GetMapping("/conferenceroom/{id}")
    @Timed
    public ResponseEntity<ConferenceRoomDTO> getConferenceRooms(@PathVariable Long id) {
        log.debug("REST request to get Conference Room : {}", id);
        ConferenceRoomDTO ConferenceRoomDTO = conferenceRoomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ConferenceRoomDTO));
    }

    /**
     * DELETE  /conferenceroom/:id : delete the "id" conference room.
     *
     * @param id the id of the ConferenceRoomDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conferenceroom/{id}")
    @Timed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteConferenceRoom(@PathVariable Long id) {
        log.debug("REST request to delete Conference Room : {}", id);
        conferenceRoomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
