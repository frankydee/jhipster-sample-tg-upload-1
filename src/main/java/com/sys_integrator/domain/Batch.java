package com.sys_integrator.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.sys_integrator.domain.enumeration.BatchStatus;

/**
 * A Batch.
 */
@Entity
@Table(name = "batch")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Batch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "initiated_date")
    private LocalDate initiatedDate;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "jhi_comment")
    private String comment;

    @Column(name = "train_identifier")
    private String trainIdentifier;

    @Column(name = "journey")
    private String journey;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BatchStatus status;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInitiatedDate() {
        return initiatedDate;
    }

    public Batch initiatedDate(LocalDate initiatedDate) {
        this.initiatedDate = initiatedDate;
        return this;
    }

    public void setInitiatedDate(LocalDate initiatedDate) {
        this.initiatedDate = initiatedDate;
    }

    public String getUsername() {
        return username;
    }

    public Batch username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public Batch comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTrainIdentifier() {
        return trainIdentifier;
    }

    public Batch trainIdentifier(String trainIdentifier) {
        this.trainIdentifier = trainIdentifier;
        return this;
    }

    public void setTrainIdentifier(String trainIdentifier) {
        this.trainIdentifier = trainIdentifier;
    }

    public String getJourney() {
        return journey;
    }

    public Batch journey(String journey) {
        this.journey = journey;
        return this;
    }

    public void setJourney(String journey) {
        this.journey = journey;
    }

    public BatchStatus getStatus() {
        return status;
    }

    public Batch status(BatchStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(BatchStatus status) {
        this.status = status;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public Batch modifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Batch batch = (Batch) o;
        if (batch.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), batch.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Batch{" +
            "id=" + getId() +
            ", initiatedDate='" + getInitiatedDate() + "'" +
            ", username='" + getUsername() + "'" +
            ", comment='" + getComment() + "'" +
            ", trainIdentifier='" + getTrainIdentifier() + "'" +
            ", journey='" + getJourney() + "'" +
            ", status='" + getStatus() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
