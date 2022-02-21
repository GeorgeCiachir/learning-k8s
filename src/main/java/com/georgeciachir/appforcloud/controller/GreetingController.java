package com.georgeciachir.appforcloud.controller;

import com.georgeciachir.appforcloud.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@RestController
@RequestMapping
public class GreetingController {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingController.class);

    private final TemplateService templateService;

    public GreetingController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping(value = "/hello/{name}", produces = TEXT_HTML_VALUE)
    public String sayHello(@PathVariable String name) {
        LOG.info("Greeting [{}]", name);
        String htmlTemplate = this.templateService.getTemplate();
        return format(htmlTemplate, name);
    }
}
