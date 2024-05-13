FROM eclipse-temurin:21-jdk
VOLUME /tmp
ARG JAR_FILE=build/libs/gcp-card-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]