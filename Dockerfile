FROM eclipse-temurin:25-jdk-jammy
WORKDIR /app
COPY target/oda-font-service-0.6.0.jar /app

CMD ["java","--add-opens","java.base/java.time=ALL-UNNAMED","-jar","oda-font-service-0.6.0.jar"]
