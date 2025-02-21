package com.proyect.webscrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service

public class SpiderService {
    @Autowired
    private WebscrapperService webscrapperService;

        public void start() throws IOException {
            String initialLink = "https://www.recetasgratis.net/";
            getAndScrape(initialLink);
        }






        public void getAndScrape(String url ) throws IOException {
            String initialLink = "https://www.recetasgratis.net/";

            List<String> links = webscrapperService.getAllLinks(url);
            links.stream().forEach(link -> {
                try {
                    webscrapperService.ScrapAndSave(link);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    getAndScrape(link);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });}}



