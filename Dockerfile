FROM fedora:41
WORKDIR /app
COPY target/oda-font-service /app

CMD ["./oda-font-service"]
