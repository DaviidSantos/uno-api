FROM openjdk:17
ADD ./uno-lims.jar uno-lims.jar
ENTRYPOINT ["java", "-jar", "uno-lims.jar"]