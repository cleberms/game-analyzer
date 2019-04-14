# Backend Test
 Candidate: Cleber Santaterra
 
 ### Project
 The project was structured following the clean architecture pattern of Uncle Bob.
 
 [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
 
  ### Technologies
  - Java 1.8
  - Mongo
  
  ### Solution
  Parser log file to analyze game statistics
  
  All analyzes are saved in the mongo database and made available by the API
  
  #### Mongo Database
  Mongo is hosted on heroku server
  
  *Host*: ds213513.mlab.com<br>
  *Database*: heroku_0nngbvrm<br>
  *User*: mlivre<br>
  *Pass*: mlivre2014
  
  ### Start application
  In directory on project execute:
   
   - mvn clean install spring-boot:run
   
   ### Swagger
   [http://localhost:8085/swagger-ui.html#/](http://localhost:8085/swagger-ui.html#/)