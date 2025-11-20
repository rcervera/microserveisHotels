# -----------------------------------------------------------------
# STAGE 1: El "Builder" (Construirà el projecte)
# -----------------------------------------------------------------
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# Copiem els arxius de Maven
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Solucionem els problemes de permisos
RUN chmod +x mvnw

# Descarreguem dependències (optimització)
RUN ./mvnw dependency:go-offline

# Copiem la resta del codi font
COPY src/ src/

# Compilem i creem el JAR
RUN ./mvnw package -DskipTests

# -----------------------------------------------------------------
# STAGE 2: El "Runner" (Imatge final lleugera)
# -----------------------------------------------------------------
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copiem el JAR que hem creat a l'STAGE 1
COPY --from=builder /app/target/*.jar app.jar

# Canviem el port per al hotel-service
EXPOSE 6000

ENTRYPOINT ["java", "-jar", "app.jar"]