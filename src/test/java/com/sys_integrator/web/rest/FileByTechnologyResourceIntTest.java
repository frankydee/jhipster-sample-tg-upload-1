package com.sys_integrator.web.rest;

import com.sys_integrator.TgUpload1App;

import com.sys_integrator.domain.FileByTechnology;
import com.sys_integrator.repository.FileByTechnologyRepository;
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
 * Test class for the FileByTechnologyResource REST controller.
 *
 * @see FileByTechnologyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TgUpload1App.class)
public class FileByTechnologyResourceIntTest {

    private static final String DEFAULT_OVERRIDING_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_OVERRIDING_PATTERN = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    private static final Boolean DEFAULT_REQUIRED = false;
    private static final Boolean UPDATED_REQUIRED = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private FileByTechnologyRepository fileByTechnologyRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFileByTechnologyMockMvc;

    private FileByTechnology fileByTechnology;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileByTechnologyResource fileByTechnologyResource = new FileByTechnologyResource(fileByTechnologyRepository);
        this.restFileByTechnologyMockMvc = MockMvcBuilders.standaloneSetup(fileByTechnologyResource)
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
    public static FileByTechnology createEntity(EntityManager em) {
        FileByTechnology fileByTechnology = new FileByTechnology()
            .overridingPattern(DEFAULT_OVERRIDING_PATTERN)
            .sortOrder(DEFAULT_SORT_ORDER)
            .required(DEFAULT_REQUIRED)
            .active(DEFAULT_ACTIVE)
            .createDate(DEFAULT_CREATE_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return fileByTechnology;
    }

    @Before
    public void initTest() {
        fileByTechnology = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileByTechnology() throws Exception {
        int databaseSizeBeforeCreate = fileByTechnologyRepository.findAll().size();

        // Create the FileByTechnology
        restFileByTechnologyMockMvc.perform(post("/api/file-by-technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileByTechnology)))
            .andExpect(status().isCreated());

        // Validate the FileByTechnology in the database
        List<FileByTechnology> fileByTechnologyList = fileByTechnologyRepository.findAll();
        assertThat(fileByTechnologyList).hasSize(databaseSizeBeforeCreate + 1);
        FileByTechnology testFileByTechnology = fileByTechnologyList.get(fileByTechnologyList.size() - 1);
        assertThat(testFileByTechnology.getOverridingPattern()).isEqualTo(DEFAULT_OVERRIDING_PATTERN);
        assertThat(testFileByTechnology.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testFileByTechnology.isRequired()).isEqualTo(DEFAULT_REQUIRED);
        assertThat(testFileByTechnology.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testFileByTechnology.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testFileByTechnology.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createFileByTechnologyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileByTechnologyRepository.findAll().size();

        // Create the FileByTechnology with an existing ID
        fileByTechnology.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileByTechnologyMockMvc.perform(post("/api/file-by-technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileByTechnology)))
            .andExpect(status().isBadRequest());

        // Validate the FileByTechnology in the database
        List<FileByTechnology> fileByTechnologyList = fileByTechnologyRepository.findAll();
        assertThat(fileByTechnologyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFileByTechnologies() throws Exception {
        // Initialize the database
        fileByTechnologyRepository.saveAndFlush(fileByTechnology);

        // Get all the fileByTechnologyList
        restFileByTechnologyMockMvc.perform(get("/api/file-by-technologies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileByTechnology.getId().intValue())))
            .andExpect(jsonPath("$.[*].overridingPattern").value(hasItem(DEFAULT_OVERRIDING_PATTERN.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].required").value(hasItem(DEFAULT_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getFileByTechnology() throws Exception {
        // Initialize the database
        fileByTechnologyRepository.saveAndFlush(fileByTechnology);

        // Get the fileByTechnology
        restFileByTechnologyMockMvc.perform(get("/api/file-by-technologies/{id}", fileByTechnology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileByTechnology.getId().intValue()))
            .andExpect(jsonPath("$.overridingPattern").value(DEFAULT_OVERRIDING_PATTERN.toString()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.required").value(DEFAULT_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFileByTechnology() throws Exception {
        // Get the fileByTechnology
        restFileByTechnologyMockMvc.perform(get("/api/file-by-technologies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileByTechnology() throws Exception {
        // Initialize the database
        fileByTechnologyRepository.saveAndFlush(fileByTechnology);

        int databaseSizeBeforeUpdate = fileByTechnologyRepository.findAll().size();

        // Update the fileByTechnology
        FileByTechnology updatedFileByTechnology = fileByTechnologyRepository.findById(fileByTechnology.getId()).get();
        // Disconnect from session so that the updates on updatedFileByTechnology are not directly saved in db
        em.detach(updatedFileByTechnology);
        updatedFileByTechnology
            .overridingPattern(UPDATED_OVERRIDING_PATTERN)
            .sortOrder(UPDATED_SORT_ORDER)
            .required(UPDATED_REQUIRED)
            .active(UPDATED_ACTIVE)
            .createDate(UPDATED_CREATE_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restFileByTechnologyMockMvc.perform(put("/api/file-by-technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFileByTechnology)))
            .andExpect(status().isOk());

        // Validate the FileByTechnology in the database
        List<FileByTechnology> fileByTechnologyList = fileByTechnologyRepository.findAll();
        assertThat(fileByTechnologyList).hasSize(databaseSizeBeforeUpdate);
        FileByTechnology testFileByTechnology = fileByTechnologyList.get(fileByTechnologyList.size() - 1);
        assertThat(testFileByTechnology.getOverridingPattern()).isEqualTo(UPDATED_OVERRIDING_PATTERN);
        assertThat(testFileByTechnology.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testFileByTechnology.isRequired()).isEqualTo(UPDATED_REQUIRED);
        assertThat(testFileByTechnology.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testFileByTechnology.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testFileByTechnology.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFileByTechnology() throws Exception {
        int databaseSizeBeforeUpdate = fileByTechnologyRepository.findAll().size();

        // Create the FileByTechnology

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileByTechnologyMockMvc.perform(put("/api/file-by-technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileByTechnology)))
            .andExpect(status().isBadRequest());

        // Validate the FileByTechnology in the database
        List<FileByTechnology> fileByTechnologyList = fileByTechnologyRepository.findAll();
        assertThat(fileByTechnologyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFileByTechnology() throws Exception {
        // Initialize the database
        fileByTechnologyRepository.saveAndFlush(fileByTechnology);

        int databaseSizeBeforeDelete = fileByTechnologyRepository.findAll().size();

        // Get the fileByTechnology
        restFileByTechnologyMockMvc.perform(delete("/api/file-by-technologies/{id}", fileByTechnology.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FileByTechnology> fileByTechnologyList = fileByTechnologyRepository.findAll();
        assertThat(fileByTechnologyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileByTechnology.class);
        FileByTechnology fileByTechnology1 = new FileByTechnology();
        fileByTechnology1.setId(1L);
        FileByTechnology fileByTechnology2 = new FileByTechnology();
        fileByTechnology2.setId(fileByTechnology1.getId());
        assertThat(fileByTechnology1).isEqualTo(fileByTechnology2);
        fileByTechnology2.setId(2L);
        assertThat(fileByTechnology1).isNotEqualTo(fileByTechnology2);
        fileByTechnology1.setId(null);
        assertThat(fileByTechnology1).isNotEqualTo(fileByTechnology2);
    }
}
