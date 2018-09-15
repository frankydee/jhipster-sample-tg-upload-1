package com.sys_integrator.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.sys_integrator.domain.enumeration.FileStatus;

/**
 * A UploadedFile.
 */
@Entity
@Table(name = "uploaded_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UploadedFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "uploaded_date")
    private Instant uploadedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FileStatus status;

    @Column(name = "comnment")
    private String comnment;

    @Column(name = "submitted_date")
    private Instant submittedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public UploadedFile fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public UploadedFile fileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Instant getUploadedDate() {
        return uploadedDate;
    }

    public UploadedFile uploadedDate(Instant uploadedDate) {
        this.uploadedDate = uploadedDate;
        return this;
    }

    public void setUploadedDate(Instant uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public FileStatus getStatus() {
        return status;
    }

    public UploadedFile status(FileStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(FileStatus status) {
        this.status = status;
    }

    public String getComnment() {
        return comnment;
    }

    public UploadedFile comnment(String comnment) {
        this.comnment = comnment;
        return this;
    }

    public void setComnment(String comnment) {
        this.comnment = comnment;
    }

    public Instant getSubmittedDate() {
        return submittedDate;
    }

    public UploadedFile submittedDate(Instant submittedDate) {
        this.submittedDate = submittedDate;
        return this;
    }

    public void setSubmittedDate(Instant submittedDate) {
        this.submittedDate = submittedDate;
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
        UploadedFile uploadedFile = (UploadedFile) o;
        if (uploadedFile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uploadedFile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UploadedFile{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", uploadedDate='" + getUploadedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", comnment='" + getComnment() + "'" +
            ", submittedDate='" + getSubmittedDate() + "'" +
            "}";
    }
}
