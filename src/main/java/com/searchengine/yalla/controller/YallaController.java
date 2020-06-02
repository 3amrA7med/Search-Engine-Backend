package com.searchengine.yalla.controller;

import com.searchengine.yalla.entity.Suggestion;
import com.searchengine.yalla.ranker.PageRanker;
import com.searchengine.yalla.repository.SuggestionRepository;
import com.searchengine.yalla.utils.OpenNLP;
import com.searchengine.yalla.utils.Stemmer;

import com.searchengine.yalla.utils.Trends_DB_Handler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;


import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;

@CrossOrigin("*")
@RestController
public class YallaController {

    private final String spring_path = "/api/v1";
    private final SuggestionRepository suggestionRepository;
    private PageRanker pageRanker;
    private Stemmer stemmer;
    private List<String> stopWords;
    private OpenNLP openNlp;
    private Trends_DB_Handler trends_db_handler;

    YallaController(SuggestionRepository suggestionRepository) throws FileNotFoundException {
        this.suggestionRepository = suggestionRepository;
        this.stemmer = new Stemmer();
        this.stopWords = this.Stop_Words();
        this.pageRanker = new PageRanker();
        this.openNlp = new OpenNLP();
        this.trends_db_handler = new Trends_DB_Handler();
        this.trends_db_handler.connect();
    }

    @RequestMapping(method = RequestMethod.POST, value = spring_path + "/addSuggestion/{country}")
    public void addSuggestion(@RequestBody Suggestion suggestion,
                              @PathVariable("country") String  country)
        /*
            * This function add the suggestion to table suggestion
            *
            * */
    {
        ArrayList<String> trendQuery = new ArrayList<String>();
        String[] words = suggestion.getSuggestion().trim().split("\\s+");
        try {
            System.out.println("POSTING");
            suggestionRepository.save(suggestion);
            int i = 0;
            while(i < words.length) {
                trendQuery.add(words[i]);
                i++;
            }
            this.openNlp.NamesExtraction(trendQuery,country);
        }
        catch (DataIntegrityViolationException e)
        {
            System.out.println(suggestion.getSuggestion()+" suggestion already exists");
        }
    }

    @RequestMapping(method = RequestMethod.GET,
            value = spring_path + "/getResults/{value}/{index}/{size}/{phrase}")
    public List<Object> getResults(@PathVariable("value") String value,
                                   @PathVariable("index") Integer index,
                                   @PathVariable("size") Integer size,
                                   @PathVariable("phrase")Boolean phrase) throws SQLException {
        String[] words = value.trim().split("\\s+");
        ArrayList<String> searchWords = new ArrayList<String>();

        int i = 0;
        while(i < words.length) {
            //get lower case of the word
            String lower = words[i].toLowerCase();

            // if stop word (do nothing)
            if (this.stopWords.contains(lower) == false) {
                // stem the word
                this.stemmer.add(lower.toCharArray(), lower.length());
                this.stemmer.stem();
                lower = this.stemmer.toString();
                searchWords.add(lower);
            }
            i++;
        }
        System.out.println("Search Words:");
        System.out.println(searchWords);

        // Fixed data to test with it
        List<Object> response = new ArrayList<Object>();
        response = this.pageRanker.pageRelevance(searchWords,phrase,index*size,size);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET,
            value = spring_path + "/getImageResults/{value}/{index}/{size}")
    public List<Object> getImageResults(@PathVariable("value") String value,
                                        @PathVariable("index") Integer index,
                                        @PathVariable("size") Integer size) throws SQLException {
        String[] words = value.trim().split("\\s+");
        ArrayList<String> searchWords = new ArrayList<String>();

        int i = 0;
        while(i < words.length) {
            //get lower case of the word
            String lower = words[i].toLowerCase();

            // if stop word (do nothing)
            if (this.stopWords.contains(lower) == false) {
                // stem the word
                this.stemmer.add(lower.toCharArray(), lower.length());
                this.stemmer.stem();
                lower = this.stemmer.toString();
                searchWords.add(lower);
            }
            i++;
        }
        System.out.println("Search Words:");
        System.out.println(searchWords);

        // Fixed data to test with it
        List<Object> response = new ArrayList<Object>();

        response = this.pageRanker.imageRelevance(searchWords,index*size,size);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = spring_path + "/getTrends/{country}")
    public List<Object> getTrends(@PathVariable("country") String country) {
        /*
        * function return trends
        * */
        return this.trends_db_handler.GetTrends(country);
    }

    // Utility function to get stop words
    public static List<String> Stop_Words() throws FileNotFoundException {

        File file = new File("src/main/java/com/searchengine/yalla/stop_words.txt");
        Scanner fscn = new Scanner(file);
        List<String> stopWords = new ArrayList<String>();
        while(fscn.hasNext()) {
            //skip triangle wordScanner
            stopWords.add(fscn.nextLine());
        }
        fscn.close();
        return stopWords;
    }


}
