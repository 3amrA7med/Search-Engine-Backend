package com.searchengine.yalla.controller;

import com.searchengine.yalla.entity.Suggestion;
import com.searchengine.yalla.ranker.PageRanker;
import com.searchengine.yalla.repository.SuggestionRepository;
import com.searchengine.yalla.utils.Stemmer;

import org.json.JSONException;
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

    YallaController(SuggestionRepository suggestionRepository) throws FileNotFoundException {
        this.suggestionRepository = suggestionRepository;
        this.stemmer = new Stemmer();
        this.stopWords = this.Stop_Words();
        this.pageRanker = new PageRanker();
    }

    @RequestMapping(method = RequestMethod.POST, value = spring_path + "/addSuggestion")
    public void addSuggestion(@RequestBody Suggestion suggestion)
        /*
            * This function add the suggestion to table suggestion
            *
            * */
    {
        System.out.println(suggestion.getSuggestion());
        try {
            suggestionRepository.save(suggestion);
        }
        catch (DataIntegrityViolationException e)
        {
            System.out.println(suggestion.getSuggestion()+" suggestion already exists");
        }
    }

    @RequestMapping(method = RequestMethod.GET,
            value = spring_path + "/getResults/{value}/{index}/{size}")
    public List<Object> getResults(@PathVariable("value") String value,
                                   @PathVariable("index") Integer index,
                                   @PathVariable("size") Integer size) throws JSONException {
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


        Dictionary result1 = new Hashtable();

        result1.put("url","https://www.google.com");
        result1.put("title","Google");
        result1.put("description","google search engine");
        Dictionary result2 = new Hashtable();
        result2.put("url","https://www.google.com");
        result2.put("title","Google2");
        result2.put("description","google search engine2");
        List<Object>Results = new ArrayList<Object>();
        Results.add(result1);
        Results.add(result2);
        Dictionary searchResults = new Hashtable();
        searchResults.put("searchResults",Results);
        System.out.println(searchResults);
        Dictionary page = new Hashtable();
        Dictionary pageDetails = new Hashtable();
        page.put("totalSize","2");
        pageDetails.put("pageDetails",page);

        response.add(searchResults);
        response.add(pageDetails);
        System.out.println(response);
        return response;
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
