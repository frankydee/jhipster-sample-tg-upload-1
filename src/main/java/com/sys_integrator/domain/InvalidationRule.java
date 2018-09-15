package com.sys_integrator.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A InvalidationRule.
 */
@Entity
@Table(name = "invalidation_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InvalidationRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 4)
    @Column(name = "elr", length = 4, nullable = false)
    private String elr;

    @NotNull
    @Size(max = 4)
    @Column(name = "track_id", length = 4, nullable = false)
    private String trackId;

    @NotNull
    @Column(name = "start_mileage", nullable = false)
    private Integer startMileage;

    @NotNull
    @Column(name = "end_mileage", nullable = false)
    private Integer endMileage;

    @Size(max = 1000)
    @Column(name = "jhi_comment", length = 1000)
    private String comment;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getElr() {
        return elr;
    }

    public InvalidationRule elr(String elr) {
        this.elr = elr;
        return this;
    }

    public void setElr(String elr) {
        this.elr = elr;
    }

    public String getTrackId() {
        return trackId;
    }

    public InvalidationRule trackId(String trackId) {
        this.trackId = trackId;
        return this;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public Integer getStartMileage() {
        return startMileage;
    }

    public InvalidationRule startMileage(Integer startMileage) {
        this.startMileage = startMileage;
        return this;
    }

    public void setStartMileage(Integer startMileage) {
        this.startMileage = startMileage;
    }

    public Integer getEndMileage() {
        return endMileage;
    }

    public InvalidationRule endMileage(Integer endMileage) {
        this.endMileage = endMileage;
        return this;
    }

    public void setEndMileage(Integer endMileage) {
        this.endMileage = endMileage;
    }

    public String getComment() {
        return comment;
    }

    public InvalidationRule comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public InvalidationRule createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public InvalidationRule updatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
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
        InvalidationRule invalidationRule = (InvalidationRule) o;
        if (invalidationRule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invalidationRule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvalidationRule{" +
            "id=" + getId() +
            ", elr='" + getElr() + "'" +
            ", trackId='" + getTrackId() + "'" +
            ", startMileage=" + getStartMileage() +
            ", endMileage=" + getEndMileage() +
            ", comment='" + getComment() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
