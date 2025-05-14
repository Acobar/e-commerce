LABEL authors="Christopher Sage"
FROM amazoncorretto:24-jdk
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]