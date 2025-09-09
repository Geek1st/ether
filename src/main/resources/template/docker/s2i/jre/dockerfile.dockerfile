FROM alpine/git:v2.49.1 as scm
RUN git clone https://github.com/geek1st/ether

FROM maven
COPY --from=scm /ether /app
WORKDIR /app
RUN mvn clean package

FROM ubuntu/jre:21-24.04_stable
COPY --from=maven /app/target/ether-0.0.1-SNAPSHOT.jar /app/ether.jar
CMD ["java", "-jar", "/app/ether.jar"]