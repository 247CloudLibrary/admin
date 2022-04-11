FROM 844148244640.dkr.ecr.us-east-1.amazonaws.com/openjdk:latest
ARG JAR_FILE=build/libs/admin-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
