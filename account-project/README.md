# Account Project

Account project provides Restful API supporting the following operations
- getAllAccounts
- addNewAccount
- deleteAccount

## Pre-requisites

The following will be required to build and run this application
```
Java 8 
Maven
```
Steps to install Java 8 https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

Steps to install maven https://maven.apache.org/install.html

Application is developed in TDD method using java 8 build version 1.8.0_201, apache maven 3.6.0

## Assumptions / current implementation scope 
- This is a demo type of project, not coded to product standard 
- Dao is not implemented/dummy implementation of dao is provided
- Production level code will have more validations using a validation framework, 
exception handling, 
integration tests will have a test to verify if the data is deleted from the database, 
error messages will from resource file,
HttpStatus.Created (201) can also be returned for create operation

## Building the application
Application can be build on IDE like eclipse or on command line by running the following command 
```
mvn clean compile
```
## Running the tests
Application functionality can be verified using the unit tests.
The tests can be run using IDE like eclipse or by running the following command at root folder
```
mvn clean test
```

## Unit & Integration Tests
```
AccountServiceImplTest
AccountControllerTest 
AccountControllerIT
```

