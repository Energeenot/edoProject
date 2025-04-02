package com.example.edo.services.s3;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class SelectelS3Client {

    private final String bucketName;
    private final S3Client s3Client;

    public SelectelS3Client(@Value("${s3.access-key}")String accessKey,
                            @Value("${s3.secret-key}") String secretKey,
                            @Value("${s3.endpoint}") String endpointUrl,
                            @Value("${s3.bucket-name}") String bucketName,
                            @Value("${s3.region}") String region) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        this.s3Client = S3Client.builder()
                .endpointOverride(URI.create(endpointUrl))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .region(Region.of(region))
                .build();
        this.bucketName = bucketName;
    }

    public void uploadFile(List<MultipartFile> files, String uniqueFileName) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String extension = FilenameUtils.getExtension(originalFilename);
                String zipEntryName = originalFilename + "." + extension;

                zos.putNextEntry(new ZipEntry(zipEntryName));
                zos.write(file.getBytes());
                zos.closeEntry();
            }
            zos.finish();

            byte[] bytes = baos.toByteArray();

            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFileName)
                    .contentType("application/zip")
                    .build();

            s3Client.putObject(putRequest, RequestBody.fromBytes(bytes));
        }
    }


}
