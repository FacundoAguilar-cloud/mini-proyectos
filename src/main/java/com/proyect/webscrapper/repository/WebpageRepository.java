package com.proyect.webscrapper.repository;

import com.proyect.webscrapper.models.Webpage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebpageRepository extends CrudRepository<Webpage, Integer> {
    Webpage findByUrl(String url);
}
