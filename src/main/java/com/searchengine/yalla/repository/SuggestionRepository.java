package com.searchengine.yalla.repository;

import com.searchengine.yalla.entity.Suggestion;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.domain.Pageable;

@CrossOrigin("*")
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

    @RestResource(path = "suggestions")
    Page<Suggestion> findBySuggestionStartingWith(@Param("suggestion") String suggestion, Pageable pageable);
}
