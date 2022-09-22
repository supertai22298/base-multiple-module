title management-service
REM call mvn clean install
call mvn install
call mvn spring-boot:run -Dspring-boot.run.profiles=staging
pause