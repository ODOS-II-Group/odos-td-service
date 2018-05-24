package gov.dhs.uscis.odos.web.rest;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import gov.dhs.uscis.odos.service.BuildingService;
import gov.dhs.uscis.odos.service.ConferenceRoomScheduleService;
import gov.dhs.uscis.odos.service.dto.BuildingDTO;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomScheduleDTO;

/**
 * REST controller for searching.
 */
@RestController
@RequestMapping("/api/search")
public class SearchResource {
	private final Logger log = LoggerFactory.getLogger(SearchResource.class);
	
	@Inject
	private BuildingService buildingService;
	
	@Inject
	private ConferenceRoomScheduleService crsService;
			
	/**
     * GET  /building/:buildingName : get the "buildingName" building.
     *
     * @param name the id of the BuildingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buildingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/building/{buildingName}")
    @Timed
    public List<BuildingDTO> getBuilding(@PathVariable String buildingName) {
        log.debug("REST request to get Building : {}", buildingName);
        return buildingService.findByName(buildingName);
    }
    
    /**
     * GET  /user/:requestorId : get the "requestorId" schedule.
     *
     * @param name the id of the ConferenceRoomScheduleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ConferenceRoomScheduleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user/{requestorId}")
    @Timed
    public List<ConferenceRoomScheduleDTO> getConferenceRoomSchedule(@PathVariable String requestorId) {
        log.debug("REST request to get Conference Room Schedule for requestor : {}", requestorId);
        return crsService.findByRequestId(requestorId);
    }
}
