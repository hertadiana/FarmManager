create database ferma;
use ferma;

create table vaci(
    id int PRIMARY KEY AUTO_INCREMENT,nume VARCHAR(255) NOT NULL ,numar INT NOT NULL,data_nastere DATE NOT NULL)
INSERT INTO vaci(nume,numar, data_nastere) VALUES ("Joiana",1234,'2021-01-01')