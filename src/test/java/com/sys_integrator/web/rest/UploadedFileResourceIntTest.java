package com.sys_integrator.web.rest;

import com.sys_integrator.TgUpload1App;

import com.sys_integrator.domain.UploadedFile;
import com.sys_integrator.repository.UploadedFileRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.sys_integrator.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sys_integrator.domain.enumeration.FileStatus;
/**
 * Test class for the UploadedFileResource REST controller.
 *
 * @see UploadedFileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TgUpload1App.class)
public class UploadedFileResourceIntTest {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPLOADED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOADED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final FileStatus DEFAULT_STATUS = FileStatus.UPLOADED;
    private static final FileStatus UPDATED_STATUS = FileStatus.VALIDATING;

    private static final String DEFAULT_COMNMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMNMENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_SUBMITTED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUBMITTED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UploadedFileRepository uploadedFileRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUploadedFileMockMvc;

    private UploadedFile uploadedFile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UploadedFileResource uploadedFileResource = new UploadedFileResource(uploadedFileRepository);
        this.restUploadedFileMockMvc = MockMvcBuilders.standaloneSetup(uploadedFileResource)
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
    public static UploadedFile createEntity(EntityManager em) {
        UploadedFile uploadedFile = new UploadedFile()
            .fileName(DEFAULT_FILE_NAME)
            .fileType(DEFAULT_FILE_TYPE)
            .uploadedDate(DEFAULT_UPLOADED_DATE)
            .status(DEFAULT_STATUS)
            .comnment(DEFAULT_COMNMENT)
            .submittedDate(DEFAULT_SUBMITTED_DATE);
        return uploadedFile;
    }

    @Before
    public void initTest() {
        uploadedFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createUploadedFile() throws Exception {
        int databaseSizeBeforeCreate = uploadedFileRepository.findAll().size();

        // Create the UploadedFile
        restUploadedFileMockMvc.perform(post("/api/uploaded-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadedFile)))
            .andExpect(status().isCreated());

        // Validate the UploadedFile in the database
        List<UploadedFile> uploadedFileList = uploadedFileRepository.findAll();
        assertThat(uploadedFileList).hasSize(databaseSizeBeforeCreate + 1);
        UploadedFile testUploadedFile = uploadedFileList.get(uploadedFileList.size() - 1);
        assertThat(testUploadedFile.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testUploadedFile.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testUploadedFile.getUploadedDate()).isEqualTo(DEFAULT_UPLOADED_DATE);
        assertThat(testUploadedFile.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUploadedFile.getComnment()).isEqualTo(DEFAULT_COMNMENT);
        assertThat(testUploadedFile.getSubmittedDate()).isEqualTo(DEFAULT_SUBMITTED_DATE);
    }

    @Test
    @Transactional
    public void createUploadedFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uploadedFileRepository.findAll().size();

        // Create the UploadedFile with an existing ID
        uploadedFile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUploadedFileMockMvc.perform(post("/api/uploaded-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadedFile)))
            .andExpect(status().isBadRequest());

        // Validate the UploadedFile in the database
        List<UploadedFile> uploadedFileList = uploadedFileRepository.findAll();
        assertThat(uploadedFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUploadedFiles() throws Exception {
        // Initialize the database
        uploadedFileRepository.saveAndFlush(uploadedFile);

        // Get all the uploadedFileList
        restUploadedFileMockMvc.perform(get("/api/uploaded-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadedFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].uploadedDate").value(hasItem(DEFAULT_UPLOADED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comnment").value(hasItem(DEFAULT_COMNMENT.toString())))
            .andExpect(jsonPath("$.[*].submittedDate").value(hasItem(DEFAULT_SUBMITTED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getUploadedFile() throws Exception {
        // Initialize the database
        uploadedFileRepository.saveAndFlush(uploadedFile);

        // Get the uploadedFile
        restUploadedFileMockMvc.perform(get("/api/uploaded-files/{id}", uploadedFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uploadedFile.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE.toString()))
            .andExpect(jsonPath("$.uploadedDate").value(DEFAULT_UPLOADED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.comnment").value(DEFAULT_COMNMENT.toString()))
            .andExpect(jsonPath("$.submittedDate").value(DEFAULT_SUBMITTED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUploadedFile() throws Exception {
        // Get the uploadedFile
        restUploadedFileMockMvc.perform(get("/api/uploaded-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUploadedFile() throws Exception {
        // Initialize the database
        uploadedFileRepository.saveAndFlush(uploadedFile);

        int databaseSizeBeforeUpdate = uploadedFileRepository.findAll().size();

        // Update the uploadedFile
        UploadedFile updatedUploadedFile = uploadedFileRepository.findById(uploadedFile.getId()).get();
        // Disconnect from session so that the updates on updatedUploadedFile are not directly saved in db
        em.detach(updatedUploadedFile);
        updatedUploadedFile
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .uploadedDate(UPDATED_UPLOADED_DATE)
            .status(UPDATED_STATUS)
            .comnment(UPDATED_COMNMENT)
            .submittedDate(UPDATED_SUBMITTED_DATE);

        restUploadedFileMockMvc.perform(put("/api/uploaded-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUploadedFile)))
            .andExpect(status().isOk());

        // Validate the UploadedFile in the database
        List<UploadedFile> uploadedFileList = uploadedFileRepository.findAll();
        assertThat(uploadedFileList).hasSize(databaseSizeBeforeUpdate);
        UploadedFile testUploadedFile = uploadedFileList.get(uploadedFileList.size() - 1);
        assertThat(testUploadedFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testUploadedFile.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testUploadedFile.getUploadedDate()).isEqualTo(UPDATED_UPLOADED_DATE);
        assertThat(testUploadedFile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUploadedFile.getComnment()).isEqualTo(UPDATED_COMNMENT);
        assertThat(testUploadedFile.getSubmittedDate()).isEqualTo(UPDATED_SUBMITTED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUploadedFile() throws Exception {
        int databaseSizeBeforeUpdate = uploadedFileRepository.findAll().size();

        // Create the UploadedFile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUploadedFileMockMvc.perform(put("/api/uploaded-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadedFile)))
            .andExpect(status().isBadRequest());

        // Validate the UploadedFile in the database
        List<UploadedFile> uploadedFileList = uploadedFileRepository.findAll();
        assertThat(uploadedFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUploadedFile() throws Exception {
        // Initialize the database
        uploadedFileRepository.saveAndFlush(uploadedFile);

        int databaseSizeBeforeDelete = uploadedFileRepository.findAll().size();

        // Get the uploadedFile
        restUploadedFileMockMvc.perform(delete("/api/uploaded-files/{id}", uploadedFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UploadedFile> uploadedFileList = uploadedFileRepository.findAll();
        assertThat(uploadedFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadedFile.class);
        UploadedFile uploadedFile1 = new UploadedFile();
        uploadedFile1.setId(1L);
        UploadedFile uploadedFile2 = new UploadedFile();
        uploadedFile2.setId(uploadedFile1.getId());
        assertThat(uploadedFile1).isEqualTo(uploadedFile2);
        uploadedFile2.setId(2L);
        assertThat(uploadedFile1).isNotEqualTo(uploadedFile2);
        uploadedFile1.setId(null);
        assertThat(uploadedFile1).isNotEqualTo(uploadedFile2);
    }
}
