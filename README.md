# centiglobe-assignment
Spring data rest example

# Useful info
http://localhost:8080/swagger-ui/index.html

curl -i -X GET -H "Content-Type:application/json" http://localhost:8080/api/configs

curl -X 'POST' 'http://localhost:8080/api/configs' -H 'accept:application/hal+json' -H 'Content-Type: application/json' -d '{"memberName": “@rakdr", "maxConnections": 2, "status": "Active"}'

curl -X 'GET' 'http://localhost:8080/api/configs/search/findByMemberName?memberName=%40rakdr' -H 'accept: application/hal+json'


# References
https://stackoverflow.com/questions/45401734/how-to-work-with-dto-in-spring-data-rest-projects
https://spring.io/guides/gs/accessing-data-rest
https://stackoverflow.com/questions/59945364/spring-data-rest-with-validation
https://medium.com/@mertkagan/spring-data-rest-say-goodbye-to-controller-and-service-1acb6c7437f1
https://github.com/spring-projects/spring-data-examples/tree/main/rest/security
