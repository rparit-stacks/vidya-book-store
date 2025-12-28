package com.rps.bookstore.service;

import com.rps.bookstore.dto.response.FileResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${aws.s3.bucket.name}")
    public String bucketName;

    @Autowired
    private S3Client s3Client;


    public String uploadFile(MultipartFile file) {
        try {
            String key = UUID.randomUUID() + "_" + file.getOriginalFilename();

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(
                    request,
                    RequestBody.fromInputStream(
                            file.getInputStream(),
                            file.getSize()
                    )
            );

            return key; // return key instead of message

        } catch (IOException e) {
            throw new RuntimeException("Failed to read file", e);

        } catch (S3Exception e) {
            throw new RuntimeException(
                    "S3 upload failed. Status: " + e.statusCode(),
                    e
            );
        }
    }


    public byte[] getFile(String key) {
        try {
            ResponseBytes<GetObjectResponse> object =
                    s3Client.getObjectAsBytes(
                            GetObjectRequest.builder()
                                    .bucket(bucketName)
                                    .key(key)
                                    .build()
                    );
            return object.asByteArray();
        } catch (NoSuchKeyException e) {

            throw new RuntimeException("File not found in S3 for key: " + key, e);

        } catch (S3Exception e) {
            throw new RuntimeException(
                    "Error fetching file from S3. Status: " + e.statusCode(),
                    e
            );

        } catch (SdkException e) {
            throw new RuntimeException("AWS SDK error while fetching file", e);

        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while fetching file", e);
        }
    }



}
