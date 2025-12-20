#=================================================================================

ARG JAVA_VERSION=21.0.3_9-jre-alpine@sha256:23467b3e42617ca197f43f58bc5fb03ca4cb059d68acd49c67128bfded132d67

FROM eclipse-temurin:${JAVA_VERSION} AS builder
WORKDIR /application

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar

RUN ["java", "-Djarmode=layertools", "-jar", "application.jar", "extract"]

#=================================================================================

FROM eclipse-temurin:${JAVA_VERSION}

WORKDIR /application

# copy the application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader ./
COPY --from=builder application/internal-dependencies ./
COPY --from=builder application/snapshot-dependencies ./
COPY --from=builder application/application/ ./

# create usergroup 'javauser' and 'javauser' user     
RUN addgroup --system javauser \
      && adduser -S -s  /bin/false -G javauser javauser \
      && chown -R javauser:javauser /application

# user the user 'javauser'
USER javauser

# execute the application
ENTRYPOINT [\
    "java",\
	# Profiling
    # "-Dcom.sun.management.jmxremote.port=9010",\ 
    # "-Dcom.sun.management.jmxremote.ssl=false",\ 
    # "-Dcom.sun.management.jmxremote.authenticate=false",\
    # "-Dcom.sun.management.jmxremote.local.only=false",\
    # Debug
    "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000",\
    # Spring  
    #"-Dspring.profiles.active=local",\
    # VM
	"-XX:+UseContainerSupport",\ 
	"-XX:InitialRAMPercentage=40.0",\ 
	"-XX:MinRAMPercentage=20.0",\ 
	"-XX:MaxRAMPercentage=80.0",\
	# Exec
    "org.springframework.boot.loader.launch.JarLauncher"\
]

HEALTHCHECK --start-period=1s CMD curl --fail https://localhost:8080/health | exit 1 

EXPOSE 8080 8000 9010 
