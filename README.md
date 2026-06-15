# specimen-search

A small **Java / Spring Boot** REST service that indexes scientific specimen
records and exposes search, filter, and retrieval endpoints over a clean REST
API.

I built this to mirror — in Java — the kind of **FastAPI data-discovery
services** I have built in Python: index a dataset, serve it over a documented
REST API, validate inputs, and cover it with automated tests. It exists to
ground my Java/Spring Boot learning in a working, tested artifact rather than a
tutorial.

## What it does

| Method & path                          | Description                                  |
|----------------------------------------|----------------------------------------------|
| `GET /api/specimens`                   | List all records (sorted by id)              |
| `GET /api/specimens/{id}`              | Retrieve one record; `404` if not found      |
| `GET /api/specimens/search?q=&taxon=`  | Free-text search + optional taxon facet      |
| `GET /api/health`                      | Service status and indexed-record count      |

The index is in-memory for simplicity; the access pattern (index on startup,
retrieve by id, scan for matches) mirrors the shape of a Lucene/Solr-backed
discovery service at small scale.

## Tech

- Java 17, Spring Boot 3.3 (`spring-boot-starter-web`, `-validation`)
- JUnit 5 + MockMvc integration tests
- Maven build

## Run it

```bash
# from the project root
./mvnw test            # run the 5-test suite (should pass)
./mvnw spring-boot:run # start on http://localhost:8080
```

Then try:

```bash
curl localhost:8080/api/health
curl localhost:8080/api/specimens
curl "localhost:8080/api/specimens/search?q=imaging"
curl "localhost:8080/api/specimens/search?taxon=Mammalia"
curl localhost:8080/api/specimens/SPC-002
```

## If you don't have the Maven wrapper

This bundle ships source + `pom.xml` but not the `./mvnw` wrapper scripts.
Easiest fix: generate a fresh skeleton from https://start.spring.io
(Project: Maven, Language: Java, Spring Boot 3.3.x, Java 17, dependencies:
*Spring Web*, *Validation*), then copy the `src/` tree and merge the
dependencies from this `pom.xml`. The wrapper comes included from Initializr.

Alternatively, with a local Maven install, just run `mvn test` /
`mvn spring-boot:run`.

## Push to GitHub

```bash
git init
git add .
git commit -m "Spring Boot specimen-search: REST data-discovery service with tests"
git branch -M main
git remote add origin https://github.com/ShubhamX57/specimen-search.git
git push -u origin main
```
