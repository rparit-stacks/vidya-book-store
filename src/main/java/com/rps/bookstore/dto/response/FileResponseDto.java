package com.rps.bookstore.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class FileResponseDto {
    private String fileName;
    private String fileUrl;
    private String uploadedBy;
    private Instant timeStamp;
}
