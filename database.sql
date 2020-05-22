-- create database Yalla search engine
DROP SCHEMA IF EXISTS `yalla-dev`;
CREATE SCHEMA `yalla-dev`;

-- select the database
USE `yalla-dev` ;


  -- create category table tbl_category
CREATE TABLE IF NOT EXISTS `yalla-dev`.`yalla_suggestions` (
  suggestion VARCHAR(255) NOT NULL PRIMARY KEY
  );
  

INSERT INTO yalla_suggestions
(
	suggestion
)
VALUES 
(
	'Facebook'
);

INSERT INTO yalla_suggestions
(
	suggestion
)
VALUES 
(
	'Twitter'
);
INSERT INTO yalla_suggestions
(
	suggestion
)
VALUES 
(
	'Linkedin'
);
INSERT INTO yalla_suggestions
(
	suggestion
)
VALUES 
(
	'Github'
);
INSERT INTO yalla_suggestions
(
	suggestion
)
VALUES 
(
	'Faculty Of Engineering'
);
INSERT INTO yalla_suggestions
(
	suggestion
)
VALUES 
(
	'Java'
);
