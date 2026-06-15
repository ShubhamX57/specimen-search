# specimen-search

A small **Java / Spring Boot** REST service that indexes scientific specimen records and exposes search, filter, and retrieval endpoints over a clean REST API.

Built to mirror — in Java — the FastAPI data-discovery services I have built in Python: index a dataset, serve it over a documented REST API, validate inputs, and cover it with automated tests.

## What it does

| Method & path | Description |
|---|---|
| `GET /api/specimens` | List all records (sorted by id) |
| `GET /api/specimens/{id}` | Retrieve one record; `404` if not found |
| `GET /api/specimens/search?q=&taxon=` | Free-text search + optional taxon facet |
| `GET /api/health` | Service status and indexed-record count |

The index is in-memory; the access pattern (index on startup, retrieve by id, scan for matches) mirrors a Lucene/Solr-backed discovery service at small scale.

## Tech

- Java 17 (builds and runs on 21), Spring Boot 3.5 (spring-boot-starter-web, validation)
- JUnit 5 + MockMvc integration tests
- Maven (wrapper included)

## Run it

    ./mvnw test            # run the 5-test suite
    ./mvnw spring-boot:run # start on http://localhost:8080

Then try:

    curl localhost:8080/api/health
    curl localhost:8080/api/specimens
    curl "localhost:8080/api/specimens/search?q=imaging"
    curl "localhost:8080/api/specimens/search?taxon=Mammalia"
    curl localhost:8080/api/specimens/SPC-002
