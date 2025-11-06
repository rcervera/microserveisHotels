FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/api-gateway-3.2.5.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
