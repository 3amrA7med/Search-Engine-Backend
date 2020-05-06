package com.searchengine.yalla.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="yalla_temp_search", schema = "yalla-dev")
@Setter
@Getter
@ToString
public class Search {

    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "url")
    private String url;

    //Setters and Getters.
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) { this.title = title;}

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}
