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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3Service {
    @Autowired
    private S3Client s3Client;
    private final Logger LOGGER = LoggerFactory.getLogger(S3Service.class);


    @Value("${s3.bucketName}")
    private String bucketName;

    public List<S3Object> listObjects(Long userId, int page, int pageSize) {
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(userId.toString())
                .maxKeys(page * pageSize)
                .build();

        ListObjectsV2Response listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);
        //pagination
        if (listObjectsResponse.isTruncated() && page > 1)
            listObjectsResponse = s3Client.listObjectsV2(ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix(userId.toString())
                    .maxKeys(page * pageSize)
                    .continuationToken(listObjectsResponse.nextContinuationToken())
                    .build());

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
            LOGGER.error("Error uploading object to S3", e);
        }
}

    public void deleteObject(Long userId, String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                        .bucket(this.bucketName)
                        .key(userId + "/" + key)
                        .build();
        s3Client.deleteObject(deleteObjectRequest);
        this.LOGGER.info("Object {} deleted successfully", key);
    }

    public void renameObject(Long userId, String key, String newKey) {
        CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                .sourceBucket(this.bucketName)
                .sourceKey(userId + "/" + key)
                .destinationBucket(this.bucketName)
                .destinationKey(userId + "/" + newKey)
                .build();
        s3Client.copyObject(copyObjectRequest);
        this.deleteObject(userId, key);
        this.LOGGER.info("Object {} renamed successfully", key);
    }


    @PostConstruct
    private void testConection(){
        try {
            s3Client.listBuckets();
            LOGGER.info(" ------------------------------ Connected to S3 ---------------------------------");
        } catch (Exception e) {
            LOGGER.error(" ------------------------------ Error connecting to S3 ---------------------------------");
            throw e;
        }

    }

    public List<S3Object> listObjectsSearch(Long userId, String search, int page, int pageSize) {
       return listObjects(userId, page, pageSize).stream()
                .filter(s3Object -> s3Object.key().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
    }
}
