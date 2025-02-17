-- Remover e recriar o banco de dados
DROP DATABASE IF EXISTS matchmusic;
CREATE DATABASE matchmusic;
USE matchmusic;

-- Tabela Usuario
CREATE TABLE usuario (
    desc_login VARCHAR(40) PRIMARY KEY,
    desc_senha VARCHAR(255),
    nome_usuario VARCHAR(255)
);

-- Tabela Genero
CREATE TABLE genero (
    cod_genero INT AUTO_INCREMENT PRIMARY KEY,
    desc_genero VARCHAR(40),
    ano_surgimento VARCHAR(7),
    local_surgimento VARCHAR(40)
);

-- Tabela Artista
CREATE TABLE artista (
    cod_artista INT AUTO_INCREMENT PRIMARY KEY,
    nome_artista VARCHAR(40),
    data_nascimento DATE,
    cod_genero INT,
    FOREIGN KEY (cod_genero) REFERENCES genero(cod_genero)
);

-- Tabela Musica
CREATE TABLE musica (
    cod_musica INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(40),
    ano_lancamento INT,
    duracao VARCHAR(40),
    imagem VARCHAR(255),
    cod_artista INT,
    FOREIGN KEY (cod_artista) REFERENCES artista(cod_artista)
);

-- Inserindo usuários
INSERT INTO usuario (desc_login, desc_senha, nome_usuario) VALUES 
('aluno', '$2a$10$x5LxuB7sf5Mx8wZyY111m.Hpp2IMqdoAUuH729l5By8eY25kwsK1S', 'Alice Smith');

-- Inserindo gêneros musicais
INSERT INTO genero (desc_genero, ano_surgimento, local_surgimento) VALUES 
('Rock', '1950', 'USA'),
('Pop', '1960', 'UK'),
('Jazz', '1920', 'USA');

-- Inserindo artistas e associando ao gênero correto
INSERT INTO artista (nome_artista, data_nascimento, cod_genero) VALUES 
('The Beatles', '1960-01-01', 2),  -- Pop
('Elvis Presley', '1935-01-08', 1), -- Rock
('Michael Jackson', '1958-08-29', 2), -- Pop
('Bruno Mars', '1985-10-08', 2), -- Pop
('Adele', '1988-05-05', 2), -- Pop
('Frank Sinatra', '1915-12-12', 3), -- Jazz
("Guns N' Roses", '1985-03-26', 1), -- Rock
('Lynyrd Skynyrd', '1964-01-01', 1); -- Rock

-- Inserindo músicas associadas corretamente aos artistas
INSERT INTO musica (titulo, ano_lancamento, duracao, imagem, cod_artista) VALUES 

-- The Beatles (Pop)
('Yesterday', 1965, '2:05', 'yesterday.jpg', 1),
('Come Together', 1969, '4:20', 'come_together.jpg', 1),

-- Elvis Presley (Rock)
('Jailhouse Rock', 1957, '2:37', 'jailhouse_rock.jpg', 2),
('Can’t Help Falling in Love', 1961, '3:00', 'cant_help_falling_in_love.jpg', 2),

-- Michael Jackson (Pop)
('Billie Jean', 1983, '4:54', 'billie_jean.jpg', 3),
('Thriller', 1982, '5:57', 'thriller.jpg', 3),

-- Bruno Mars (Pop)
('Uptown Funk', 2014, '4:30', 'uptown_funk.jpg', 4),

-- Adele (Pop)
('Rolling in the Deep', 2010, '3:48', 'rolling_in_the_deep.jpg', 5),

-- Frank Sinatra (Jazz)
('My Way', 1969, '4:35', 'my_way.jpg', 6),
('Fly Me to the Moon', 1964, '2:27', 'fly_me_to_the_moon.jpg', 6),
('Strangers in the Night', 1966, '2:35', 'strangers_in_the_night.jpg', 6),

-- Inserindo músicas do Guns N' Roses
("Sweet Child O' Mine", 1987, '5:56', 'sweet_child_o_mine.jpg', 7),
('November Rain', 1991, '8:57', 'november_rain.jpg', 7),
('Welcome to the Jungle', 1987, '4:31', 'welcome_to_the_jungle.jpg', 7),

-- Inserindo músicas do Lynyrd Skynyrd
('Sweet Home Alabama', 1974, '4:43', 'sweet_home_alabama.jpg', 8),
('Free Bird', 1973, '9:08', 'free_bird.jpg', 8),
('Simple Man', 1973, '5:57', 'simple_man.jpg', 8);