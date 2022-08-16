# Getting Started

Clone the project from Github :

```sh
git clone git@github.com:daniellsantiago/space-probe-challenger.git
cd spaceprobe
```

## Docker
If you don't have Docker installed, you'll need to set a local postgres database. Otherwise, use docker-compose to run It locally.

``` shell
 $ docker-compose up
 ```

## Spring Boot
``` shell
 $ ./mvnw spring-boot:run
 ```

## Good to improve

- [ ] Add unit tests to controllers;
- [ ] Move Persistence layer code from Domain to Infrastructure;
- [ ] Add Swagger Support;
- [ ] Improve API error handling;
- [ ] Add CRUD endpoints.