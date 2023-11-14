package com.fileisland.datakeeper.Controller;

import com.fileisland.datakeeper.Services.JwtService;
import com.fileisland.datakeeper.Services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/s3")
public class FileController {

    @Autowired
    private S3Service s3Service;

    @GetMapping("/list")
    public ResponseEntity listObjects(@RequestBody ListObjectsDTO listObjectsDTO) {
        return ResponseEntity.ok(s3Service.listObjects(listObjectsDTO.userId()));
    }

    @GetMapping("/download")
    public ResponseEntity downloadObject(@RequestBody DownloadObjectDTO obejctDTO) throws IOException {
        ByteArrayResource resource = s3Service.downloadObject(obejctDTO.userId().toString(), obejctDTO.objectKey());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + obejctDTO.objectKey())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @PostMapping("/upload")
    public ResponseEntity uploadObject(@ModelAttribute UploadObjectDTO dto) {
        s3Service.uploadObject(dto.userId().toString(), dto.objectKey(), dto.file());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deleteObject(@RequestBody DeleteObjectDTO objectDTO){
        s3Service.deleteObject(objectDTO.userId(), objectDTO.objectKey());
        return ResponseEntity.ok().build();
    }
}
