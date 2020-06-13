# Code challenge - Account repository

### Description

#### Technologies used
The technologies used in the development of this project are:
Java 11, Springboot 2, Gradle.

I decided to use the in-memory H2 database in Postgres mode. 
From the application.properties file you can set up your own data source.

JUnit 5 and Mockito are used for testing.
The code coverage currently sits at 87%.
A new code coverage report can be generated using Jacoco by running
 ```
 ./gradlew test jacocoTestReport
```

#### How to run

```
./gradlew bootRun
```
or build the project with
```
./gradlew clean build
```
and then run the docker image
```
 docker build -t cc:latest --rm=true .
 docker run cc -p 8080:8080
```
The application should now be available on localhost:8080

#### How to use
There are 4 available endpoints:
```
POST /accounts
GET /accounts
DELETE /accounts/{uuid}
PUT /account
```

The POST endpoint expects a JSON as body in the following format:
```json
{
	"firstName":"John",
	"lastName":"Doe",
	"emailAddress":"jd@gmail.com",
	"dob": "11/11/1996"
}
```
Currently the only accepted date format is dd/mm/yyyy

A unique identifier is generated when a new account is created.

The GET endpoint returns a list of JSON objects, e.g. 
```json
[
    {
        "uuid": "73a00f51-9b48-4fb5-a007-22e9990d654d",
        "firstName": "John",
        "lastName": "Doe",
        "emailAddress": "jd@gmail.com",
        "dob": "1996-11-11T00:00:00.000+0000"
    }
]
```

The DELETE endpoint uses a path variable which should be the unique identifier of the object to be deleted.

The PUT endpoint expects a JSON in the following format:
```json
{
        "uuid": "73a00f51-9b48-4fb5-a007-22e9990d654d",
        "firstName": "Jane",
        "lastName": "Dean",
        "emailAddress": "jay@gmail.com",
        "dob": "10/09/1995"
    }
```
