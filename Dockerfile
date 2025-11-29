FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean bootJar

FROM gcr.io/distroless/java21
COPY --from=build /app/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]