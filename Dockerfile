FROM eclipse-temurin:17

WORKDIR /app

COPY . .

RUN ./gradlew dependencies

RUN ./gradlew build

EXPOSE 3001

CMD ["./gradlew", "bootRun"]