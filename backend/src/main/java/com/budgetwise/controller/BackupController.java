package com.budgetwise.controller;

import com.budgetwise.security.UserPrincipal;
import com.budgetwise.service.DropboxService;
import com.budgetwise.service.ExportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/api/backup")
public class BackupController {

    private final ExportService exportService;
    private final DropboxService dropboxService;

    public BackupController(ExportService exportService, DropboxService dropboxService) {
        this.exportService = exportService;
        this.dropboxService = dropboxService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadBackup(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            byte[] data = exportService.exportAllDataJSON(userPrincipal.getId());
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = "budgetwise_backup_" + userPrincipal.getId() + "_" + timestamp + ".json";

            String result = dropboxService.uploadBackup(data, filename);
            return ResponseEntity.ok(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}
