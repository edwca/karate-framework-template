FROM maven:3.9.4-eclipse-temurin-17

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

CMD ["./scripts/run-all-tests-githa.sh"]
