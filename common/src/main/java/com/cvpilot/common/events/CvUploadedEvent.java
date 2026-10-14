package com.cvpilot.common.events;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class CvUploadedEvent implements Serializable {
    private UUID cvId;
    private UUID userId;
    private String fileKey;
    private String originalFileName;
    private String contentType;
    private long fileSize;
    private Instant uploadedAt;

    public CvUploadedEvent() {
    }

    public CvUploadedEvent(UUID cvId, UUID userId, String fileKey, String originalFileName, String contentType, long fileSize, Instant uploadedAt) {
        this.cvId = cvId;
        this.userId = userId;
        this.fileKey = fileKey;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.uploadedAt = uploadedAt;
    }

    public static CvUploadedEventBuilder builder() {
        return new CvUploadedEventBuilder();
    }

    public static class CvUploadedEventBuilder {
        private UUID cvId;
        private UUID userId;
        private String fileKey;
        private String originalFileName;
        private String contentType;
        private long fileSize;
        private Instant uploadedAt;

        public CvUploadedEventBuilder cvId(UUID cvId) {
            this.cvId = cvId;
            return this;
        }

        public CvUploadedEventBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public CvUploadedEventBuilder fileKey(String fileKey) {
            this.fileKey = fileKey;
            return this;
        }

        public CvUploadedEventBuilder originalFileName(String originalFileName) {
            this.originalFileName = originalFileName;
            return this;
        }

        public CvUploadedEventBuilder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public CvUploadedEventBuilder fileSize(long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public CvUploadedEventBuilder uploadedAt(Instant uploadedAt) {
            this.uploadedAt = uploadedAt;
            return this;
        }

        public CvUploadedEvent build() {
            return new CvUploadedEvent(cvId, userId, fileKey, originalFileName, contentType, fileSize, uploadedAt);
        }
    }

    public UUID getCvId() {
        return cvId;
    }

    public void setCvId(UUID cvId) {
        this.cvId = cvId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
