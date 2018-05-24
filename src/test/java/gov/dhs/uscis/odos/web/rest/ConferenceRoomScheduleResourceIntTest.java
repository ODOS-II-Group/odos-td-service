package gov.dhs.uscis.odos.web.rest;

import static gov.dhs.uscis.odos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import gov.dhs.uscis.odos.base.test.BaseIntegrationTest;
import gov.dhs.uscis.odos.domain.Building;
import gov.dhs.uscis.odos.domain.ConferenceRoom;
import gov.dhs.uscis.odos.domain.ConferenceRoomSchedule;
import gov.dhs.uscis.odos.repository.ConferenceRoomScheduleRepository;
import gov.dhs.uscis.odos.service.ConferenceRoomScheduleService;
import gov.dhs.uscis.odos.service.dto.ConferenceRoomScheduleDTO;
import gov.dhs.uscis.odos.service.mapper.ConferenceRoomScheduleMapper;
import gov.dhs.uscis.odos.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the ConferenceRoomScheduleResource REST controller.
 *
 * @see ConferenceRoomScheduleResource
 */
public class ConferenceRoomScheduleResourceIntTest extends BaseIntegrationTest {

    private static final String DEFAULT_REQUESTOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTOR_ID = "BBBBBBBBBB";

    private final String DEFAULT_ROOM_SCHEDULE_START_TIME = "2018-04-19 10:00";
    private final String UPDATED_ROOM_SCHEDULE_START_TIME = "2018-04-19 11:00";

    private static final String DEFAULT_ROOM_SCHEDULE_END_TIME = "2018-04-19 12:00";
    private static final String UPDATED_ROOM_SCHEDULE_END_TIME = "2018-04-19 13:00";

    private static final String DEFAULT_CONFERENCE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_CONFERENCE_TITLE = "BBBBBBBBBB";

    private static final Logger log  = LoggerFactory.getLogger(ConferenceRoomScheduleResourceIntTest.class);
    
    @Autowired
    private ConferenceRoomScheduleRepository conferenceRoomScheduleRepository;

    @Autowired
    private ConferenceRoomScheduleMapper conferenceRoomScheduleMapper;

    @Autowired
    private ConferenceRoomScheduleService conferenceRoomScheduleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConferenceRoomScheduleMockMvc;

    private ConferenceRoomSchedule conferenceRoomSchedule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConferenceRoomScheduleResource conferenceRoomScheduleResource = new ConferenceRoomScheduleResource(conferenceRoomScheduleService);
        this.restConferenceRoomScheduleMockMvc = MockMvcBuilders.standaloneSetup(conferenceRoomScheduleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public ConferenceRoomSchedule createEntity(EntityManager em) {
        ConferenceRoomSchedule conferenceRoomSchedule = new ConferenceRoomSchedule()
            .requestorId(DEFAULT_REQUESTOR_ID)
            .roomScheduleStartTime(convertDateString(DEFAULT_ROOM_SCHEDULE_START_TIME, "yyyy-MM-dd HH:mm"))
            .roomScheduleEndTime(convertDateString(DEFAULT_ROOM_SCHEDULE_END_TIME, "yyyy-MM-dd HH:mm"))
            .conferenceTitle(DEFAULT_CONFERENCE_TITLE);
        
		Building building = new Building();
		building.setBuildingDesc("Building One");
		building.setBuildingName("BLDG1");
		
		ConferenceRoom room = new ConferenceRoom();
		room.setRoomName("ATOMICS");
		room.setRoomNum("23");
		room.setRoomCapacity(10);
		room.setBuilding(building);

        conferenceRoomSchedule.setConferenceRoom(room);
        
        return conferenceRoomSchedule;
    }

    @Before
    public void initTest() {
        conferenceRoomSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createConferenceRoomSchedule() throws Exception {
        int databaseSizeBeforeCreate = conferenceRoomScheduleRepository.findAll().size();

        // Create the ConferenceRoomSchedule
        ConferenceRoomScheduleDTO conferenceRoomScheduleDTO = conferenceRoomScheduleMapper.toDto(conferenceRoomSchedule);
        restConferenceRoomScheduleMockMvc.perform(post("/api/conference-room-schedule")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceRoomScheduleDTO)))
            .andExpect(status().is5xxServerError());

    }

    @Test
    @Transactional
    public void createConferenceRoomScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conferenceRoomScheduleRepository.findAll().size();

        // Create the ConferenceRoomSchedule with an existing ID
        conferenceRoomSchedule.setId(1L);
        ConferenceRoomScheduleDTO conferenceRoomScheduleDTO = conferenceRoomScheduleMapper.toDto(conferenceRoomSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConferenceRoomScheduleMockMvc.perform(post("/api/conference-room-schedule")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceRoomScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConferenceRoomSchedule in the database
        List<ConferenceRoomSchedule> conferenceRoomScheduleList = conferenceRoomScheduleRepository.findAll();
        assertThat(conferenceRoomScheduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRequestorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = conferenceRoomScheduleRepository.findAll().size();
        // set the field null
        conferenceRoomSchedule.setRequestorId(null);

        // Create the ConferenceRoomSchedule, which fails.
        ConferenceRoomScheduleDTO conferenceRoomScheduleDTO = conferenceRoomScheduleMapper.toDto(conferenceRoomSchedule);

        restConferenceRoomScheduleMockMvc.perform(post("/api/conference-room-schedule")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceRoomScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<ConferenceRoomSchedule> conferenceRoomScheduleList = conferenceRoomScheduleRepository.findAll();
        assertThat(conferenceRoomScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoomScheduleStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = conferenceRoomScheduleRepository.findAll().size();
        // set the field null
        conferenceRoomSchedule.setRoomScheduleStartTime(null);

        // Create the ConferenceRoomSchedule, which fails.
        ConferenceRoomScheduleDTO conferenceRoomScheduleDTO = conferenceRoomScheduleMapper.toDto(conferenceRoomSchedule);

        restConferenceRoomScheduleMockMvc.perform(post("/api/conference-room-schedule")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceRoomScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<ConferenceRoomSchedule> conferenceRoomScheduleList = conferenceRoomScheduleRepository.findAll();
        assertThat(conferenceRoomScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoomScheduleEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = conferenceRoomScheduleRepository.findAll().size();
        // set the field null
        conferenceRoomSchedule.setRoomScheduleEndTime(null);

        // Create the ConferenceRoomSchedule, which fails.
        ConferenceRoomScheduleDTO conferenceRoomScheduleDTO = conferenceRoomScheduleMapper.toDto(conferenceRoomSchedule);

        restConferenceRoomScheduleMockMvc.perform(post("/api/conference-room-schedule")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceRoomScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<ConferenceRoomSchedule> conferenceRoomScheduleList = conferenceRoomScheduleRepository.findAll();
        assertThat(conferenceRoomScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConferenceRoomSchedules() throws Exception {
        // Initialize the database
        conferenceRoomScheduleRepository.saveAndFlush(conferenceRoomSchedule);

        // Get all the conferenceRoomScheduleList
        restConferenceRoomScheduleMockMvc.perform(get("/api/conference-room-schedule?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conferenceRoomSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestorId").value(hasItem(DEFAULT_REQUESTOR_ID.toString())))
            .andExpect(jsonPath("$.[*].roomScheduleStartTime").exists())
            .andExpect(jsonPath("$.[*].roomScheduleEndTime").exists())
            .andExpect(jsonPath("$.[*].conferenceTitle").value(hasItem(DEFAULT_CONFERENCE_TITLE.toString())));
    }

    @Test
    @Transactional
    public void getConferenceRoomSchedule() throws Exception {
        // Initialize the database
        conferenceRoomScheduleRepository.saveAndFlush(conferenceRoomSchedule);

        // Get the conferenceRoomSchedule
        restConferenceRoomScheduleMockMvc.perform(get("/api/conference-room-schedule/{id}", conferenceRoomSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conferenceRoomSchedule.getId().intValue()))
            .andExpect(jsonPath("$.requestorId").value(DEFAULT_REQUESTOR_ID.toString()))
            .andExpect(jsonPath("$.roomScheduleStartTime").exists())
            .andExpect(jsonPath("$.roomScheduleEndTime").exists())
            .andExpect(jsonPath("$.conferenceTitle").value(DEFAULT_CONFERENCE_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConferenceRoomSchedule() throws Exception {
        // Get the conferenceRoomSchedule
        restConferenceRoomScheduleMockMvc.perform(get("/api/conference-room-schedule/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConferenceRoomSchedule() throws Exception {
        // Initialize the database
        conferenceRoomScheduleRepository.saveAndFlush(conferenceRoomSchedule);
        int databaseSizeBeforeUpdate = conferenceRoomScheduleRepository.findAll().size();

        // Update the conferenceRoomSchedule
        ConferenceRoomSchedule updatedConferenceRoomSchedule = conferenceRoomScheduleRepository.findOne(conferenceRoomSchedule.getId());
        // Disconnect from session so that the updates on updatedConferenceRoomSchedule are not directly saved in db
        em.detach(updatedConferenceRoomSchedule);
        updatedConferenceRoomSchedule
            .requestorId(UPDATED_REQUESTOR_ID)
            .roomScheduleStartTime(convertDateString(UPDATED_ROOM_SCHEDULE_START_TIME, "yyyy-MM-dd HH:mm"))
            .roomScheduleEndTime(convertDateString(UPDATED_ROOM_SCHEDULE_END_TIME, "yyyy-MM-dd HH:mm"))
            .conferenceTitle(UPDATED_CONFERENCE_TITLE);
        ConferenceRoomScheduleDTO conferenceRoomScheduleDTO = conferenceRoomScheduleMapper.toDto(updatedConferenceRoomSchedule);

        restConferenceRoomScheduleMockMvc.perform(put("/api/conference-room-schedule")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceRoomScheduleDTO)))
            .andExpect(status().is5xxServerError());

    }

    @Test
    @Transactional
    public void updateNonExistingConferenceRoomSchedule() throws Exception {
        int databaseSizeBeforeUpdate = conferenceRoomScheduleRepository.findAll().size();

        // Create the ConferenceRoomSchedule
        ConferenceRoomScheduleDTO conferenceRoomScheduleDTO = conferenceRoomScheduleMapper.toDto(conferenceRoomSchedule);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConferenceRoomScheduleMockMvc.perform(put("/api/conference-room-schedule")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conferenceRoomScheduleDTO)))
            .andExpect(status().is5xxServerError());

    }

    @Test
    @Transactional
    public void deleteConferenceRoomSchedule() throws Exception {
        // Initialize the database
        conferenceRoomScheduleRepository.saveAndFlush(conferenceRoomSchedule);
        int databaseSizeBeforeDelete = conferenceRoomScheduleRepository.findAll().size();

        // Get the conferenceRoomSchedule
        restConferenceRoomScheduleMockMvc.perform(delete("/api/conference-room-schedule/{id}", conferenceRoomSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConferenceRoomSchedule> conferenceRoomScheduleList = conferenceRoomScheduleRepository.findAll();
        assertThat(conferenceRoomScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConferenceRoomSchedule.class);
        ConferenceRoomSchedule conferenceRoomSchedule1 = new ConferenceRoomSchedule();
        conferenceRoomSchedule1.setId(1L);
        ConferenceRoomSchedule conferenceRoomSchedule2 = new ConferenceRoomSchedule();
        conferenceRoomSchedule2.setId(conferenceRoomSchedule1.getId());
        assertThat(conferenceRoomSchedule1).isEqualTo(conferenceRoomSchedule2);
        conferenceRoomSchedule2.setId(2L);
        assertThat(conferenceRoomSchedule1).isNotEqualTo(conferenceRoomSchedule2);
        conferenceRoomSchedule1.setId(null);
        assertThat(conferenceRoomSchedule1).isNotEqualTo(conferenceRoomSchedule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConferenceRoomScheduleDTO.class);
        ConferenceRoomScheduleDTO conferenceRoomScheduleDTO1 = new ConferenceRoomScheduleDTO();
        conferenceRoomScheduleDTO1.setId(1L);
        ConferenceRoomScheduleDTO conferenceRoomScheduleDTO2 = new ConferenceRoomScheduleDTO();
        assertThat(conferenceRoomScheduleDTO1).isNotEqualTo(conferenceRoomScheduleDTO2);
        conferenceRoomScheduleDTO2.setId(conferenceRoomScheduleDTO1.getId());
        assertThat(conferenceRoomScheduleDTO1).isEqualTo(conferenceRoomScheduleDTO2);
        conferenceRoomScheduleDTO2.setId(2L);
        assertThat(conferenceRoomScheduleDTO1).isNotEqualTo(conferenceRoomScheduleDTO2);
        conferenceRoomScheduleDTO1.setId(null);
        assertThat(conferenceRoomScheduleDTO1).isNotEqualTo(conferenceRoomScheduleDTO2);
    }

	private Date convertDateString(String dateStr, String format) {
		Date dateValue = null;
		try {
			dateValue = DateUtils.parseDate(dateStr, format);
		}
		catch(ParseException e) {
			log.error("Error parsing date value " + dateStr, e);
			throw new RuntimeException(e);
		}
		return dateValue;
	}
	
	private String convertDateValue(Date dateValue, String format) {
		Instant instant = dateValue.toInstant();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		return localDateTime.toString();
	}
  
}
