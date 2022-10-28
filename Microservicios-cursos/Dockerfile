FROM openjdk:11-jdk-slim-buster as builder
WORKDIR /app/Microservicios-cursos

COPY ./pom.xml /app
COPY ./Microservicios-cursos/.mvn ./.mvn
COPY ./Microservicios-cursos/mvnw .
COPY ./Microservicios-cursos/pom.xml .

RUN  ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline

COPY ./Microservicios-cursos/src ./src

RUN  ./mvnw clean package -DskipTests

FROM openjdk:11
WORKDIR /app

COPY --from=builder /app/Microservicios-cursos/target/Microservicios-cursos-0.0.1-SNAPSHOT.jar .

EXPOSE 8001
ENTRYPOINT ["java", "-jar", "Microservicios-cursos-0.0.1-SNAPSHOT.jar"]