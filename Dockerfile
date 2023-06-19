FROM openjdk:17-oracle
ADD target/project-0.0.1-SNAPSHOT.jar financier
ENTRYPOINT ["java", "-jar","financier"]
EXPOSE 8080