FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY target/oda-font-service-0.5.0.jar /app

CMD ["java","--add-opens","java.base/java.time=ALL-UNNAMED","-jar","oda-font-service-0.5.0.jar"]
