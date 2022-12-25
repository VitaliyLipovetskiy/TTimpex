FROM --platform=linux/amd64 maven:3.8.6-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
COPY checkstyle.xml /home/app
COPY system.properties /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

FROM --platform=linux/amd64 openjdk:11
RUN mkdir /opt/results
RUN mkdir /app
WORKDIR /app
COPY --from=build /home/app/target/*.war /app/app.war
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

CMD ["/app/entrypoint.sh"]