package com.fileisland.datakeeper.Controller;

import com.fileisland.datakeeper.Dao.Entity.User;
import com.fileisland.datakeeper.Services.JwtService;
import com.fileisland.datakeeper.Services.S3Service;
import com.fileisland.datakeeper.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/s3")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileController {

    @Autowired
    private S3Service s3Service;
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseEntity listObjects(@RequestHeader Long userId, @RequestHeader String searchWord, @RequestHeader int page, @RequestHeader int pageSize) {
        //if page and pageSize are not present or is less than 1, set default values to 1 and 10

          page = page < 1 ? 1 : page;
          pageSize = pageSize < 10 ? 10 : pageSize;

          User user = userService.findById(userId);

          if (searchWord == null || searchWord.isEmpty())
              return ResponseEntity.ok(s3Service.listObjects(userId, page, pageSize));

          return ResponseEntity.ok(s3Service.listObjectsSearch(userId, searchWord, page, pageSize));

    }

    @GetMapping("/download")
    public ResponseEntity downloadObject(@RequestHeader DownloadObjectDTO obejctDTO) throws IOException {
       Resource resource = s3Service.downloadObject(obejctDTO.userId().toString(), obejctDTO.objectKey());
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
    public ResponseEntity deleteObject(@RequestHeader String userId, @RequestHeader String objectKey){
        s3Service.deleteObject(userId, objectKey);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/rename")
    public ResponseEntity renameObject(@RequestBody RenameObjectDTO objectDTO){
        s3Service.renameObject(objectDTO.userId(), objectDTO.objectKey(), objectDTO.newObjectKey());
        return ResponseEntity.ok().build();
    }


}
