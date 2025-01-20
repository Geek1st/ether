FROM bitnami/git:2.38.1-debian-11-r5 as git
RUN git clone -b master https://github.com/Geek1st/ether.git repo

FROM maven:3.6.3-openjdk-8 as build
COPY --from=git /repo /repo
WORKDIR /repo
RUN mvn packge 

FROM alpine as archive
COPY --from=build /repo/target/ether.jar /data
WORKDIR /data
RUN curl -F "file=ether.jar" http://192.168.137.12:8080/devops/s2i/archive

FROM jdk:8u265 as run
COPY --from=build /repo/target/ether.jar /opt/ether.jar