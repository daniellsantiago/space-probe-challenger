# Space Probe Challenger
## Description
Imagine that a developer received a task from a member of the product team. The product team member wanted to be able to control probes on other planets through commands. To explain the product's operation, the following example was written on a piece of paper:

### Explanation of the need:
```
Planet area size: 5x5

Landing position of probe 1: x=1, y=2 pointing North
Command sequence: LMLMLMLMM
Final position of probe: x=1 y=3 pointing North

Landing position of probe 2: x=3, y=3 pointing East
Command sequence: MMRMMRMRRML
Final position of probe: x=5 y=1 pointing North
```
### Details on the functionality above:
The command sequence is a set of instructions sent from Earth to the probe, where:
- `M` -> Move forward in the current direction by 1 position.
- `L` -> Turn the probe to the left (90 degrees).
- `R` -> Turn the probe to the right (90 degrees).
The orientation of the probe within the Cartesian plane uses a compass rose as reference.

## How to run

Clone the project from Github :

```sh
git clone git@github.com:daniellsantiago/space-probe-challenger.git
cd spaceprobe
```

### Docker
If you don't have Docker installed, you'll need to set a local postgres database. Otherwise, use docker-compose to run It locally.

``` shell
 $ docker-compose up
 ```

### Spring Boot
``` shell
 $ ./mvnw spring-boot:run
 ```
### Swagger
Once the app is running, you can see the available endpoints on ``` localhost:8080/swagger-ui ```

### Good to improve

- [ ] Add unit tests to controllers;
- [ ] Move Persistence layer code from Domain to Infrastructure;
- [X] Add Swagger Support;
- [ ] Improve API error handling;
- [ ] Add CRUD endpoints.