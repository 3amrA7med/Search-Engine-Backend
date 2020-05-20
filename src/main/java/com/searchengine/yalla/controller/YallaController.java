package com.searchengine.yalla.controller;

import com.searchengine.yalla.entity.Suggestion;
import com.searchengine.yalla.ranker.PageRanker;
import com.searchengine.yalla.repository.SuggestionRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;


import java.sql.SQLException;
import java.util.ArrayList;

@CrossOrigin("*")
@RestController
public class YallaController {

    private final String spring_path = "/api/v1";
    private final SuggestionRepository suggestionRepository;

    YallaController(SuggestionRepository suggestionRepository){
        this.suggestionRepository = suggestionRepository;

    }

    @RequestMapping(method = RequestMethod.POST, value = spring_path + "/addSuggestion")
    public void addSuggestion(@RequestBody Suggestion suggestion) throws SQLException
        /*
            * This function add the suggestion to table suggestion
            *
            * */
    {
        suggestionRepository.save(suggestion);

    }

    @RequestMapping(method = RequestMethod.GET, value = spring_path + "/test")
    public void testRanker() throws SQLException {
        /*
        * test function for ranker functions
        * http://localhost:8080/api/v1/test
        * */

        //MySqlTest.connect();
        System.out.println("Inside ranker");
        PageRanker p1 = new PageRanker();
        //p1.pagePopularity();

        ArrayList<String> searchWords = new ArrayList<String>();
        searchWords.add("metro");
        searchWords.add("javatpoint");
        searchWords.add("loop");
        searchWords.add("mobile");
        searchWords.add("plate");
        p1.pageRelevance(searchWords,false);
        //p1.imageRelevance(searchWords);
    }


}
