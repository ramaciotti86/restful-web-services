Restful Web Services (restful-web-services)
===========================================

Studying Spring I take a course called "Java Web Services & REST API - RESTful & SOAP - with Spring & Spring Boot" from Udemy

What I created in this project
--------------------------
* Spring Boot
* Service and Resource classes* 
* @PathVariable
* @GetMapping
* @PostMapping
* @DeleteMapping
* Return the correct HTTP Status code and Location
  * find on CustomizedExceptionHandler java or other method that return ResponseEntity
* Exception Handler
  * user package has Exception classes
  * post pacakge has Exception classes
* Generic Exception Handler
  * find Handler on exception package
* Bean Validation with spring-boot-starter-validation
  * find validation on User and UserResource.createUser when receive the RequestBody
* HATEOAS for Restful Services
  * find HATEOAS on UserResource.retrieveUserAndPosts
* Internationalization (i18n)
  * find messages files on resources/ folder
* Content Negotiation - Support for XML
  * find the dependency on pom.xml - jackson-dataformat-xml 
* HTTP Requests
  * find requests for Hello World, User and Post under test/httpRequests
* Swagger 3
* Spring Boot Actuator
* Filtering
  * Static
  * Dynamic
* API Versioning
  * URI Versioning
  * Request Param Versioning
  * Header versioning
  * Content Negotiation or Accept Header Versioning or Mimetype Versioning
* Spring Security
  * Basic Approach for Authentication