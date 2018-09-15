package com.sys_integrator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A FileByTechnology.
 */
@Entity
@Table(name = "file_type_by_tech")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileByTechnology implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "overriding_pattern")
    private String overridingPattern;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "required")
    private Boolean required;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @ManyToOne
    @JsonIgnoreProperties("")
    private FileType fileType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private RecordingTechnology technology;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOverridingPattern() {
        return overridingPattern;
    }

    public FileByTechnology overridingPattern(String overridingPattern) {
        this.overridingPattern = overridingPattern;
        return this;
    }

    public void setOverridingPattern(String overridingPattern) {
        this.overridingPattern = overridingPattern;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public FileByTechnology sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean isRequired() {
        return required;
    }

    public FileByTechnology required(Boolean required) {
        this.required = required;
        return this;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean isActive() {
        return active;
    }

    public FileByTechnology active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public FileByTechnology createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public FileByTechnology updatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public FileType getFileType() {
        return fileType;
    }

    public FileByTechnology fileType(FileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public RecordingTechnology getTechnology() {
        return technology;
    }

    public FileByTechnology technology(RecordingTechnology recordingTechnology) {
        this.technology = recordingTechnology;
        return this;
    }

    public void setTechnology(RecordingTechnology recordingTechnology) {
        this.technology = recordingTechnology;
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
        FileByTechnology fileByTechnology = (FileByTechnology) o;
        if (fileByTechnology.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileByTechnology.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileByTechnology{" +
            "id=" + getId() +
            ", overridingPattern='" + getOverridingPattern() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", required='" + isRequired() + "'" +
            ", active='" + isActive() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
