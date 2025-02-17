# Trabalho-Final-MVC

## Descrição do Projeto

O projeto "MatchMusic" é uma aplicação web desenvolvida com Spring Boot e Thymeleaf, que permite gerenciar informações sobre músicas, artistas e gêneros musicais. A aplicação oferece funcionalidades para listar, adicionar, editar e excluir músicas, artistas e gêneros musicais. Além disso, a aplicação permite que os usuários façam login e cadastro.

## Funcionalidades

- Gerenciamento de músicas: listar, adicionar, editar e excluir músicas.
- Gerenciamento de artistas: listar, adicionar, editar e excluir artistas.
- Gerenciamento de gêneros musicais: listar, adicionar, editar e excluir gêneros musicais.
- Autenticação de usuários: login e cadastro de usuários.

## Pré-requisitos

- Java 17 ou superior
- Maven 3.6.0 ou superior
- MySQL 8.0 ou superior

## Configuração do Banco de Dados

1. Crie um banco de dados no MySQL com o nome `matchmusic`.
2. Execute o script SQL `bd_matchMusic.sql` localizado na raiz do projeto para criar as tabelas e inserir os dados iniciais.

## Configuração do Projeto

1. Clone o repositório para a sua máquina local:
   ```bash
   git clone https://github.com/pedroaalexandre/Trabalho-Final-MVC.git
   ```
2. Navegue até o diretório do projeto:
   ```bash
   cd Trabalho-Final-MVC
   ```
3. Configure as credenciais do banco de dados no arquivo `application.properties` localizado em `Trabalho final MVC/src/main/resources`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/matchmusic
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```

## Executando a Aplicação

1. Navegue até o diretório do projeto:
   ```bash
   cd Trabalho final MVC
   ```
2. Execute o comando Maven para compilar e executar a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Acesse a aplicação no seu navegador através do endereço:
   ```
   http://localhost:8080
   ```

## Endpoints Disponíveis

- `/home`: Página inicial da aplicação.
- `/artista`: Listagem de artistas.
- `/artista/novo`: Formulário para adicionar um novo artista.
- `/artista/editar/{codigo}`: Formulário para editar um artista existente.
- `/genero/listaGeneros`: Listagem de gêneros musicais.
- `/genero/criar`: Formulário para adicionar um novo gênero musical.
- `/genero/editar/{codigo}`: Formulário para editar um gênero musical existente.
- `/musica/listar`: Listagem de músicas.
- `/musica/novo`: Formulário para adicionar uma nova música.
- `/musica/editar/{codigo}`: Formulário para editar uma música existente.
- `/usuario/cadastro`: Formulário para cadastro de novos usuários.
- `/usuario/login`: Formulário para login de usuários.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Thymeleaf
- Maven
- MySQL
