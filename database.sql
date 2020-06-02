-- create database Yalla search engine
DROP SCHEMA IF EXISTS `yalla-prod`;
CREATE SCHEMA `yalla-prod`;

-- select the database
USE `yalla-prod` ;


  -- create category table tbl_category
CREATE TABLE IF NOT EXISTS `yalla-prod`.`yalla_suggestions` (
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
