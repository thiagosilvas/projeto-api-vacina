# apiPaciente
 Para esse projeto será necessário criar 3 APIs, cada uma rodando em portas separadas e com seu banco de dados isolado. 

A **API 1 deve gerenciar Vacinas, sendo que uma vacina deve ter:**

- Fabricante
- Lote
- Data de Validade
- Número de doses
- Intervalo mínimo entre doses em dias

A **API 2 deve gerenciar Pacientes, sendo que um paciente deve ter:**

- Nome
- Sobrenome
- Data de nascimento
- Sexo
- Contatos (Celular, WhatsApp e/ou E-mail)
- Endereço (Logradouro, Número, Bairro, Município, CEP e Estado)

A **API 3 deve gerenciar Registro de vacinação, sendo que um Registro de vacinação deve ter:**

- Data de vacinação
- Identificação do paciente
- Identificação da vacina
- Identificação da dose
- Identificação do profissional de saúde (Apenas Nome e CPF)

## Requisitos funcionais e não funcionais:

- Deve ser possível listar doses que um paciente já tomou, com as respectivas datas

```html
# Ex
http://localhost:8080/pacientes/5fd5f26845a3b1d2d0e8a550/vacinas
```

- Deve ser possível registrar um vacinação
- Deve ser possível listar a quantidade total de vacinas aplicadas (No mesmo endpoint, se for informado o estado, deve mostrar o total de vacinas aplicadas naquele estado)

```html
# Ex 1
http://localhost:8081/pacientes/vacinas

# Ex 2
http://localhost:8081/pacientes/vacinas?estado=ba
```

- Deve ser possível listar pacientes com doses atrasadas (No mesmo endpoint, se for informado o estado, deve mostrar o total de pacientes com doses atrasadas naquele estado)

```html
# Ex 1
http://localhost:8081/pacientes/vacinas/atrasadas

# Ex 2
http://localhost:8081/pacientes/vacinas/atrasadas?estado=ba
```

```json
// Exemplo de resposta
[
  {
    "paciente": {
			"nome": "Maria Aparecida dos Santos",
			"idade": 58,
			"bairro": "Portão",
			"municipio": "Lauro de Freitas"
			"estado": "ba"
		},
		"vacina": {
			"fabricante": "pfizer",
			"vacina": "Comirnaty",
			"total_de_doses": 3,
			"intervalo_entre_doses": 15
		},
    "doses": ["2023-06-01", "2023-06-23"]
  }
]
```

- Deve ser possível listar o total de vacinas aplicadas de cada fabricante (No mesmo endpoint, se for informado o estado, deve mostrar o total de vacinas aplicadas daquele fabricante naquele estado)

```html
# Ex 1
http://localhost:8081/pacientes/vacinas/?fabricantes=pfizer

# Ex 2
http://localhost:8081/pacientes/vacinas/?fabricantes=pfizer&estado=ba
```

```json
// Exemplo de resposta
[
  {
		"vacina": {
		"fabricante": "pfizer",
		"vacina": "Comirnaty",
		"doses_aplicadas": 954786,
	},
  {
		"vacina": {
		"fabricante": "Sinovac",
		"vacina": "CoronaVac",
		"doses_aplicadas": 546987,
	}
]
```

- A comunicação entre as APIs devem ocorrer via HTTP
- Uma aplicação não deve ler/gravar diretamente no banco de dados de outra aplicação
- Não deve ser possível cadastrar uma vacina sem informar todos os dados da mesma (Com exceção do “Intervalo mínimo entre doses”, visto que essa informação só é obrigatória para vacinas que não sejam de dose única).
- Não deve ser possível vacinar um paciente num intervalo menor do que o especificado pelo fabricante
- Não deve ser possível vacinar um paciente mais vezes que o especificado pelo fabricante
- Não deve ser possível vacinar um paciente com doses de fabricantes/vacinas diferentes
- Deve fazer uso inteligente do cache visando otimizar a aplicação
- **Deve haver testes automatizados de todos os endpoints**
- Apenas o último registro de vacinação de um paciente pode ser editado / excluído, ou seja, se ele tomou duas vacinas, uma dia 10 e outra dia 20, somente o registro do dia 20 pode ser editado ou excluído. Uma vez que esse registro foi excluído, o do dia 10 passa a ser o último, logo, ele já pode ser excluído.
- **Todas as operações de escrita devem gerar LOG**.
- Deve haver validações. Ex.:

```bash
# status 400
{
	"mensagem": "O nome do paciente não foi informado!"
}
```

- Erros no servidor devem ser informado com status code 500 e uma mensagem customizada. Ex.:

```bash
# status 500
{
	"mensagem": "Ocorreu um erro na aplicação. Nossa equipe de TI já foi notificada e em 
   breve nossos serviços estarão reestabelecidos. Para maiores informações entre em 
   contato pelo nosso WhatsApp 71 99999-9999. Lamentamos o ocorrido!"
}
```

- As mensagem referente a negócio devem seguir o seguinte padrão:

```bash
# status 400
{
	"mensagem": "O CPF informado já encontra-se cadastrado em nossa base de dados!"
}
```

```bash
# status 422
{
	"mensagem": "A primeira dose aplicada no paciente 'JOSÉ SANTOS MARTINS' foi: 
  'CORONAVAC' do laboratório 'SINOVAC'. Todas as doses devem ser aplicadas com 
  o mesmo medicamento!"
}
```

```bash
# status 422
{
	"mensagem": "O paciente 'JOSÉ SANTOS MARTINS' recebeu uma dose de 'CORONAVAC' no
  dia 30/08/2023. A próxima dose deverá ser aplicada a partir do dia 30/09/2023!"
}
```

```bash
# status 422
{
	"mensagem": "Não foi possível registrar sua solicitação pois o paciente 
  'JOSÉ SANTOS MARTINS' recebeu uma dose ÚNICA de 'ASTRAZENECA' no dia 30/08/2023!"
}
```

```bash
# status 422
{
	"mensagem": "Não foi possível registrar sua solicitação pois o paciente 
  'JOSÉ SANTOS MARTINS' já recebeu todas as vacinas necessárias de seu tratamento!"
}
```

### Importante!

1. A atividade deve ser enviada por completo para o e-mail: [paulo.r.santos@kroton.com.br](mailto:paulo.r.santos@kroton.com.br) até o dia **18 de novembro de 2023**.
2. Deve ser enviado link para os repositórios públicos da aplicação
3. **Ao enviar a atividade, a mesma deve conter um endpoint para alimentar o banco de dados com dados fakes**.
4. Deve haver um **README** contendo todos os dados necessários para executar o projeto e explicando como instalar, executar  e testar a aplicação
5. Para essa atividade é possível utilizar qualquer linguagem de programação / framework / SGBD, **porém se optar por utilizar algo diferente de Java / Spring / MongoDB, será necessário enviar no projeto o Dockerfile / Docker-compose para subir o ambiente.**
6. Não será aceito a construção do projeto com ferramentas de NO-CODE ou LOW-CODE
7. Não é necessário fazer paginação dos dados
8. Não é necessário implementar autenticação / autorização
9. **Todos os membros da equipe devem ser capazes de responder os questionamentos do professor sobre o projeto. Isso impactará na avaliação do projeto**.

# Tarefas opcionais

### 1. Construir o BDD de todos os Endpoints e possíveis cenários, seguindo o modelo abaixo:

**Feature: Cadastro de Vacinação**

**Cenário:** Cadastro de vacinação dentro do limite de doses permitido pelo fabricante

```markdown
Dado que a pessoa "João" já está cadastrada no sistema
E o fabricante da vacina "VacinaXYZ" permite um máximo de 3 doses
E "João" já tomou 2 doses da "VacinaXYZ"
Quando o usuário inicia o processo de cadastro de vacinação
E preenche o nome da pessoa como "João"
E preenche o nome da vacina como "VacinaXYZ"
E preenche a data da vacinação como "2023-10-02"
E preenche o lote da vacina como "Lote123"
Então o sistema deve permitir o cadastro da vacinação com sucesso
E deve exibir uma mensagem de confirmação
```

**Cenário:** Tentativa de cadastro de vacinação excedendo o limite de doses permitido pelo fabricante

```markdown
Dado que a pessoa "Maria" já está cadastrada no sistema
E o fabricante da vacina "VacinaABC" permite um máximo de 2 doses
E "Maria" já tomou 2 doses da "VacinaABC"
Quando o usuário inicia o processo de cadastro de vacinação
E preenche o nome da pessoa como "Maria"
E preenche o nome da vacina como "VacinaABC"
E preenche a data da vacinação como "2023-10-03"
E preenche o lote da vacina como "Lote456"
Então o sistema deve exibir uma mensagem de erro informando que "Maria" atingiu o limite de doses permitido pelo fabricante
E não deve permitir o cadastro da vacinação
```

### 2. Construir um ambiente Docker que permita subir, de maneira simplificada, as 3 aplicações utilizando: Nginx, Servlet Container, MongoDB, Redis (caso sua equipe use), etc… e que permita a comunicação entre todas as aplicações, com volumes persistentes
