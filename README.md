# spring-boot-jsonb-postgresql

Sample Spring Boot application that maps PostgreSQL `jsonb` columns to Java types with Spring Data JPA.

## Tech Stack

| Component         | Version |
|-------------------|---------|
| Java (JDK)        | 25      |
| Spring Boot       | 4.1.1   |
| Hibernate ORM     | 7.x (managed by Spring Boot) |
| PostgreSQL JDBC   | 42.7.14 |
| PostgreSQL server | 17 (tested) |
| Maven             | 3.9.11 (via `./mvnw`) |

## How JSONB Mapping Works

The project uses Hibernate 7's built-in JSON support, so it needs no custom `UserType`, dialect or third-party
library. A field is mapped to `jsonb` with:

```java
@JdbcTypeCode(SqlTypes.JSON)
@Column(columnDefinition = "jsonb")
private Map<String, Object> info;
```

Entities:

- `Account`: `info` is a `Map<String, Object>` stored as `jsonb`.
- `Parent` (table `parents`): `children` (`List<Child>`) and `bio` (`Bio`) stored as `jsonb`.

`AccountRepository#findByName` shows a native query against the JSON content:

```sql
select * from account where info ->> 'name' = :name
```

## Prerequisites

- JDK 25 (`java -version` should report 25)
- PostgreSQL running on `localhost:5432` with user `postgres` / password `postgres`
  (see `src/main/resources/application.yml`)

Start PostgreSQL quickly with Docker:

```shell
docker run -d --name pg-jsonb -p 5432:5432 \
  -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres \
  postgres:17
```

> `spring.jpa.hibernate.ddl-auto` is set to `create`, so the tables are dropped and recreated on every start.

## Build, Test & Run

```shell
# build and run the tests (needs PostgreSQL running)
./mvnw clean package

# run the application
./mvnw spring-boot:run
# or
java -jar target/spring-boot-jsonb-postgresql-0.0.1-SNAPSHOT.jar
```

Tests:

- `AccountRepositoryTest`: saves an `Account` with a JSON map and finds it by `info ->> 'name'`.
- `ParentRepositoryTest`: saves a `Parent` and reads back the `children` and `bio` JSONB columns.

Example of the stored data:

```text
 id |  name  |            bio            |                 children
----+--------+---------------------------+------------------------------------------
  1 | Minato | {"text": "Fourth Hokage"} | [{"name": "Naruto"}, {"name": "Boruto"}]
```

## Notes on the JDK 25 Upgrade

- `java.version` in `pom.xml` is `25`; CI (`.github/workflows/maven.yml`) builds with Temurin JDK 25 and a PostgreSQL 17
  service container.
- Since JDK 23, `javac` no longer runs annotation processors found on the classpath, so Lombok and
  `spring-boot-configuration-processor` are declared in `maven-compiler-plugin`'s `annotationProcessorPaths`.
- The legacy Hibernate 5 code (`javax.persistence`, `@TypeDef`, custom `JsonbType`, `CustomPostgreSqlDialect` and
  `hibernate-types-52`) was replaced with Jakarta Persistence and Hibernate 7's native `SqlTypes.JSON` mapping.
