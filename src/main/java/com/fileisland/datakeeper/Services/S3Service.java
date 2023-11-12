package com.fileisland.datakeeper.Services;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class S3Service {
    @Autowired
    private S3Client s3Client;
    private static Logger LOGGER = LoggerFactory.getLogger(S3Service.class);


    @Value("${s3.bucketName}")
    private String bucketName;

    public List<S3Object> listObjects(Long userId) {
        ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                .bucket(bucketName)
                .prefix(userId.toString())
                .build();

        ListObjectsResponse listObjectsResponse = s3Client.listObjects(listObjectsRequest);

        return listObjectsResponse.contents();
    }

    public ByteArrayResource downloadObject(String userId, String objectKey) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(userId + "/" + objectKey)
                .build();

        ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObjectAsBytes(getObjectRequest);
        byte[] content = responseBytes.asByteArray();
        return new ByteArrayResource(content);

    }

    public void uploadObject(String userId, String objectKey, MultipartFile file) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(userId + "/" + objectKey)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(),file.getSize()));
            System.out.println("Object uploaded successfully");
        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
        }
}

    public void deleteObject(Long userId, String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                        .bucket(this.bucketName)
                        .key(userId + "/" + key)
                        .build();
        s3Client.deleteObject(deleteObjectRequest);
        this.LOGGER.info("");
    }

    @PostConstruct
    private void testConection(){
        String ss = s3Client.listBuckets().owner().displayName();
        if (ss != null)
            LOGGER.info(" --- S3 connection OK");
        else
            LOGGER.error(" --- S3 connection failed");

    }
}
