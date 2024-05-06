 
<h1 align="center">
  To do List 📝
</h1>

<p align="center">
 <img src="https://img.shields.io/static/v1?label=GitHub&message=@juravellar&color=66cdaa&labelColor=006400" alt="@juravellar" />
 <img src="https://img.shields.io/static/v1?label=Tipo&message=Desafio&color=66cdaa&labelColor=006400" alt="Desafio" />
 <img src="https://img.shields.io/static/v1?label=Status&message=Em%20desenvolvimento&color=yellow&labelColor=006400" alt="Status">
</p>

# Sobre o projeto

Este projeto é uma API RESTful que permite a você gerenciar suas tarefas de forma eficiente. Ele implementa as operações CRUD (Criar, Ler, Atualizar e Deletar) para tarefas, permitindo que você adicione, visualize, edite e exclua tarefas conforme necessário.

# Tecnologias utilizadas

- **Backend:**
    - Java 17
    - Spring Boot
    - Spring Webflux (para reatividade)
    - Spring Data R2DBC (para acesso ao banco de dados reativo)
    - SpringDoc OpenAPI 3 (para documentação da API)
    - Slugify (para geração de slugs)
- **Banco de dados:** (escolha um)
    - PostgreSQL 
    - MySQL
    - H2 (para desenvolvimento)

# Funcionalidades

- **CRUD de tarefas:** Adicione, visualize, edite e exclua tarefas.
- **Filtros dinâmicos:** Pesquise tarefas com base em diferentes critérios.
- **API reativa:** Desfrute de melhor desempenho e escalabilidade.
- **DTOs:** Mantenha a separação de responsabilidades entre camadas.
- **Injeção de dependências:** Facilite a testabilidade e a manutenção.
- **Swagger UI:** Documentação da API interativa e fácil de usar.
- **Geração de slugs:** URLs amigáveis para suas tarefas.
- **Auditoria:** Acompanhe as alterações feitas nas tarefas.

### Como Executar

## Pré-requisitos

- Java Development Kit (JDK) 17
- Maven
- Docker (opcional)

#### Localmente
- Clonar repositório git
  ```bash
  git clone https://github.com/seu-usuario/to-do-list.git

 - Navegue até o diretório do projeto:
```
cd to-do-list
```
- Construir o projeto:
```
./mvnw clean package
```
Compile e execute o projeto:
```
./mvnw spring-boot:run
```
- Executar:
```
java -jar to-do-list/target/to-do-list-0.0.1-SNAPSHOT.jar
```

Acesse a API e o Swagger UI:
API: [localhost:8080](http://localhost:8080).
Swagger UI: [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

#### Executando com Docker

- Clonar repositório git
- Construir o projeto:
```
./mvnw clean package
```
- Construir a imagem:
```
./mvnw spring-boot:build-image
```
- Executar o container:
```
docker run --name place-service -p 8080:8080  -d place-service:0.0.1-SNAPSHOT
```

####Acesse a API e o Swagger UI usando os mesmos endereços mencionados acima.

###Próximos passos (opcional)
-Implementar autenticação e autorização.
-Adicionar testes unitários e de integração.
-Criar uma interface de usuário (front-end).
-Implantar em um ambiente de produção.
-Adicionar um sistema de lembretes para as tarefas.
-Permitir a organização de tarefas em listas ou categorias.
-Implementar a colaboração em tarefas, permitindo que vários usuários trabalhem juntos.

###Contribuição
Sinta-se à vontade para contribuir com este projeto! Fique à vontade para abrir issues e pull requests.
**Observações:**

- Substitua `seu-usuario` pelo seu nome de usuário do GitHub.
- Escolha o banco de dados que deseja usar e configure-o adequadamente. 
- Adapte a seção "Próximos passos" de acordo com suas necessidades e objetivos. 

**Espero que este READ.me aprimorado seja útil para o seu projeto!**
