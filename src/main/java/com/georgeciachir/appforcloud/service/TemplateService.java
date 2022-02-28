package com.georgeciachir.appforcloud.service;

import com.georgeciachir.appforcloud.exception.HtmlTemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.lang.String.format;

@Service
public class TemplateService {

    private static final Logger LOG = LoggerFactory.getLogger(TemplateService.class);
    private static final String GREETING_HTML = "greeting.html";

    private final String htmlLocation;
    private final String initialHtmlTemplate;

    public TemplateService(@Value("${defaultHtmlLocation}") String defaultHtmlLocation,
                           @Value("${htmlLocation}") String htmlLocation) {
        this.htmlLocation = htmlLocation;
        this.initialHtmlTemplate = getInitialContentFromDisk(defaultHtmlLocation);
    }

    public String getTemplate() {
        LOG.info("Retrieving html template from disk");
        try {
            return getContentFromDisk(htmlLocation);
        } catch (HtmlTemplateException e) {
            LOG.warn("Retrieving the html template from memory");
            return initialHtmlTemplate;
        }
    }

    public void updateTemplate(MultipartFile file) {
        LOG.info("Updating html template");
        writeTemplateToDisk(htmlLocation, file);
    }

    private static String getContentFromDisk(String htmlLocation) {
        Path htmlTemplateLocation = Paths.get(htmlLocation).resolve(GREETING_HTML);
        try {
            return Files.readString(htmlTemplateLocation);
        } catch (IOException e) {
            String errorMessage = format("Could not find the html template at the specified location: %s", htmlTemplateLocation);
            throw new HtmlTemplateException(errorMessage, e);
        }
    }

    private static String getInitialContentFromDisk(String htmlLocation) {
        Path htmlTemplateLocation = Paths.get(htmlLocation).resolve(GREETING_HTML);
        try {
            return Files.readString(htmlTemplateLocation);
        } catch (IOException e) {
            LOG.warn("Could not find the html template at the specified location: {}", htmlTemplateLocation);
            return null;
        }
    }

    private static void writeTemplateToDisk(String htmlLocation, MultipartFile file) {
        Path htmlTemplateLocation = Paths.get(htmlLocation).resolve(GREETING_HTML);
        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, htmlTemplateLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new HtmlTemplateException(e.getMessage(), e);
        }
    }
}
