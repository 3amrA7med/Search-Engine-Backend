package com.searchengine.yalla.repository;

import com.searchengine.yalla.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(collectionResourceRel = "products", path="product")
public interface SearchRepository extends JpaRepository<Search, Long> {
}
