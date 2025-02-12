drop database matchmusic;
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
    ano_surgimento varchar (7),
    local_surgimento varchar (40)
);

create table artista (
    cod_artista int not null primary key auto_increment,
    nome_artista varchar (40),
    idade_artista int,
    cod_genero int,
    foreign key (cod_genero) references genero(cod_genero)
);

-- Tabela Musica
CREATE TABLE musica (
    cod_musica INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(40),
    ano_lancamento int,
    duracao VARCHAR(40),
    -- cod_genero INT,
    -- FOREIGN KEY (cod_genero) REFERENCES genero(cod_genero),
    cod_artista INT,
    FOREIGN KEY (cod_artista) REFERENCES artista(cod_artista)
);

INSERT INTO usuario (desc_login, desc_senha, nome_Usuario)
VALUES 
('user1', 'password1', 'Alice Smith'),
('user2', 'password2', 'Bob Johnson'),
('user3', 'password3', 'Charlie Davis');

INSERT INTO genero (desc_genero, ano_surgimento, local_surgimento)
VALUES 
('Rock', '1950', 'USA'),
('Pop', '1960', 'UK'),
('Jazz', '1920', 'USA');

INSERT INTO artista (nome_artista, idade_artista, cod_genero)
VALUES 
('The Beatles', 60, 2),
('Miles Davis', 75, 3),
('Elvis Presley', 42, 1);

INSERT INTO musica (titulo, ano_lancamento, duracao, cod_artista)
VALUES 
('Hey Jude', 1968, '7:11', 1),
('So What', 1959, '9:22', 2),
('Hound Dog', 1956, '2:15', 3);

select * from musica;