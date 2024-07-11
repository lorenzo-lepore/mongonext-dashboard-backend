FROM amazoncorretto:22-alpine-full
WORKDIR /app
COPY target/springbackend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]