FROM openjdk:17-jdk-alpine
COPY target/*.jar /usr/local/ether.jar
CMD ["java","-jar","/usr/local/ether.jar"]