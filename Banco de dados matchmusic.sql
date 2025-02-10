create database matchMusic;

use matchMusic;

create table usuario (
cod_usuario int auto_increment primary key,
desc_login varchar (40),
desc_senha varchar(40),
nome_Usuario varchar (255)
);

create table genero (
	cod_genero int auto_increment primary key,
    desc_genero varchar (40),
    ano_surgimento year,
    local_surgimento varchar (40)
);

create table artista (
cod_artista int not null primary key auto_increment,
nome_artista varchar (40),
idade_artista int,
cod_genero int,
foreign key (cod_genero) references genero(cod_genero)
);