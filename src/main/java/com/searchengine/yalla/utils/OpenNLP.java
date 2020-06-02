package com.searchengine.yalla.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

public class OpenNLP {
	
	private String Country = null;
	private ArrayList<String> Names = new ArrayList<String>();
	Trends_DB_Handler db;

	public OpenNLP(){
		this.db= new Trends_DB_Handler();
		this.db.connect();
	}
	public static void print(Object o)
	{
		System.out.println(o);
	}
	public String[] String_preprocessing(ArrayList<String> text)
	{
		// Convert ArrayList to object array 
        Object[] objArr = text.toArray();   
        // Getting the sentence in the form of String array
        String[] words = Arrays.copyOf(objArr, objArr.length,String[].class);
     
        
        String str = null;
        int index = 0;
        for(String s : words)
        {
        	str = "";
        	str = s.toLowerCase();
        	str = str.trim();
        	
        	if(str.isEmpty())
        	{
  
        		index +=1;
        		continue;
        	}
        	str = str.substring(0, 1).toUpperCase() + str.substring(1);
        	words[index] = str;
        	index +=1;
        }
        
        return words;
		
	}
	public void NamesExtraction(ArrayList<String> text, String c) {
		this.Country = c;
		this.Names = new ArrayList<String>();
		String sentence [] = String_preprocessing(text);
		// Loading the NER - Person model
		InputStream inputStream = null;
		TokenNameFinderModel model = null;
		try {
			inputStream = new FileInputStream("src/main/java/com/searchengine/yalla/en-ner-person.bin");
			model = new TokenNameFinderModel(inputStream);
			inputStream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Instantiating the NameFinder class
		NameFinderME nameFinder = new NameFinderME(model);

		
		/*for (String s : sentence)
		{
			System.out.println(s);
		}*/
		// Finding the names in the sentence
		Span nameSpans[] = nameFinder.find(sentence);
		
		
		if(nameSpans.length == 0)
		{
			System.out.println("No Names founded");
			return;
		}
		// Printing the spans of the names in the sentence
		for (Span s : nameSpans)
		{
			System.out.println(s.toString() + " " + sentence[s.getStart()]);
		}
		 System.out.println("********************************");
		 
		String FoundedName ="";
		for(Span Name: nameSpans) {
    	    for(int i = Name.getStart(); i < Name.getEnd(); i++)
    	    {
    	        FoundedName += sentence[i];
    	        if(i != Name.getEnd() - 1)
    	        	FoundedName += " ";
    	    }
    	    //System.out.println(FoundedName);
    	    Names.add(FoundedName);
    	    FoundedName ="";
		}
		for(String s :Names )
		{
			this.db.Insert_Row(s, this.Country);
		}
		
	}
}
// names :
/*
 * queryWords.add("Tom");
 * 
 * 		queryWords.add("Paul");
		queryWords.add("Pogba");
		
		queryWords.add("Wayne");
		queryWords.add("rooney");
		
		queryWords.add("Harry");
		queryWords.add("Kane");
		
		queryWords.add("David");
		queryWords.add("Silva");
		
		queryWords.add("Tom");
		queryWords.add("Hardy");
		
		queryWords.add("Ahmed");
		queryWords.add("Hassan");
		
		queryWords.add("Roger");
		queryWords.add("Federer");
		
		queryWords.add("Rafael");
		queryWords.add("Nadal");
		
		queryWords.add("Donald");
		queryWords.add("Trump");
		
		queryWords.add("Mike");
		
		
		queryWords.add("Charile");
		queryWords.add("James");
		queryWords.add("Khaled");
		queryWords.add("Omar");
		queryWords.add("David");
		
		
 * */
