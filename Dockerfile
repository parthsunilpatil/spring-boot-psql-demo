FROM openjdk:8-jdk-alpine
VOLUME /tmp
ENV PG_HOST postgres.kube-db
ENV PG_PORT 5432
ENV PG_DATABASE info_db
ENV PG_USER admin
ENV PG_PASSWORD admin123
COPY target/spring-boot-postgres-demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app.jar"]