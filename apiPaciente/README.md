# API de paciente

Este é um projeto de API desenvolvido em Spring Boot para gerenciamento de pacientes.

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

- [Java Development Kit (JDK) 17 ou superior](https://www.oracle.com/java/technologies/javase-downloads.html)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [MongoDB Community Server](https://www.mongodb.com/try/download/community)
- [Postman](https://www.postman.com/downloads/)

## Configuração do Ambiente

### MongoDB

1. Instale o MongoDB Community Server [aqui](https://www.mongodb.com/try/download/community).(Pressione select package em Community Community Server, em seguida pressione download)
2. Certifique-se que MongoDB está com URI: mongodb://localhost:27017

### IntelliJ IDEA

1. Instale o IntelliJ IDEA seguindo as instruções [aqui](https://www.jetbrains.com/idea/download/).
2. Abra o IntelliJ IDEA e importe o projeto.

### Postman

1. Baixe e instale o Postman através do [site oficial](https://www.postman.com/downloads/).
2. Abra o Postman e configure-o conforme necessário.

## Clone e Execução

1. Clone o projeto:

```bash
git clone https://github.com/Xalvitor/apiPaciente.git
```

2. Abra o projeto no IntelliJ IDEA.
3. Configure o MongoDB host e porta no arquivo `src/main/resources/application.properties`:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=pacientes-api
server.port=8082
```

4. Execute o aplicativo a partir do IntelliJ IDEA.

## Endpoints

Acesse os seguintes endpoints da API:

- `GET http://localhost:8082/pacientes`: Obtém a lista de todos os pacientes.
- `GET http://localhost:8082/pacientes/{id}`: Obtém um paciente específico com base no ID.
- `POST http://localhost:8082/pacientes`: Adiciona um novo paciente ao banco de dados. Utilize um JSON no corpo da requisição conforme o modelo da entidade `Paciente`.

  Exemplo de JSON:

  ```json
  { 
    "nome": "Antonio",
    "sobrenome": "Vitor",
    "cpf": "11784323004",
    "dataDeNascimento": "2000-12-12",
    "genero": "Masculino",
    "contatos": ["97974-4727"],
    "enderecos": [
        {
          "logradouro": "Rua Itamar Neiva Monteiro",
          "numero": 1,
          "bairro": "José Américo de Almeida",
          "cep": "58074000",
          "municipio": "João Pessoa",
          "estado": "PB"
        }
     ]
  }
  ```
- `PUT http://localhost:8082/pacientes/{id do paciente}`: Altera as informações do paciente com base no ID. Utilize um JSON no corpo da requisição conforme o modelo da entidade `Paciente`.

  Exemplo de JSON:
  ```json
  { 
    "nome": "Antonio",
    "sobrenome": "Vitor",
    "cpf": "11784323004",
    "dataDeNascimento": "2000-12-12",
    "genero": "Masculino",
    "contatos": ["98849-7848"],
    "enderecos": [
        {
          "logradouro": "Rua Itamar Neiva Monteiro",
          "numero": 1,
          "bairro": "José Américo de Almeida",
          "cep": "58074000",
          "municipio": "João Pessoa",
          "estado": "PB"
        }
     ]
  }

- `DELETE http://localhost:8082/pacientes/{id do paciente}`: Deleta o paciente com o id inserido do banco de dados
- `POST http://localhost:8082/pacientes/adicionar-pacientes`: Adiciona pacientes pre-definidos ao banco de dados a fim de testes

- ## Licença MIT

Este projeto é licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE.txt) para detalhes.
