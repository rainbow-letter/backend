package com.rainbowletter.server.image.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.image.adapter.in.web.dto.ImageCreateResponse;
import com.rainbowletter.server.image.application.domain.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
@Tag(name = "image")
@RequiredArgsConstructor
@WebAdapter
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "프로필 이미지 업로드")
    @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageCreateResponse> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        final ImageCreateResponse response = imageService.uploadImage(file, "profile");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "편지 이미지 업로드")
    @PostMapping(value = "/letter", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageCreateResponse> uploadLetterImage(@RequestParam("file") MultipartFile file) {
        final ImageCreateResponse response = imageService.uploadImage(file, "letter");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
