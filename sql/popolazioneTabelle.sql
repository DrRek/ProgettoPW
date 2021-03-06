-- Carattere di escape '\r\n', i null sono indicati con '\N', la data del sistema deve essere in formato yyyy-MM-dd per usare i csv con Excel.
-- luca mac //Users//luca//Desktop//ProgettoPW//sql//csv//
-- luca desktop C:\\Users\\Luca\\Desktop\\ProgettoPW\\sql\\csv\\

use quattrocchiDB;
LOAD DATA LOCAL INFILE '//Users//luca//Desktop//ProgettoPW//sql//csv//Articolo.csv'
	INTO TABLE Articolo
		FIELDS TERMINATED BY ';' LINES TERMINATED BY '\n'
	();
LOAD DATA LOCAL INFILE '//Users//luca//Desktop//ProgettoPW//sql//csv//Occhiale.csv'
	INTO TABLE Occhiale
		FIELDS TERMINATED BY ';' LINES TERMINATED BY '\n'
	();
LOAD DATA LOCAL INFILE '//Users//luca//Desktop//ProgettoPW//sql//csv//Lentine.csv'
	INTO TABLE Lentine
		FIELDS TERMINATED BY ';' LINES TERMINATED BY '\n'
	();
LOAD DATA LOCAL INFILE '//Users//luca//Desktop//ProgettoPW//sql//csv//Amministratore.csv'
	INTO TABLE Amministratore
		FIELDS TERMINATED BY ';' LINES TERMINATED BY '\n'
	();
LOAD DATA LOCAL INFILE '//Users//luca//Desktop//ProgettoPW//sql//csv//Cliente.csv'
	INTO TABLE Cliente
		FIELDS TERMINATED BY ';' LINES TERMINATED BY '\n'
	();
LOAD DATA LOCAL INFILE '//Users//luca//Desktop//ProgettoPW//sql//csv//Disponibilita.csv'
	INTO TABLE Disponibilita
		FIELDS TERMINATED BY ';' LINES TERMINATED BY '\n'
	();
