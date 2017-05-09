-- Carattere di escape '\r\n', i null sono indicati con '\N', la data del sistema deve essere in formato yyyy-MM-dd per usare i csv con Excel.
use quattrocchiDB;
LOAD DATA LOCAL INFILE '//Users//luca//Desktop//ProgettoPW//sql//csv//Articolo.csv'
	INTO TABLE Articolo
		FIELDS TERMINATED BY ';' LINES TERMINATED BY '\r\n'
	();