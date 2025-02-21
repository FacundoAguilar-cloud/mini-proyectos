package com.proyect.webscrapper.controllers;

import com.proyect.webscrapper.models.Webpage;
import com.proyect.webscrapper.repository.WebpageRepository;
import com.proyect.webscrapper.service.SpiderService;
import com.proyect.webscrapper.service.WebscrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController

public class WebpageController {

    @Autowired
    WebpageRepository repository;

    @Autowired
    WebscrapperService webscrapperService;

    @Autowired
    SpiderService spiderService;

    // tener en cuenta que no vamos a hacer el tipico CRUD, vamos a hacer una busqueda

    @GetMapping("/api/search")
    public List<Webpage> search(@RequestParam String query) {
        List<Webpage> list = new ArrayList<>();

        Iterable<Webpage> results = repository.findAll();
        for (Webpage webpage : results) {
            list.add(webpage);
        }
        return list;


    }

    @GetMapping("/api/webscrapper")
    public void webscrapper(@RequestParam String url) throws IOException {
        webscrapperService.ScrapAndSave(url);
    }

    @GetMapping("/api/start")
    public void start() throws IOException {
        spiderService.start();

    }
}