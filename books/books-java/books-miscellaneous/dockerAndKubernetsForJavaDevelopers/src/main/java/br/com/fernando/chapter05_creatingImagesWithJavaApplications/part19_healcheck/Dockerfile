FROM jeanblanchard/java:8                                   
COPY rest-example-0.1.0.jar rest-example-0.1.0.jar   
CMD java -jar rest-example-0.1.0.jar
HEALTHCHECK --interval=5m --timeout=2s --retries=3 CMD curl -f http://localhost/ping || exit 1                        
EXPOSE 8080