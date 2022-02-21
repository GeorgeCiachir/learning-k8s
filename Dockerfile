FROM openjdk:11-jre-slim

ARG APPLICATION_ROOT=/application
ARG LOG_DIR=${APPLICATION_ROOT}/logs

# Create the new application directory that will hold the application artifacts
# Create the logs directory in the application directory
# Grant the access on the logs directory to the non root user

RUN mkdir -p ${LOG_DIR} && \
    chown 1000:1000 ${LOG_DIR} && \
    chown 1000:1000 ${APPLICATION_ROOT}

# Copy the application artifacts

COPY ./target/app-for-cloud-0.0.1-SNAPSHOT.jar /application/app.jar
COPY ./target/classes/templates/greeting.html /application/templates/greeting.html

# Change the permissions on the files

RUN chmod 0755 /application && \
    chmod 0444 /application/app.jar

# Switch to a non root user

#USER 1000:1000

WORKDIR /application

CMD [ "java", "-jar", "/application/app.jar" ]

EXPOSE 8080
