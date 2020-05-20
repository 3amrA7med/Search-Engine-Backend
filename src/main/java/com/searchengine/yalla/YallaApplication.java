package com.searchengine.yalla;

import com.searchengine.yalla.ranker.MySqlTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YallaApplication {

	public static void main(String[] args) {
		SpringApplication.run(YallaApplication.class, args);
		MySqlTest.connect();
	}

}
