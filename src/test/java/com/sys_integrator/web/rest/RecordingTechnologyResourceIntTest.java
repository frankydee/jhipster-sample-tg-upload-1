package com.sys_integrator.web.rest;

import com.sys_integrator.TgUpload1App;

import com.sys_integrator.domain.RecordingTechnology;
import com.sys_integrator.repository.RecordingTechnologyRepository;
import com.sys_integrator.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.sys_integrator.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RecordingTechnologyResource REST controller.
 *
 * @see RecordingTechnologyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TgUpload1App.class)
public class RecordingTechnologyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RecordingTechnologyRepository recordingTechnologyRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecordingTechnologyMockMvc;

    private RecordingTechnology recordingTechnology;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecordingTechnologyResource recordingTechnologyResource = new RecordingTechnologyResource(recordingTechnologyRepository);
        this.restRecordingTechnologyMockMvc = MockMvcBuilders.standaloneSetup(recordingTechnologyResource)
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
    public static RecordingTechnology createEntity(EntityManager em) {
        RecordingTechnology recordingTechnology = new RecordingTechnology()
            .name(DEFAULT_NAME)
            .sortOrder(DEFAULT_SORT_ORDER)
            .active(DEFAULT_ACTIVE)
            .createDate(DEFAULT_CREATE_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return recordingTechnology;
    }

    @Before
    public void initTest() {
        recordingTechnology = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecordingTechnology() throws Exception {
        int databaseSizeBeforeCreate = recordingTechnologyRepository.findAll().size();

        // Create the RecordingTechnology
        restRecordingTechnologyMockMvc.perform(post("/api/recording-technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordingTechnology)))
            .andExpect(status().isCreated());

        // Validate the RecordingTechnology in the database
        List<RecordingTechnology> recordingTechnologyList = recordingTechnologyRepository.findAll();
        assertThat(recordingTechnologyList).hasSize(databaseSizeBeforeCreate + 1);
        RecordingTechnology testRecordingTechnology = recordingTechnologyList.get(recordingTechnologyList.size() - 1);
        assertThat(testRecordingTechnology.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecordingTechnology.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testRecordingTechnology.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testRecordingTechnology.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testRecordingTechnology.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createRecordingTechnologyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recordingTechnologyRepository.findAll().size();

        // Create the RecordingTechnology with an existing ID
        recordingTechnology.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecordingTechnologyMockMvc.perform(post("/api/recording-technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordingTechnology)))
            .andExpect(status().isBadRequest());

        // Validate the RecordingTechnology in the database
        List<RecordingTechnology> recordingTechnologyList = recordingTechnologyRepository.findAll();
        assertThat(recordingTechnologyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordingTechnologyRepository.findAll().size();
        // set the field null
        recordingTechnology.setName(null);

        // Create the RecordingTechnology, which fails.

        restRecordingTechnologyMockMvc.perform(post("/api/recording-technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordingTechnology)))
            .andExpect(status().isBadRequest());

        List<RecordingTechnology> recordingTechnologyList = recordingTechnologyRepository.findAll();
        assertThat(recordingTechnologyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecordingTechnologies() throws Exception {
        // Initialize the database
        recordingTechnologyRepository.saveAndFlush(recordingTechnology);

        // Get all the recordingTechnologyList
        restRecordingTechnologyMockMvc.perform(get("/api/recording-technologies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recordingTechnology.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getRecordingTechnology() throws Exception {
        // Initialize the database
        recordingTechnologyRepository.saveAndFlush(recordingTechnology);

        // Get the recordingTechnology
        restRecordingTechnologyMockMvc.perform(get("/api/recording-technologies/{id}", recordingTechnology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recordingTechnology.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecordingTechnology() throws Exception {
        // Get the recordingTechnology
        restRecordingTechnologyMockMvc.perform(get("/api/recording-technologies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecordingTechnology() throws Exception {
        // Initialize the database
        recordingTechnologyRepository.saveAndFlush(recordingTechnology);

        int databaseSizeBeforeUpdate = recordingTechnologyRepository.findAll().size();

        // Update the recordingTechnology
        RecordingTechnology updatedRecordingTechnology = recordingTechnologyRepository.findById(recordingTechnology.getId()).get();
        // Disconnect from session so that the updates on updatedRecordingTechnology are not directly saved in db
        em.detach(updatedRecordingTechnology);
        updatedRecordingTechnology
            .name(UPDATED_NAME)
            .sortOrder(UPDATED_SORT_ORDER)
            .active(UPDATED_ACTIVE)
            .createDate(UPDATED_CREATE_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restRecordingTechnologyMockMvc.perform(put("/api/recording-technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecordingTechnology)))
            .andExpect(status().isOk());

        // Validate the RecordingTechnology in the database
        List<RecordingTechnology> recordingTechnologyList = recordingTechnologyRepository.findAll();
        assertThat(recordingTechnologyList).hasSize(databaseSizeBeforeUpdate);
        RecordingTechnology testRecordingTechnology = recordingTechnologyList.get(recordingTechnologyList.size() - 1);
        assertThat(testRecordingTechnology.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecordingTechnology.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testRecordingTechnology.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testRecordingTechnology.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testRecordingTechnology.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRecordingTechnology() throws Exception {
        int databaseSizeBeforeUpdate = recordingTechnologyRepository.findAll().size();

        // Create the RecordingTechnology

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecordingTechnologyMockMvc.perform(put("/api/recording-technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordingTechnology)))
            .andExpect(status().isBadRequest());

        // Validate the RecordingTechnology in the database
        List<RecordingTechnology> recordingTechnologyList = recordingTechnologyRepository.findAll();
        assertThat(recordingTechnologyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecordingTechnology() throws Exception {
        // Initialize the database
        recordingTechnologyRepository.saveAndFlush(recordingTechnology);

        int databaseSizeBeforeDelete = recordingTechnologyRepository.findAll().size();

        // Get the recordingTechnology
        restRecordingTechnologyMockMvc.perform(delete("/api/recording-technologies/{id}", recordingTechnology.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RecordingTechnology> recordingTechnologyList = recordingTechnologyRepository.findAll();
        assertThat(recordingTechnologyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecordingTechnology.class);
        RecordingTechnology recordingTechnology1 = new RecordingTechnology();
        recordingTechnology1.setId(1L);
        RecordingTechnology recordingTechnology2 = new RecordingTechnology();
        recordingTechnology2.setId(recordingTechnology1.getId());
        assertThat(recordingTechnology1).isEqualTo(recordingTechnology2);
        recordingTechnology2.setId(2L);
        assertThat(recordingTechnology1).isNotEqualTo(recordingTechnology2);
        recordingTechnology1.setId(null);
        assertThat(recordingTechnology1).isNotEqualTo(recordingTechnology2);
    }
}
