package com.cvpilot.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CvUploadedEvent implements Serializable {
    private UUID cvId;
    private UUID userId;
    private String fileKey;
    private String originalFileName;
    private String contentType;
    private long fileSize;
    private Instant uploadedAt;
}
