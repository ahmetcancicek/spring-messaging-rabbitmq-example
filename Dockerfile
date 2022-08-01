FROM openjdk:17.0.2

ADD ./target/gitbank.jar /app/

CMD ["java","-Xmx200m","-jar","/app/gitbank.jar"]

EXPOSE 8090