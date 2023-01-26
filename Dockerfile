FROM openjdk:8-jdk-alpine
COPY target/spring-security-micro-0.0.1-SNAPSHOT.jar springsecuritymicro.jar
ENTRYPOINT ["java","-jar","springsecuritymicro.jar"]