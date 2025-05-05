package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.FileUploadResponse;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestController
public class FileController {

    @Value("${file.upload-file.base-uri}")
    private String baseURI;

    private final FileServiceImpl fileService;

    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/files")
    public ApiResponse<FileUploadResponse> upload(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam("folder") String folder
    ) throws URISyntaxException, IOException {

        // validate
        if (file == null || file.isEmpty())
            throw new AppException(ErrorCode.FILE_EMPTY);

        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png");
        List<String> allowedMimeTypes = Arrays.asList(
                "application/pdf",
                "image/jpeg",
                "image/png",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        );
        boolean isValid = allowedExtensions.stream().anyMatch(item -> fileName.toLowerCase().endsWith(item));
        if (!isValid) {
            throw new AppException(ErrorCode.FILE_INVALID_TYPE);
        }
        // create a directory if not exist
        this.fileService.createDirectory(baseURI + folder);

        // store file
        String uploadFile = this.fileService.store(file, folder);

        FileUploadResponse response = new FileUploadResponse(uploadFile, Instant.now());
        return ApiResponse.<FileUploadResponse>builder()
                .code(1000)
                .data(response).build();

    }

}
