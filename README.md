# employee-list-api
employee list backend app

## Description of Project

RESTful API for a Employee list web application. 

## Functionalities

:bell: Full CRUD API

## Steps and Requirements to build and run the Project

**1. Clone the application**
```bash
git clone https://github.com/SergioRuy/TavernOfGuilds.git
```

**2. Create Mysql database and execute the migration**
```bash
create the new directory database: src/main/resources/db/migrations
``` 
- The API use Flyway DB Migrations, for execute your sql file, you should follow the convention below.

<p align"center">
  <img src="https://snipboard.io/2Kwnpv.jpg"/>
</p>

- **Prefix:** V for versioned, U for undo and R for repeatable migrations.
- **Version:** Version with dots or underscores separate as many parts as you like (Not for repeatable migrations).
- **Separator:** __ (two underscores).
- **Description:** Underscores or spaces separate the words.
- **Suffix:** .sql
- The FlywayDB is automatic migration, on your IDE, you just need to go to the main class, run as Java Application
and the migration will be done.

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Run the app using maven**

```bash
mvn spring-boot:run or src/main/java/com.spring.TavernOfGuilds/TavernOfGuildsApplication:run
```
The app will start running at <http://localhost:8080>
