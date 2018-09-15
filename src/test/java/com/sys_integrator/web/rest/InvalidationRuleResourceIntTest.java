package com.sys_integrator.web.rest;

import com.sys_integrator.TgUpload1App;

import com.sys_integrator.domain.InvalidationRule;
import com.sys_integrator.repository.InvalidationRuleRepository;
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
 * Test class for the InvalidationRuleResource REST controller.
 *
 * @see InvalidationRuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TgUpload1App.class)
public class InvalidationRuleResourceIntTest {

    private static final String DEFAULT_ELR = "AAAA";
    private static final String UPDATED_ELR = "BBBB";

    private static final String DEFAULT_TRACK_ID = "AAAA";
    private static final String UPDATED_TRACK_ID = "BBBB";

    private static final Integer DEFAULT_START_MILEAGE = 1;
    private static final Integer UPDATED_START_MILEAGE = 2;

    private static final Integer DEFAULT_END_MILEAGE = 1;
    private static final Integer UPDATED_END_MILEAGE = 2;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private InvalidationRuleRepository invalidationRuleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInvalidationRuleMockMvc;

    private InvalidationRule invalidationRule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvalidationRuleResource invalidationRuleResource = new InvalidationRuleResource(invalidationRuleRepository);
        this.restInvalidationRuleMockMvc = MockMvcBuilders.standaloneSetup(invalidationRuleResource)
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
    public static InvalidationRule createEntity(EntityManager em) {
        InvalidationRule invalidationRule = new InvalidationRule()
            .elr(DEFAULT_ELR)
            .trackId(DEFAULT_TRACK_ID)
            .startMileage(DEFAULT_START_MILEAGE)
            .endMileage(DEFAULT_END_MILEAGE)
            .comment(DEFAULT_COMMENT)
            .createDate(DEFAULT_CREATE_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return invalidationRule;
    }

    @Before
    public void initTest() {
        invalidationRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvalidationRule() throws Exception {
        int databaseSizeBeforeCreate = invalidationRuleRepository.findAll().size();

        // Create the InvalidationRule
        restInvalidationRuleMockMvc.perform(post("/api/invalidation-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invalidationRule)))
            .andExpect(status().isCreated());

        // Validate the InvalidationRule in the database
        List<InvalidationRule> invalidationRuleList = invalidationRuleRepository.findAll();
        assertThat(invalidationRuleList).hasSize(databaseSizeBeforeCreate + 1);
        InvalidationRule testInvalidationRule = invalidationRuleList.get(invalidationRuleList.size() - 1);
        assertThat(testInvalidationRule.getElr()).isEqualTo(DEFAULT_ELR);
        assertThat(testInvalidationRule.getTrackId()).isEqualTo(DEFAULT_TRACK_ID);
        assertThat(testInvalidationRule.getStartMileage()).isEqualTo(DEFAULT_START_MILEAGE);
        assertThat(testInvalidationRule.getEndMileage()).isEqualTo(DEFAULT_END_MILEAGE);
        assertThat(testInvalidationRule.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testInvalidationRule.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testInvalidationRule.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createInvalidationRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invalidationRuleRepository.findAll().size();

        // Create the InvalidationRule with an existing ID
        invalidationRule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvalidationRuleMockMvc.perform(post("/api/invalidation-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invalidationRule)))
            .andExpect(status().isBadRequest());

        // Validate the InvalidationRule in the database
        List<InvalidationRule> invalidationRuleList = invalidationRuleRepository.findAll();
        assertThat(invalidationRuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkElrIsRequired() throws Exception {
        int databaseSizeBeforeTest = invalidationRuleRepository.findAll().size();
        // set the field null
        invalidationRule.setElr(null);

        // Create the InvalidationRule, which fails.

        restInvalidationRuleMockMvc.perform(post("/api/invalidation-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invalidationRule)))
            .andExpect(status().isBadRequest());

        List<InvalidationRule> invalidationRuleList = invalidationRuleRepository.findAll();
        assertThat(invalidationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrackIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = invalidationRuleRepository.findAll().size();
        // set the field null
        invalidationRule.setTrackId(null);

        // Create the InvalidationRule, which fails.

        restInvalidationRuleMockMvc.perform(post("/api/invalidation-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invalidationRule)))
            .andExpect(status().isBadRequest());

        List<InvalidationRule> invalidationRuleList = invalidationRuleRepository.findAll();
        assertThat(invalidationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartMileageIsRequired() throws Exception {
        int databaseSizeBeforeTest = invalidationRuleRepository.findAll().size();
        // set the field null
        invalidationRule.setStartMileage(null);

        // Create the InvalidationRule, which fails.

        restInvalidationRuleMockMvc.perform(post("/api/invalidation-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invalidationRule)))
            .andExpect(status().isBadRequest());

        List<InvalidationRule> invalidationRuleList = invalidationRuleRepository.findAll();
        assertThat(invalidationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndMileageIsRequired() throws Exception {
        int databaseSizeBeforeTest = invalidationRuleRepository.findAll().size();
        // set the field null
        invalidationRule.setEndMileage(null);

        // Create the InvalidationRule, which fails.

        restInvalidationRuleMockMvc.perform(post("/api/invalidation-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invalidationRule)))
            .andExpect(status().isBadRequest());

        List<InvalidationRule> invalidationRuleList = invalidationRuleRepository.findAll();
        assertThat(invalidationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvalidationRules() throws Exception {
        // Initialize the database
        invalidationRuleRepository.saveAndFlush(invalidationRule);

        // Get all the invalidationRuleList
        restInvalidationRuleMockMvc.perform(get("/api/invalidation-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invalidationRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].elr").value(hasItem(DEFAULT_ELR.toString())))
            .andExpect(jsonPath("$.[*].trackId").value(hasItem(DEFAULT_TRACK_ID.toString())))
            .andExpect(jsonPath("$.[*].startMileage").value(hasItem(DEFAULT_START_MILEAGE)))
            .andExpect(jsonPath("$.[*].endMileage").value(hasItem(DEFAULT_END_MILEAGE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getInvalidationRule() throws Exception {
        // Initialize the database
        invalidationRuleRepository.saveAndFlush(invalidationRule);

        // Get the invalidationRule
        restInvalidationRuleMockMvc.perform(get("/api/invalidation-rules/{id}", invalidationRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invalidationRule.getId().intValue()))
            .andExpect(jsonPath("$.elr").value(DEFAULT_ELR.toString()))
            .andExpect(jsonPath("$.trackId").value(DEFAULT_TRACK_ID.toString()))
            .andExpect(jsonPath("$.startMileage").value(DEFAULT_START_MILEAGE))
            .andExpect(jsonPath("$.endMileage").value(DEFAULT_END_MILEAGE))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvalidationRule() throws Exception {
        // Get the invalidationRule
        restInvalidationRuleMockMvc.perform(get("/api/invalidation-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvalidationRule() throws Exception {
        // Initialize the database
        invalidationRuleRepository.saveAndFlush(invalidationRule);

        int databaseSizeBeforeUpdate = invalidationRuleRepository.findAll().size();

        // Update the invalidationRule
        InvalidationRule updatedInvalidationRule = invalidationRuleRepository.findById(invalidationRule.getId()).get();
        // Disconnect from session so that the updates on updatedInvalidationRule are not directly saved in db
        em.detach(updatedInvalidationRule);
        updatedInvalidationRule
            .elr(UPDATED_ELR)
            .trackId(UPDATED_TRACK_ID)
            .startMileage(UPDATED_START_MILEAGE)
            .endMileage(UPDATED_END_MILEAGE)
            .comment(UPDATED_COMMENT)
            .createDate(UPDATED_CREATE_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restInvalidationRuleMockMvc.perform(put("/api/invalidation-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvalidationRule)))
            .andExpect(status().isOk());

        // Validate the InvalidationRule in the database
        List<InvalidationRule> invalidationRuleList = invalidationRuleRepository.findAll();
        assertThat(invalidationRuleList).hasSize(databaseSizeBeforeUpdate);
        InvalidationRule testInvalidationRule = invalidationRuleList.get(invalidationRuleList.size() - 1);
        assertThat(testInvalidationRule.getElr()).isEqualTo(UPDATED_ELR);
        assertThat(testInvalidationRule.getTrackId()).isEqualTo(UPDATED_TRACK_ID);
        assertThat(testInvalidationRule.getStartMileage()).isEqualTo(UPDATED_START_MILEAGE);
        assertThat(testInvalidationRule.getEndMileage()).isEqualTo(UPDATED_END_MILEAGE);
        assertThat(testInvalidationRule.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testInvalidationRule.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testInvalidationRule.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingInvalidationRule() throws Exception {
        int databaseSizeBeforeUpdate = invalidationRuleRepository.findAll().size();

        // Create the InvalidationRule

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvalidationRuleMockMvc.perform(put("/api/invalidation-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invalidationRule)))
            .andExpect(status().isBadRequest());

        // Validate the InvalidationRule in the database
        List<InvalidationRule> invalidationRuleList = invalidationRuleRepository.findAll();
        assertThat(invalidationRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvalidationRule() throws Exception {
        // Initialize the database
        invalidationRuleRepository.saveAndFlush(invalidationRule);

        int databaseSizeBeforeDelete = invalidationRuleRepository.findAll().size();

        // Get the invalidationRule
        restInvalidationRuleMockMvc.perform(delete("/api/invalidation-rules/{id}", invalidationRule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InvalidationRule> invalidationRuleList = invalidationRuleRepository.findAll();
        assertThat(invalidationRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvalidationRule.class);
        InvalidationRule invalidationRule1 = new InvalidationRule();
        invalidationRule1.setId(1L);
        InvalidationRule invalidationRule2 = new InvalidationRule();
        invalidationRule2.setId(invalidationRule1.getId());
        assertThat(invalidationRule1).isEqualTo(invalidationRule2);
        invalidationRule2.setId(2L);
        assertThat(invalidationRule1).isNotEqualTo(invalidationRule2);
        invalidationRule1.setId(null);
        assertThat(invalidationRule1).isNotEqualTo(invalidationRule2);
    }
}
