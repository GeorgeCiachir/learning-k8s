package com.georgeciachir.appforcloud.service;

import com.georgeciachir.appforcloud.exception.HtmlTemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class TemplateService {

    @Value("${htmlLocation}")
    private String htmlLocation;

    public void updateTemplate(MultipartFile file) {
        Path htmlTemplateLocation = Paths.get(htmlLocation).resolve("greeting.html");
        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, htmlTemplateLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new HtmlTemplateException(e.getMessage(), e);
        }
    }
}
