FROM openjdk:11-jdk-slim-buster as builder
WORKDIR /app/Microservicio-usuarios

COPY ./pom.xml /app
COPY ./Microservicio-usuarios/.mvn ./.mvn
COPY ./Microservicio-usuarios/mvnw .
COPY ./Microservicio-usuarios/pom.xml .

RUN  ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline

COPY ./Microservicio-usuarios/src ./src

RUN  ./mvnw clean package -DskipTests

FROM openjdk:11
WORKDIR /app

COPY --from=builder /app/Microservicio-usuarios/target/Microservicio-usuarios-0.0.1-SNAPSHOT.jar .

EXPOSE 8001
ENTRYPOINT ["java", "-jar", "Microservicio-usuarios-0.0.1-SNAPSHOT.jar"]