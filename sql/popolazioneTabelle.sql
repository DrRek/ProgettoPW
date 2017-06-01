-- Carattere di escape '\r\n', i null sono indicati con '\N', la data del sistema deve essere in formato yyyy-MM-dd per usare i csv con Excel.
-- luca mac //Users//luca//Desktop//ProgettoPW//sql//csv//
-- luca desktop C:\\Users\\Luca\\Desktop\\ProgettoPW\\sql\\csv\\

use quattrocchiDB;
LOAD DATA LOCAL INFILE 'C:\\Users\\Luca\\Desktop\\ProgettoPW\\sql\\csv\\Articolo.csv'
	INTO TABLE Articolo
		FIELDS TERMINATED BY ';' LINES TERMINATED BY '\r\n'
	();