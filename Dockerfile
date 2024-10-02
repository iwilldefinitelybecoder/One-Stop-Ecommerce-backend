FROM openjdk:17-jdk-alpine
EXPOSE 8080
COPY target/*.jar $APP_HOME/app.jar
WORKDIR $APP_HOME
CMD ["java", "-jar", "/app.jar"]
