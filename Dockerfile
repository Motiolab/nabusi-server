FROM amazoncorretto:17
WORKDIR /app
# Expecting the referenced jar to be built securely by the CI pipeline before building this image
# or locally via ./gradlew clean build
COPY build/libs/nabusi-server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
