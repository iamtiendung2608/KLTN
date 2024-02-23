# Copy .jar file from build and run
FROM ibm-semeru-runtimes:open-17-jre

# Set work dir
WORKDIR /app

# Expose port
EXPOSE 8080 5005

# Copy jar to app.jar
ADD /target/block-chain-project.jar /app/block-chain-project.jar

ENTRYPOINT ["java","-Xms256m","-Xmx4g","-XX:+UseZGC","-jar","block-chain-project.jar"]
RUN apt-get update; apt-get install -y fontconfig libfreetype6