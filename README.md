# transformers

A solution to a take-home technical test.
I used Spring Initializr to setup the webapp's skeleton.

The "core" of the webapp does not know anything about the "web" part of the webapp.
Furthermore, the "web" part translates domain objects into DTOs and relies on Spring to marshal them into JSON.

### Run

To build and run tests: mvnw install

To run and start listening on port 8080: java -jar target/transformers-0.0.1-SNAPSHOT.jar

### Documentation

http://localhost:8080/swagger-ui.html

http://localhost:8080/v2/api-docs
