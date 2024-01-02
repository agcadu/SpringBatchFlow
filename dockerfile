FROM openjdk:17-jdk-alpine

RUN mkdir /files

COPY target/SpringBatchFlow-0.0.1-SNAPSHOT.jar Spring_Batch_Flow.jar

RUN chmod -R 777 /files

WORKDIR /

ENTRYPOINT ["java", "-jar", "/Spring_Batch_Flow.jar"]