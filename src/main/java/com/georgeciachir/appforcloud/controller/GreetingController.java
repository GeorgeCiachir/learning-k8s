package com.georgeciachir.appforcloud.controller;

import com.georgeciachir.appforcloud.exception.TemplateLocationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.format;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@RestController
@RequestMapping
public class GreetingController {

    @Value("${htmlLocation}")
    private String htmlLocation;

    @GetMapping(value = "/hello/{name}", produces = TEXT_HTML_VALUE)
    public String sayHello(@PathVariable String name) {
        Path htmlTemplateLocation = Paths.get(htmlLocation).resolve("greeting.html");
        try {
            String htmlTemplate = Files.readString(htmlTemplateLocation);
            return format(htmlTemplate, name);
        } catch (IOException e) {
            String errorMessage = format("Could not find the html template at this location: %s", htmlTemplateLocation);
            throw new TemplateLocationException(errorMessage);
        }
    }
}
