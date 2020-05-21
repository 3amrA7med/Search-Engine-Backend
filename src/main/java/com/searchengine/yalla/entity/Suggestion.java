package com.searchengine.yalla.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="yalla_suggestions", schema = "yalla-dev")
@Setter
@Getter
@ToString
public class Suggestion {

    @Id
    @Column(name = "suggestion")
    private String suggestion;

    public String getSuggestion() {
        return this.suggestion;
    }
    public void setSuggestion(String suggestion) { this.suggestion = suggestion;}

}
