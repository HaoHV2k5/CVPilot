package com.cvpilot.common.events;

import com.cvpilot.common.enums.CvStatus;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class CvAnalysisCompletedEvent implements Serializable {
    private UUID cvId;
    private UUID userId;
    private CvStatus status;
    private String errorMessage;
    private Instant completedAt;

    public CvAnalysisCompletedEvent() {
    }

    public CvAnalysisCompletedEvent(UUID cvId, UUID userId, CvStatus status, String errorMessage, Instant completedAt) {
        this.cvId = cvId;
        this.userId = userId;
        this.status = status;
        this.errorMessage = errorMessage;
        this.completedAt = completedAt;
    }

    public static CvAnalysisCompletedEventBuilder builder() {
        return new CvAnalysisCompletedEventBuilder();
    }

    public static class CvAnalysisCompletedEventBuilder {
        private UUID cvId;
        private UUID userId;
        private CvStatus status;
        private String errorMessage;
        private Instant completedAt;

        public CvAnalysisCompletedEventBuilder cvId(UUID cvId) {
            this.cvId = cvId;
            return this;
        }

        public CvAnalysisCompletedEventBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public CvAnalysisCompletedEventBuilder status(CvStatus status) {
            this.status = status;
            return this;
        }

        public CvAnalysisCompletedEventBuilder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public CvAnalysisCompletedEventBuilder completedAt(Instant completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public CvAnalysisCompletedEvent build() {
            return new CvAnalysisCompletedEvent(cvId, userId, status, errorMessage, completedAt);
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

    public CvStatus getStatus() {
        return status;
    }

    public void setStatus(CvStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }
}
