package com.sys_integrator.web.rest;

import com.sys_integrator.TgUpload1App;

import com.sys_integrator.domain.InvalidationCode;
import com.sys_integrator.repository.InvalidationCodeRepository;
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
 * Test class for the InvalidationCodeResource REST controller.
 *
 * @see InvalidationCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TgUpload1App.class)
public class InvalidationCodeResourceIntTest {

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private InvalidationCodeRepository invalidationCodeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInvalidationCodeMockMvc;

    private InvalidationCode invalidationCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvalidationCodeResource invalidationCodeResource = new InvalidationCodeResource(invalidationCodeRepository);
        this.restInvalidationCodeMockMvc = MockMvcBuilders.standaloneSetup(invalidationCodeResource)
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
    public static InvalidationCode createEntity(EntityManager em) {
        InvalidationCode invalidationCode = new InvalidationCode()
            .code(DEFAULT_CODE)
            .shortDescription(DEFAULT_SHORT_DESCRIPTION)
            .longDescription(DEFAULT_LONG_DESCRIPTION)
            .active(DEFAULT_ACTIVE)
            .createDate(DEFAULT_CREATE_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return invalidationCode;
    }

    @Before
    public void initTest() {
        invalidationCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvalidationCode() throws Exception {
        int databaseSizeBeforeCreate = invalidationCodeRepository.findAll().size();

        // Create the InvalidationCode
        restInvalidationCodeMockMvc.perform(post("/api/invalidation-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invalidationCode)))
            .andExpect(status().isCreated());

        // Validate the InvalidationCode in the database
        List<InvalidationCode> invalidationCodeList = invalidationCodeRepository.findAll();
        assertThat(invalidationCodeList).hasSize(databaseSizeBeforeCreate + 1);
        InvalidationCode testInvalidationCode = invalidationCodeList.get(invalidationCodeList.size() - 1);
        assertThat(testInvalidationCode.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInvalidationCode.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testInvalidationCode.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testInvalidationCode.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testInvalidationCode.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testInvalidationCode.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createInvalidationCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invalidationCodeRepository.findAll().size();

        // Create the InvalidationCode with an existing ID
        invalidationCode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvalidationCodeMockMvc.perform(post("/api/invalidation-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invalidationCode)))
            .andExpect(status().isBadRequest());

        // Validate the InvalidationCode in the database
        List<InvalidationCode> invalidationCodeList = invalidationCodeRepository.findAll();
        assertThat(invalidationCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInvalidationCodes() throws Exception {
        // Initialize the database
        invalidationCodeRepository.saveAndFlush(invalidationCode);

        // Get all the invalidationCodeList
        restInvalidationCodeMockMvc.perform(get("/api/invalidation-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invalidationCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getInvalidationCode() throws Exception {
        // Initialize the database
        invalidationCodeRepository.saveAndFlush(invalidationCode);

        // Get the invalidationCode
        restInvalidationCodeMockMvc.perform(get("/api/invalidation-codes/{id}", invalidationCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invalidationCode.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvalidationCode() throws Exception {
        // Get the invalidationCode
        restInvalidationCodeMockMvc.perform(get("/api/invalidation-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvalidationCode() throws Exception {
        // Initialize the database
        invalidationCodeRepository.saveAndFlush(invalidationCode);

        int databaseSizeBeforeUpdate = invalidationCodeRepository.findAll().size();

        // Update the invalidationCode
        InvalidationCode updatedInvalidationCode = invalidationCodeRepository.findById(invalidationCode.getId()).get();
        // Disconnect from session so that the updates on updatedInvalidationCode are not directly saved in db
        em.detach(updatedInvalidationCode);
        updatedInvalidationCode
            .code(UPDATED_CODE)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .active(UPDATED_ACTIVE)
            .createDate(UPDATED_CREATE_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restInvalidationCodeMockMvc.perform(put("/api/invalidation-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvalidationCode)))
            .andExpect(status().isOk());

        // Validate the InvalidationCode in the database
        List<InvalidationCode> invalidationCodeList = invalidationCodeRepository.findAll();
        assertThat(invalidationCodeList).hasSize(databaseSizeBeforeUpdate);
        InvalidationCode testInvalidationCode = invalidationCodeList.get(invalidationCodeList.size() - 1);
        assertThat(testInvalidationCode.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInvalidationCode.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testInvalidationCode.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testInvalidationCode.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testInvalidationCode.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testInvalidationCode.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingInvalidationCode() throws Exception {
        int databaseSizeBeforeUpdate = invalidationCodeRepository.findAll().size();

        // Create the InvalidationCode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvalidationCodeMockMvc.perform(put("/api/invalidation-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invalidationCode)))
            .andExpect(status().isBadRequest());

        // Validate the InvalidationCode in the database
        List<InvalidationCode> invalidationCodeList = invalidationCodeRepository.findAll();
        assertThat(invalidationCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvalidationCode() throws Exception {
        // Initialize the database
        invalidationCodeRepository.saveAndFlush(invalidationCode);

        int databaseSizeBeforeDelete = invalidationCodeRepository.findAll().size();

        // Get the invalidationCode
        restInvalidationCodeMockMvc.perform(delete("/api/invalidation-codes/{id}", invalidationCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InvalidationCode> invalidationCodeList = invalidationCodeRepository.findAll();
        assertThat(invalidationCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvalidationCode.class);
        InvalidationCode invalidationCode1 = new InvalidationCode();
        invalidationCode1.setId(1L);
        InvalidationCode invalidationCode2 = new InvalidationCode();
        invalidationCode2.setId(invalidationCode1.getId());
        assertThat(invalidationCode1).isEqualTo(invalidationCode2);
        invalidationCode2.setId(2L);
        assertThat(invalidationCode1).isNotEqualTo(invalidationCode2);
        invalidationCode1.setId(null);
        assertThat(invalidationCode1).isNotEqualTo(invalidationCode2);
    }
}
