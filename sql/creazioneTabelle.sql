create schema quattrocchiDB;
use quattrocchiDB;

create user if not exists
	progetto@localhost identified by 'pw';
    
grant all on quattrocchiDB.* to progetto@localhost;

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
    CvcCvv decimal(3) not null
);

create table Sottoscrizione(
	Cliente char(16) not null,
	CartaCredito decimal(16) not null,
    foreign key (Cliente) references Cliente(CF) on delete cascade on update cascade,
    foreign key (CartaCredito) references CartaCredito(NumeroCC) on delete cascade on update cascade
);

create table Prescrizione(
	Codice varchar(36) primary key,
    TIpoPrescrizione varchar(20),
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
    PDDestra decimal(3,1)
);

create table Associazione(
	Cliente char(16) not null,
    Prescrizione varchar(36) not null,
    primary key(Cliente, Prescrizione),
    foreign key (Cliente) references Cliente(CF) on delete cascade on update cascade,
    foreign key (Prescrizione) references Prescrizione(Codice) on delete cascade on update cascade
);

create table Ordine(
	Codice varchar(36) primary key,
    DataEsecuzione date not null,
    Costo decimal(8,2) not null,
    CartaCredito decimal(16) not null,
    foreign key (CartaCredito) references CartaCredito(NumeroCC)
);

create table Esecuzione(
	Ordine varchar(36) not null, 
	Cliente char(16) not null,
    primary key(Ordine, Cliente),
    foreign key (Ordine) references Ordine(Codice),
    foreign key (Cliente) references Cliente(CF)
);

create table Utilizzo(
	Ordine varchar(36) not null, 
	CartaCredito decimal(16) not null,
    primary key(Ordine, CartaCredito),
    foreign key (Ordine) references Ordine(Codice),
    foreign key (CartaCredito) references CartaCredito(NumeroCC)
);

create table Articolo(
	Nome varchar(40) not null,
    Marca varchar(20) not null,
    Tipo varchar(20) not null,
    NumeroPezziDisponibili decimal(3) not null,
    Prezzo decimal(6,2) not null,
    Gradazione decimal(4,2),
    img1 varchar(30),
    img2 varchar(30),
    img3 varchar(30),
    primary key(Nome, Marca)
);

create table Appartenenza(
	Ordine varchar(36),
    Articolo varchar(20),
    Prescrizione varchar(36),
    PrezzoVendita decimal (6,2) not null,
    primary key(Ordine, Articolo),
    foreign key (Ordine) references Ordine(Codice),
    foreign key(Articolo) references Articolo(Nome),
    foreign key(Prescrizione) references Prescrizione(Codice)
);

create table Promozione(
	Nome varchar(20) primary key,
    Descrizione varchar(50),
    Sconto decimal(4,2) not null,
    DataInizio date not null,
    DataFine date not null
);

create table Validita(
	Promozione varchar(20),
    Articolo varchar(20),
    primary key(Promozione, Articolo),
    foreign key (Promozione) references Promozione(Nome),
    foreign key (Articolo) references Articolo(Nome)
);