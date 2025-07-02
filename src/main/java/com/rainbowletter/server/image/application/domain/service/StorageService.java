package com.rainbowletter.server.image.application.domain.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void uploadFile(byte[] data, String contentType, String filePath) {
        try {
            ObjectMetadata metadata = createMetadataFromFile(data, contentType);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);

            amazonS3.putObject(
                new PutObjectRequest(bucket, filePath, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (AmazonS3Exception e) {
            log.error("S3 업로드 실패: 버킷={}, 경로={}", bucket, filePath, e);
            throw new RainbowLetterException("S3 업로드 실패: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("알 수 없는 오류로 S3 업로드 실패: 경로={}", filePath, e);
            throw new RainbowLetterException("파일 업로드 중 알 수 없는 오류가 발생했습니다.", e);
        }
    }

    private ObjectMetadata createMetadataFromFile(byte[] data, String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(data.length);
        return metadata;
    }
}
