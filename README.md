```
      ___           ___           ___           ___     
     /\__\         /\__\         /\  \         /\  \    
    /::|  |       /:/  /        /::\  \       /::\  \   
   /:|:|  |      /:/  /        /:/\ \  \     /:/\ \  \  
  /:/|:|__|__   /:/  /  ___   _\:\~\ \  \   _\:\~\ \  \ 
 /:/ |::::\__\ /:/__/  /\__\ /\ \:\ \ \__\ /\ \:\ \ \__\
 \/__/~~/:/  / \:\  \ /:/  / \:\ \:\ \/__/ \:\ \:\ \/__/
       /:/  /   \:\  /:/  /   \:\ \:\__\    \:\ \:\__\  
      /:/  /     \:\/:/  /     \:\/:/  /     \:\/:/  /  
     /:/  /       \::/  /       \::/  /       \::/  /   
     \/__/         \/__/         \/__/         \/__/    
```

- **Authors:** Raúl Cayetano Díaz, Álvaro González Reneses, David Macías Rodríguez and Adrián Puertas Cabedo
- **Module:** App. Development Frameworks 
- **Professor:** Larkin Cunningham 
- **Course:** Software Development 
- **Due date:** 18/12/15

## Introduction

In this document we will expose the different requirements, data models, problem/solution nd 
entries that were relevant to this 2 assignment. 

## Requirements

Since we were advised not to get into much detail for the requirements elicitation, we omitted the information requirements. They can be easily extracted from the function requirements below.

## Functional requirements.

Every user in the application must be able to:

- FR001: Have access to the metadata from GitHub; that is, to a databa concerning all the cultural heritage objects (CHOs), Participants or Roles in an appropiate way.

Every registered user must be able to:

- FR002: List the users and display the profile of a user of the application.
- FR003: Follow and unfollow a user.
- FR004: Create reviews for CHOs, allowing them setting a rating.

Use social media features, such as following:

- FR005: Follow other users and like/appreciate CHOs.
- FR006: Have an activity stream on a user's home / profile page.
- FR007: Post messages to Facebook and/or Twitter.
- FR008: A crowdsourcing feature, such as allowing users to add tags or descriptions to CHOs.
- FR009: A gamification feature to allow users to receive points and badges for their participation on the site.

Participation could include, but is certainly not restricted to:
* Tagging / describing CHOs (points per tag, badges at certain level of points)
* Providing additional commentary / reviews of CHOs (consider whether numbers of likes / thumbs ups recieved for comments should form the basis of whether or how
many points are awarded).
* Accumulating followers and / or friends (e.g. get a special badge for accumulating 10, 25, 50, 100, etc).

## Non-Functional Requirements

- NFR001: Retrieve the data to the database from the GitHub metadata.
- NFR002: Several Data models could potentially be used for this project. You can choose from relational (e.g. MySQL), key-value (e.g. Redis), document-oriented (e.g. MongoDB), or graph (e.g. Neo4j). Or you can go for a polygot approach and use more than one database.
- NFR003: It is likely there will be a substantial relational database access layer in your solution. While JdbcTemplate can be used (e.g. reused from assignment 1), a substantial amount of your data access code (i.e. repositories) could also use JPA. Remember to include JUnit classes as you code.
- NFR004: The application must use the layered architecture patterns discussed in the lectures. You will also use Spring MVC as an architectural pattern. This means you should have a Presentation – Controller – Service – Repository architecture.
- NFR005: For the core product, you will use Spring Web / MVC and either / or of JdbcTemplate and Spring Data JPA. You must, however, implement one feature of the site using one of the following technologies: SOAP, REST, RabbitMQ / AMQP, Spring Data Neo4j.
- NFR006: Security. A consistent web application system should let each type of user (logged, anonymous, with admin rights, ..) access to the resources that are allowed for them, this should be achieved using Spring security (WebSecurityConfig).
- NFR007: User Interface. The application must assure that the user finds it “easy-to-use” and appropriate given the context and his necessities. Furthermore, it should be reasonably responsive to the correct display in smaller devices.

## Data model
The data model has been developed to adjust it to the new requirements in the Muss page approach. The new features required the inclusion of entities such as Review, Tag, User, Badge and a datatype as Gamification to cope with all the logic behind this part. To model it has been needed aggregations, associations, inheritance, and all types of navigability.
Due to this new UML we were bound to add new attributes to the already used entities.

![Data Model](img/data-model.png)

## Problem/solution entries and implementation challenges

## Not requested but delivered features

![Mobile Version](img/mobile.png)

## Understanding RabbitMQ



![Receive and convert](img/receiveAndConvert.png)

* We continue iterating over this until the “ShutdownSignalException” marks the end of the communication.

## Users

* All the users have the password: root
* There are currently four users: **reneses**, **adri**, **raul** and **david99**.
* Although it is possible to access to any of those, we recommend to create a new user in order to experience all the functionality.