FROM openjdk:8-alpine
ADD build/libs/*.jar service-a.jar
EXPOSE 8080
RUN sh -c 'touch /service-a.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/service-a.jar"]
