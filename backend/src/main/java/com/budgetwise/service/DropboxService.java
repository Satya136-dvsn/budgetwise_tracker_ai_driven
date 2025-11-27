package com.budgetwise.service;

import com.budgetwise.config.ExternalApiConfig;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class DropboxService {

    private static final Logger logger = LoggerFactory.getLogger(DropboxService.class);
    private final ExternalApiConfig apiConfig;
    private final OkHttpClient client;

    public DropboxService(ExternalApiConfig apiConfig) {
        this.apiConfig = apiConfig;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public String uploadBackup(byte[] data, String filename) {
        String accessToken = apiConfig.getDropboxApiKey();
        if (accessToken == null || accessToken.isEmpty()) {
            throw new RuntimeException("Dropbox Access Token is missing");
        }

        // Escape quotes in filename just in case
        String safeFilename = filename.replace("\"", "");
        String arg = "{\"path\": \"/" + safeFilename
                + "\", \"mode\": \"overwrite\", \"autorename\": true, \"mute\": false, \"strict_conflict\": false}";

        RequestBody body = RequestBody.create(data, MediaType.parse("application/octet-stream"));

        Request request = new Request.Builder()
                .url("https://content.dropboxapi.com/2/files/upload")
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Dropbox-API-Arg", arg)
                .addHeader("Content-Type", "application/octet-stream")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No body";
                logger.error("Dropbox Upload Failed: {} - {}", response.code(), errorBody);
                throw new RuntimeException("Dropbox upload failed: " + response.code() + " " + errorBody);
            }
            return "Backup uploaded successfully to Dropbox: /" + safeFilename;
        } catch (IOException e) {
            logger.error("Error uploading to Dropbox", e);
            throw new RuntimeException("Error uploading to Dropbox", e);
        }
    }
}
