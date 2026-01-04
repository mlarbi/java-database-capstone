
# Architectural Design for Smart Clinic Management System application

## Overview
This is a Spring Boot application. 

It follows a 3-tier Design, with Presentation, Application, and Data tiers.

1. Presentation Tier
    - Thymeleaf templates are used used to generate content for display in the browser.
    - This tier consumes REST API created and made available in the application tier

2. Application Tier \
This is the Spring Boot backend consisting of 
    - controllers
    - REST API server
    - services
    - business logic

3. Data Tier \
The data tier is where data in the application is persisted.  Two databases are used:
    - MySQL - relational database used ot store patient, doctor, appointment, and admin data
    - MongoDB - a NoSQL database for storing prescription data.

Thymeleaf templates are used for the Admin and Doctor dashboards, while REST APIs serve all other modules. 
The application interacts with two databases—MySQL (for patient, doctor, appointment, and admin data) and MongoDB (for prescriptions). 
All controllers route requests through a common service layer, which in turn delegates to the appropriate repositories. MySQL uses JPA entities while MongoDB uses document models.

## Flow of Data and Control 

There are 7 layers in the software design for handling the flow of data and the processing of user interactions within the applicaiton:

1. User Interface Layer \
The user accessess the Admin Dashboard or Appointment pages in a web browser.

2. Controller Layer \
User actions in the user interface are routed to the appropriate Thymeleaf or REST controller.

3. Service Layer \
The controller calls the service layer to process requests by interacting with other backend components.

4. Repository \
The repository is the interface through which the service layer interacts with the backend databases.

5. Database access \
Database access leverages JPA to access the the MySQL and MongoDB dataases

6. Model binding \
Classes respresenting the data entities that are persisted to the databases are modelled as JPA Entites and bound to the database tables and collections.

7. Application models \
These are classes that are mapped to REST API endpoints and used to process API calls. 