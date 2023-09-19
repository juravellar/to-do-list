 
<h1 align="center">
  To do List
</h1>

<p align="center">
 <img src="https://img.shields.io/static/v1?label=GitHub&message=@juravellar&color=66cdaa&labelColor=006400" alt="@juravellar" />
 <img src="https://img.shields.io/static/v1?label=Tipo&message=Desafio&color=66cdaa&labelColor=006400" alt="Desafio" />
</p>

# To do List
Esse projeto auxilia na [CRUD](https://developer.mozilla.org/pt-BR/docs/Glossary/CRUD)  de atividades; a estrutura foi gerada através da utilização do Maven e do JDK17.

# Começando

 Para executar o projeto, será necessário instalar os seguintes programas:
 
-[Maven 4.0.0: Necessário para realizar o build do projeto Java](https://maven.apache.org/index.html)

-[JDK 17: Necessário para executar o projeto Java](https://www.oracle.com/java/technologies/downloads/#java17)

-[Eclipse: Para desenvolvimento do projeto](https://www.eclipse.org/downloads/packages/release/oxygen/3a/eclipse-ide-java-ee-developers)

### Tecnologias usadas

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Webflux](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [Spring Data + R2DBC](https://docs.spring.io/spring-framework/reference/data-access/r2dbc.html)
- [SpringDoc OpenAPI 3](https://springdoc.org/v2/#spring-webflux-support)
- [Slugify](https://github.com/slugify/slugify)

### Práticas adotadas

- SOLID
- Testes automatizados
- Consultas com filtros dinâmicos usando o Query By Example
- API reativa na web e na camada de banco
- Uso de DTOs para a API
- Injeção de Dependências
- Geração automática do Swagger com a OpenAPI 3
- Geração de slugs automática com o Slugify
- Auditoria sobre criação e atualização da entidade

### Como Executar

#### Localmente
- Clonar repositório git
- Construir o projeto:
```
./mvnw clean package
```
- Executar:
```
java -jar to-do-list/target/to-do-list-0.0.1-SNAPSHOT.jar
```

A API poderá ser acessada em [localhost:8080](http://localhost:8080).
O Swagger poderá ser visualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

#### Usando Docker

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

A API poderá ser acessada em [localhost:8080](http://localhost:8080).
O Swagger poderá ser visualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
=======
# to-do-list
API criada com STS que gerencia tarefas
>>>>>>> 906d0db48916b0b69a7d70001384152a51612df2
