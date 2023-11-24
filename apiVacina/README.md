# API de Vacinas

Este é um projeto de API desenvolvido em Spring Boot para gerenciamento de informações sobre vacinas.

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

- [Java Development Kit (JDK) 17 ou superior](https://www.oracle.com/java/technologies/javase-downloads.html)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [MongoDB Community Server](https://www.mongodb.com/try/download/community)
- [Postman](https://www.postman.com/downloads/)

## Configuração do Ambiente

### MongoDB

1. Instale o MongoDB Community Server [aqui](https://www.mongodb.com/try/download/community).(Pressione select package em Community Community Server, em seguida pressione download)
2. Abra o MongoDB Compass e crie um banco de dados chamado `vacina-api`.

### IntelliJ IDEA

1. Instale o IntelliJ IDEA seguindo as instruções [aqui](https://www.jetbrains.com/idea/download/).
2. Abra o IntelliJ IDEA e importe o projeto.

### Postman

1. Baixe e instale o Postman através do [site oficial](https://www.postman.com/downloads/).
2. Abra o Postman e configure-o conforme necessário.

## Clone e Execução

1. Clone o projeto:

```bash
git clone https://github.com/Noobplayerv12/apiVacina.git
```

2. Abra o projeto no IntelliJ IDEA.
3. Configure o MongoDB host e porta no arquivo `src/main/resources/application.properties`:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=vacina-api
server.port=8081
```

4. Execute o aplicativo a partir do IntelliJ IDEA.

## Endpoints

Acesse os seguintes endpoints da API:

- `GET http://localhost:8081/vacina`: Obtém a lista de todas as vacinas.
- `GET http://localhost:8081/vacina/{id}`: Obtém uma vacina específica com base no ID.
- `POST http://localhost:8081/vacina`: Adiciona uma nova vacina ao banco de dados. Utilize um JSON no corpo da requisição conforme o modelo da entidade `Vacina`.

  Exemplo de JSON:
  ```json
  {
    "fabricante": "CORONAVAC",
    "lote": "Lote123",
    "dataDeValidade": "2023-12-31",
    "numeroDeDoses": 2,
    "intervaloDeDoses": 21
  }
  ```

- `PUT http://localhost:8081/vacina/{id}`: Atualiza as informações de uma vacina com base no ID. Utilize um JSON no corpo da requisição conforme o modelo da entidade `Vacina`.

  Exemplo de JSON:
  ```json
  {
    "fabricante": "PFIZER",
    "lote": "Lote456",
    "dataDeValidade": "2024-06-30",
    "numeroDeDoses": 1
  }
  ```

- `DELETE http://localhost:8081/vacina/{id}`: Deleta uma vacina do banco de dados com base no ID.
- `POST http://localhost:8081/vacina/adicionar-vacinas`: Adiciona vacinas predefinidas ao banco de dados para fins de teste.

## Licença MIT

Este projeto é licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

---
