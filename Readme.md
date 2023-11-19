

# Projeto Docker de uma API de Registro d Vacinação 

O objetivo principal é criar um ambiente isolado e padronizado para a execução da API de registro de vacinação, usando Docker como plataforma de contêineres, Java Spring Boot, MongoDB como banco de dados NoSQL e Nginx como API Gateway 

### Arquitetura 

<p align="center">
    <img src="https://i.ibb.co/GsYDrHS/Arquitetura.png" height="300px" width="">
</p>

### Pré-requisitos 

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

- [Docker](https://www.docker.com/get-started/)
- [Docker-compose](https://docs.docker.com/compose/install/)
- [Postman](https://www.postman.com/downloads/)


## Como iniciar a aplicação

1. Clone o Repositório:
Abra um terminal e execute o seguinte comando para clonar o repositório do GitHub:

```bash
git clone https://github.com/thiagosilvas/projeto-api-vacina
```
2. Navegue até o Diretório do Projeto:
Entre no diretório do projeto usando o comando:

```bash
cd projeto-api-vacina
```
3. Execute o Comando Docker Compose:
Utilize o comando docker-compose up --build para construir as imagens e iniciar os contêineres definidos no arquivo docker-compose.yml:

```bash
docker-compose up --build
```
O parâmetro --build garante que as imagens sejam reconstruídas, se necessário.

4. Aguarde a Inicialização:
Aguarde até que o Docker Compose conclua a construção e inicialização dos contêineres. Você verá os logs no terminal indicando o progresso.

5. Acesse a API:
Após a conclusão, a API deve estar disponível. Importe o arquivo Postamna-api.json incluindo dentro da pasta raix do projeto para dentro do Postman.


