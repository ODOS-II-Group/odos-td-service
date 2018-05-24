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

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import gov.dhs.uscis.odos.base.test.BaseIntegrationTest;
import gov.dhs.uscis.odos.domain.Building;
import gov.dhs.uscis.odos.repository.BuildingRepository;
import gov.dhs.uscis.odos.service.BuildingService;
import gov.dhs.uscis.odos.service.dto.BuildingDTO;
import gov.dhs.uscis.odos.service.mapper.BuildingMapper;
import gov.dhs.uscis.odos.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the BuildingResource REST controller.
 *
 * @see BuildingResource
 */
public class BuildingResourceIntTest extends BaseIntegrationTest {

    private static final String DEFAULT_BUILDING_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUILDING_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BUILDING_DESC = "AAAAAAAAAA";
    private static final String UPDATED_BUILDING_DESC = "BBBBBBBBBB";

    @Inject
    private BuildingRepository buildingRepository;

    @Inject
    private BuildingMapper buildingMapper;

    @Inject
    private BuildingService buildingService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private ExceptionTranslator exceptionTranslator;

    @Inject
    private EntityManager em;

    private MockMvc restBuildingMockMvc;

    private Building building;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BuildingResource buildingResource = new BuildingResource(buildingService);
        this.restBuildingMockMvc = MockMvcBuilders.standaloneSetup(buildingResource)
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
    public static Building createEntity(EntityManager em) {
        Building building = new Building()
            .buildingName(DEFAULT_BUILDING_NAME)
            .buildingDesc(DEFAULT_BUILDING_DESC);
        return building;
    }

    @Before
    public void initTest() {
        building = createEntity(em);
    }
    
    @Test
    @Transactional
    public void createBuilding() throws Exception {
        int databaseSizeBeforeCreate = buildingRepository.findAll().size();

        // Create the Building
        BuildingDTO buildingDTO = buildingMapper.toDto(building);
        restBuildingMockMvc.perform(post("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
            .andExpect(status().isCreated());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeCreate + 1);
        Building testBuilding = buildingList.get(buildingList.size() - 1);
        //assertThat(testBuilding.getBuildingId()).isEqualTo(DEFAULT_BUILD_ID);
        assertThat(testBuilding.getBuildingName()).isEqualTo(DEFAULT_BUILDING_NAME);
        assertThat(testBuilding.getBuildingDesc()).isEqualTo(DEFAULT_BUILDING_DESC);
    }

    @Test
    @Transactional
    public void createBuildingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = buildingRepository.findAll().size();

        // Create the Building with an existing ID
        building.setBuildingId(1L);
        BuildingDTO buildingDTO = buildingMapper.toDto(building);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildingMockMvc.perform(post("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeCreate);
    }

    
    
    @Test
    @Transactional
    public void getAllBuildings() throws Exception {
        // Initialize the database
        Building updatedBldg = buildingRepository.saveAndFlush(building);

        BuildingDTO buildingDTO = buildingMapper.toDto(updatedBldg);
        
        // Get all the buildingList
        restBuildingMockMvc.perform(get("/api/buildings?sort=id,desc").content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].buildingId").value(hasItem(updatedBldg.getBuildingId().intValue())))
            .andExpect(jsonPath("$.[*].buildingName").value(hasItem(DEFAULT_BUILDING_NAME.toString())))
            .andExpect(jsonPath("$.[*].buildingDesc").value(hasItem(DEFAULT_BUILDING_DESC.toString())));
    }

    @Test
    @Transactional
    public void getBuilding() throws Exception {
        // Initialize the database
        Building updatedBuilding = buildingRepository.saveAndFlush(building);

        // Get the building
        restBuildingMockMvc.perform(get("/api/buildings/{id}", updatedBuilding.getBuildingId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.buildingId").value(updatedBuilding.getBuildingId()))
            .andExpect(jsonPath("$.buildingName").value(DEFAULT_BUILDING_NAME.toString()))
            .andExpect(jsonPath("$.buildingDesc").value(DEFAULT_BUILDING_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBuilding() throws Exception {
        // Get the building
        restBuildingMockMvc.perform(get("/api/buildings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuilding() throws Exception {
        // Initialize the database
    	Building updatedBuilding = buildingRepository.saveAndFlush(building);
        int databaseSizeBeforeUpdate = buildingRepository.findAll().size();

        // Update the building
        updatedBuilding = buildingRepository.findOne(updatedBuilding.getBuildingId());
        // Disconnect from session so that the updates on updatedBuilding are not directly saved in db
        //em.detach(updatedBuilding);
        updatedBuilding
            .buildingName(UPDATED_BUILDING_NAME)
            .buildingDesc(UPDATED_BUILDING_DESC);
        BuildingDTO buildingDTO = buildingMapper.toDto(updatedBuilding);

        restBuildingMockMvc.perform(put("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
            .andExpect(status().isOk());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeUpdate);
        Building testBuilding = buildingList.get(buildingList.size() - 1);
        assertThat(testBuilding.getBuildingId()).isEqualTo(updatedBuilding.getBuildingId());
        assertThat(testBuilding.getBuildingName()).isEqualTo(UPDATED_BUILDING_NAME);
        assertThat(testBuilding.getBuildingDesc()).isEqualTo(UPDATED_BUILDING_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingBuilding() throws Exception {
        int databaseSizeBeforeUpdate = buildingRepository.findAll().size();

        // Create the Building
        BuildingDTO buildingDTO = buildingMapper.toDto(building);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBuildingMockMvc.perform(put("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
            .andExpect(status().is2xxSuccessful());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);
        int databaseSizeBeforeDelete = buildingRepository.findAll().size();

        // Get the building
        restBuildingMockMvc.perform(delete("/api/buildings/{id}", building.getBuildingId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Building.class);
        Building building1 = new Building();
        building1.setBuildingId(1L);
        Building building2 = new Building();
        building2.setBuildingId(building1.getBuildingId());
        assertThat(building1).isEqualTo(building2);
        building2.setBuildingId(2L);
        assertThat(building1).isNotEqualTo(building2);
        building1.setBuildingId(null);
        assertThat(building1).isNotEqualTo(building2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildingDTO.class);
        BuildingDTO buildingDTO1 = new BuildingDTO();
        buildingDTO1.setBuildingId(1L);
        BuildingDTO buildingDTO2 = new BuildingDTO();
        assertThat(buildingDTO1).isNotEqualTo(buildingDTO2);
        buildingDTO2.setBuildingId(buildingDTO1.getBuildingId());
        assertThat(buildingDTO1).isEqualTo(buildingDTO2);
        buildingDTO2.setBuildingId(2L);
        assertThat(buildingDTO1).isNotEqualTo(buildingDTO2);
        buildingDTO1.setBuildingId(null);
        assertThat(buildingDTO1).isNotEqualTo(buildingDTO2);
    }
}
