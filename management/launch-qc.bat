title management-service
call mvn clean install -Dmaven.test.skip=true
call mvn spring-boot:run -Dspring-boot.run.profiles=qc
pause