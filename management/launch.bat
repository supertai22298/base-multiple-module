title management
call mvn clean install -Dmaven.test.skip=true
call mvn spring-boot:run -Dspring-boot.run.profiles=dev
pause