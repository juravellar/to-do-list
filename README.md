# To-do List API 📝

API REST para gerenciar tarefas, construída com **Spring Boot** seguindo os princípios da **Clean Architecture**: as regras de negócio ficam isoladas em casos de uso e não dependem do framework nem do banco de dados.

## Funcionalidades

- Criar tarefas, com opção de marcá-las como prioritárias
- Editar nome e descrição
- Marcar uma tarefa como concluída
- Buscar por id, por nome ou listar as tarefas prioritárias
- Registro automático das datas de criação e de atualização

## Tecnologias

- **Java 17** e **Spring Boot** (Spring Web e Spring Data JPA)
- **MySQL**
- **Lombok**
- **JUnit** e **Mockito** para os testes
- **Maven**

## Arquitetura

```
com.avellar.todolist
├── domain/entity          # TaskPort: o modelo de tarefa, sem dependência de framework
├── application
│   ├── gateways           # TaskGateway: interface que os casos de uso usam para persistir
│   └── usecases           # CreateTask, EditTask, CompleteTask e ListTask (interactors)
├── infrastructure
│   ├── controller         # TaskController e DTOs de entrada e saída
│   ├── gateways           # TaskRepositoryGateway: implementação do gateway com JPA
│   └── persistence        # entidade JPA Task e TaskRepository
└── main                   # TaskConfig: liga os casos de uso às implementações
```

A camada `application` define o que o sistema faz e a `infrastructure` define como (HTTP, JPA, MySQL). Trocar o banco ou o framework web não exige mudar os casos de uso.

## Endpoints

| Método | Rota | O que faz |
| --- | --- | --- |
| POST | `/tasks?isPrioritized=true` | Cria uma tarefa (o parâmetro é opcional) |
| GET | `/tasks/{id}` | Busca uma tarefa pelo id |
| GET | `/tasks/find?name=` | Busca tarefas pelo nome |
| GET | `/tasks/priority` | Lista as tarefas prioritárias |
| PATCH | `/tasks/{id}` | Edita uma tarefa |
| PATCH | `/tasks/complete/{taskId}` | Marca uma tarefa como concluída |

Exemplo de criação:

```bash
curl -X POST "http://localhost:8080/tasks?isPrioritized=true" \
  -H "Content-Type: application/json" \
  -d '{"name": "Estudar Spring", "description": "Revisar Clean Architecture"}'
```

## Como rodar

### Requisitos

- JDK 17
- MySQL 8

### Passos

```bash
git clone https://github.com/juravellar/to-do-list.git
cd to-do-list
```

Crie um banco chamado `task` no MySQL.

As credenciais do banco ficam num arquivo `.env`, que não vai para o repositório. Copie o modelo e preencha com os seus dados:

```bash
cp .env.example .env
```

```properties
# .env
DB_URL=jdbc:mysql://localhost:3306/task
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
```

O `application.properties` lê esse arquivo com `spring.config.import=optional:file:.env[.properties]` e usa os valores em `${DB_URL}`, `${DB_USERNAME}` e `${DB_PASSWORD}`. Se preferir, você também pode definir essas três variáveis direto no ambiente, sem o arquivo.

Depois, rode a partir da raiz do projeto:

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

> No IntelliJ, confira se o "Working directory" da configuração de execução é a pasta do projeto, para o `.env` ser encontrado.

## Testes

```bash
./mvnw test
```

Há testes do controller e do gateway de persistência.

## Próximos passos

- [ ] Documentação interativa com SpringDoc OpenAPI (Swagger)
- [ ] Testes dos casos de uso
- [ ] Docker Compose com a API e o MySQL
- [ ] CI no GitHub Actions
