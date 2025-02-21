package com.proyect.webscrapper.service;

import com.proyect.webscrapper.models.Webpage;
import com.proyect.webscrapper.repository.WebpageRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service



//jsoup nos va ayudar a bajar cosas de la web mucho más facil (Document viene de esa libreria)
public class WebscrapperService {
    @Autowired
    private WebpageRepository repository;

    public void ScrapAndSave(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        // basicamente la liberia se va a conectar a una URL, la descarga y la va a almacenar en la variable document

        // el proximo paso es obtener datos de la página web

        String title = document.title();
        String description = document.select("meta[name=description]").attr("content");
        String picture = document.select("meta[property = og:image]").attr("content");
        String domain = getDomainFromUrl(url);

        //nota: el select es un selector de CSS

        Webpage webpage = new Webpage();
        webpage.setTitle(title);
        webpage.setDescription(description);
        webpage.setDomain(null);
        webpage.setUrl(url);
        webpage.setPicture(picture);
        webpage.setDomain(domain);

        repository.save(webpage);


        }
    private String getDomainFromUrl(String url) {
        String domain = url.replaceFirst("^(https?://)?(www\\.)?", "");
        int index = domain.indexOf('/');
        if (index != -1) {
            domain = domain.substring(0, index);
        }
        return domain;
    }

    public List<String> getAllLinks(String url) throws IOException {
       Webpage findWebpage = repository.findByUrl(url);
       if (findWebpage != null){
           return new ArrayList<>();
       }
        List <String> result = new ArrayList<>();
        Document document = Jsoup.connect(url).get();
        Elements links = document.select("a[href]");
        for (Element link: links){
            String LinkHref = link.attr("href");
            if (!result.contains(LinkHref)){
                result.add(LinkHref);
            }
            if (LinkHref.startsWith("/")){
                LinkHref = getDomainFromUrl(url) + LinkHref;
            }

        }
        return result;
    }
}


