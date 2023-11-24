package com.fileisland.datakeeper.Controller;

import org.springframework.web.multipart.MultipartFile;

record UpdatePassDTO(Long userId, String password){};
record CreateUserDTO(String username, String password, String email){}
record AuthModelDTO(String login, String password) {}
record AuthModelResponseDTO(String id, String username, String email, String token ) {}
record DownloadObjectDTO(Long userId, String objectKey){};
record UploadObjectDTO(Long userId, String objectKey, MultipartFile file){}
record DeleteObjectDTO(Long userId, String objectKey){};
record RenameObjectDTO(Long userId, String objectKey, String newObjectKey){};
