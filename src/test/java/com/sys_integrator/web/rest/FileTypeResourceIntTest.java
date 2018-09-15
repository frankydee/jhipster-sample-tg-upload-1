package com.sys_integrator.web.rest;

import com.sys_integrator.TgUpload1App;

import com.sys_integrator.domain.FileType;
import com.sys_integrator.repository.FileTypeRepository;
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
 * Test class for the FileTypeResource REST controller.
 *
 * @see FileTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TgUpload1App.class)
public class FileTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_PATTERN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private FileTypeRepository fileTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFileTypeMockMvc;

    private FileType fileType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileTypeResource fileTypeResource = new FileTypeResource(fileTypeRepository);
        this.restFileTypeMockMvc = MockMvcBuilders.standaloneSetup(fileTypeResource)
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
    public static FileType createEntity(EntityManager em) {
        FileType fileType = new FileType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .defaultPattern(DEFAULT_DEFAULT_PATTERN)
            .active(DEFAULT_ACTIVE)
            .createDate(DEFAULT_CREATE_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return fileType;
    }

    @Before
    public void initTest() {
        fileType = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileType() throws Exception {
        int databaseSizeBeforeCreate = fileTypeRepository.findAll().size();

        // Create the FileType
        restFileTypeMockMvc.perform(post("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileType)))
            .andExpect(status().isCreated());

        // Validate the FileType in the database
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FileType testFileType = fileTypeList.get(fileTypeList.size() - 1);
        assertThat(testFileType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFileType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFileType.getDefaultPattern()).isEqualTo(DEFAULT_DEFAULT_PATTERN);
        assertThat(testFileType.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testFileType.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testFileType.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createFileTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileTypeRepository.findAll().size();

        // Create the FileType with an existing ID
        fileType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileTypeMockMvc.perform(post("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileType)))
            .andExpect(status().isBadRequest());

        // Validate the FileType in the database
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileTypeRepository.findAll().size();
        // set the field null
        fileType.setName(null);

        // Create the FileType, which fails.

        restFileTypeMockMvc.perform(post("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileType)))
            .andExpect(status().isBadRequest());

        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileTypeRepository.findAll().size();
        // set the field null
        fileType.setActive(null);

        // Create the FileType, which fails.

        restFileTypeMockMvc.perform(post("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileType)))
            .andExpect(status().isBadRequest());

        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFileTypes() throws Exception {
        // Initialize the database
        fileTypeRepository.saveAndFlush(fileType);

        // Get all the fileTypeList
        restFileTypeMockMvc.perform(get("/api/file-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].defaultPattern").value(hasItem(DEFAULT_DEFAULT_PATTERN.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getFileType() throws Exception {
        // Initialize the database
        fileTypeRepository.saveAndFlush(fileType);

        // Get the fileType
        restFileTypeMockMvc.perform(get("/api/file-types/{id}", fileType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.defaultPattern").value(DEFAULT_DEFAULT_PATTERN.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFileType() throws Exception {
        // Get the fileType
        restFileTypeMockMvc.perform(get("/api/file-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileType() throws Exception {
        // Initialize the database
        fileTypeRepository.saveAndFlush(fileType);

        int databaseSizeBeforeUpdate = fileTypeRepository.findAll().size();

        // Update the fileType
        FileType updatedFileType = fileTypeRepository.findById(fileType.getId()).get();
        // Disconnect from session so that the updates on updatedFileType are not directly saved in db
        em.detach(updatedFileType);
        updatedFileType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .defaultPattern(UPDATED_DEFAULT_PATTERN)
            .active(UPDATED_ACTIVE)
            .createDate(UPDATED_CREATE_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restFileTypeMockMvc.perform(put("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFileType)))
            .andExpect(status().isOk());

        // Validate the FileType in the database
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeUpdate);
        FileType testFileType = fileTypeList.get(fileTypeList.size() - 1);
        assertThat(testFileType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFileType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFileType.getDefaultPattern()).isEqualTo(UPDATED_DEFAULT_PATTERN);
        assertThat(testFileType.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testFileType.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testFileType.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFileType() throws Exception {
        int databaseSizeBeforeUpdate = fileTypeRepository.findAll().size();

        // Create the FileType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileTypeMockMvc.perform(put("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileType)))
            .andExpect(status().isBadRequest());

        // Validate the FileType in the database
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFileType() throws Exception {
        // Initialize the database
        fileTypeRepository.saveAndFlush(fileType);

        int databaseSizeBeforeDelete = fileTypeRepository.findAll().size();

        // Get the fileType
        restFileTypeMockMvc.perform(delete("/api/file-types/{id}", fileType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileType.class);
        FileType fileType1 = new FileType();
        fileType1.setId(1L);
        FileType fileType2 = new FileType();
        fileType2.setId(fileType1.getId());
        assertThat(fileType1).isEqualTo(fileType2);
        fileType2.setId(2L);
        assertThat(fileType1).isNotEqualTo(fileType2);
        fileType1.setId(null);
        assertThat(fileType1).isNotEqualTo(fileType2);
    }
}
