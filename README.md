# employee-list-api
employee list backend app with Test Contaneirs, Unit Tests and Integration Test.

## Description of Project

RESTful API for a Employee list web application. 

## Functionalities

:bell: Full CRUD API with Spring boot and Mysql Database

:bell: Unit Tests with Junit, Mockito and BDD Pattern

:bell: Integrations Tests with Test Containers

:bell: OpenApi Swagger Documentation

## Steps and Requirements to build and run the Project

**1. Clone the application**
```bash
https://github.com/SergioRuyDev/employee-list-api.git
```

**2. Create Mysql database and execute**

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Run the app using maven**

```bash
mvn spring-boot:run or src/main/java/com.spring.employee-list-api/employee-list-api:run
```
The app will start running at <http://localhost:8080>
