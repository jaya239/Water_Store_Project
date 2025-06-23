package com.example.Water_Store.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class BackupService {

    @Value("${backup.mysql.username}")
    private String dbUser;

    @Value("${backup.mysql.password}")
    private String dbPass;

    @Value("${backup.mysql.database}")
    private String dbName;

    @Value("${backup.output.dir}")
    private String backupDir;

    @Scheduled(cron = "0 0 0 * * ?") // Every day at midnight
    public void backupDatabase() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filePath = backupDir + File.separator + "backup_" + timestamp + ".sql";

            ProcessBuilder pb = new ProcessBuilder(
                    "mysqldump",
                    "-u" + dbUser,
                    "-p" + dbPass,
                    dbName
            );
            pb.redirectOutput(new File(filePath));
            pb.redirectErrorStream(true);

            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                log.info("✅ Backup successful: " + filePath);
            } else {
                log.error("❌ Backup failed. Exit code: " + exitCode);
            }

        } catch (Exception e) {
            log.error("❌ Error during DB backup", e);
        }
    }
}
