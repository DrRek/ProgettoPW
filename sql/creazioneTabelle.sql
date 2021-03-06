create schema quattrocchiDB;
use quattrocchiDB;

create user if not exists
	progetto@localhost identified by 'pw';
	
grant all on quattrocchiDB.* to progetto@localhost;

create table Amministratore(
	User varchar(16) primary key,
	Pwd varchar(10) not null
);

create table Cliente(
	User varchar(16) primary key,
	Pwd varchar(10) not null,
	Nome varchar(20) not null,
	Cognome varchar(20) not null,
	DataNascita date not null,
	Stato varchar(20) not null,
	Cap decimal(5) not null,
	Indirizzo varchar(35) not null,
	Email varchar(30) not null unique
);

create table CartaCredito(
	NumeroCC decimal(16) primary key,
	Intestatario varchar(40) not null,
	Circuito varchar(20) not null,
	DataScadenza date not null,
	CvcCvv decimal(3) not null,
	Cliente varchar(16) not null,
	Stato varchar(16) not null,
	foreign key (Cliente) references Cliente(User) on delete cascade on update cascade
);

create table Prescrizione(
	Codice varchar(36) primary key,
	Cliente char(16) not null,
	NomePrescrizione varchar(20),
	SferaSinistra decimal(4,2),
	CilindroSinistra decimal(3,2),
	AsseSinistra decimal(3),
	SferaDestra decimal(4,2),
	CilindroDestra decimal(3,2),
	AsseDestra decimal(3),
	AddizioneVicinanza decimal(3,2),
	PrismaOrizSinistra decimal(3,2),
	PrismaOrizSinistraBaseDirection varchar(3),
	PrismaVertSinistra decimal(3,2),
	PrismaVertSinistraBaseDirection varchar(3),
	PrismaOrizDestra decimal(3,2),
	PrismaOrizDestraBaseDirection varchar(3),
	PrismaVertDestra decimal(3,2),
	PrismaVertDestraBaseDirection varchar(3),
	PDSinistra decimal(3,1),
	PDDestra decimal(3,1),
	foreign key (Cliente) references Cliente(User) on delete cascade on update cascade
);

create table Ordine(
	Codice varchar(36) primary key,
   	DataEsecuzione date not null,
	Costo decimal(8,2) not null,
	CartaCredito decimal(16) not null,
	Cliente char(16) not null,
	foreign key (CartaCredito) references CartaCredito(NumeroCC),
	foreign key (Cliente) references Cliente(User)
);

create table Articolo(
	Nome varchar(40) not null,
	Marca varchar(20) not null,
	Tipo varchar(1) not null,
	Prezzo decimal(6,2) not null,
	img1 varchar(60),
	primary key(Nome, Marca)
);

create table Occhiale(
	Nome varchar(40) not null,
	Marca varchar(20) not null,
	Descrizione varchar(200),
	Sesso char(1),
	NumeroPezziDisponibili decimal(3) not null,
	img1 varchar(60),
	img2 varchar(60),
	foreign key (Nome,Marca) references Articolo(Nome,Marca),
    primary key(Nome, Marca)
);

create table Lentine(
	Nome varchar(40) not null,
	Marca varchar(20) not null,
	Tipologia char(1),
	NumeroPezziNelPacco decimal(2),
	Raggio decimal(3,1),
	Diametro decimal(3,1),
	Colore varchar(10),
	primary key(Nome, Marca),
	foreign key (Nome,Marca) references Articolo(Nome,Marca)
);

create table Appartenenza(
	Ordine varchar(36),
	NomeArticolo varchar(40) not null,
    MarcaArticolo varchar(20) not null,
	Prescrizione varchar(36),
	PrezzoVendita decimal (6,2) not null,
	Gradazione decimal(4,2),
    Quantita decimal(3),
	primary key(Ordine, NomeArticolo, MarcaArticolo),
	foreign key(Ordine) references Ordine(Codice),
	foreign key(NomeArticolo, MarcaArticolo) references Articolo(Nome, Marca),
	foreign key(Prescrizione) references Prescrizione(Codice) on delete set null
);

create table Promozione(
	Nome varchar(20) primary key,
	Descrizione varchar(50),
	Sconto decimal(4,2) not null,
    Tipo char(1) not null,
	DataInizio date not null,
	DataFine date not null,
	Cumulabile boolean not null
);

create table Validita(
	Promozione varchar(20),
	NomeArticolo varchar(40),
    MarcaArticolo varchar(20),
	primary key(Promozione, NomeArticolo, MarcaArticolo),
	foreign key (Promozione) references Promozione(Nome),
	foreign key (NomeArticolo, MarcaArticolo) references Articolo(Nome, Marca)
);

create table Disponibilita(
	Nome varchar(40) not null,
	Marca varchar(20) not null,
	NumeroPezziDisponibili decimal(3) not null,
	Gradazione decimal(4,2),
	foreign key(Nome,Marca) references Lentine(Nome,Marca),
	primary key(Nome,Marca,Gradazione)
);
	