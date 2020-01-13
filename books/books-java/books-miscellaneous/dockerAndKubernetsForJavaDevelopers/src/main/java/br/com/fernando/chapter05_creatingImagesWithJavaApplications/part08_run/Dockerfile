FROM ubuntu:18.04
MAINTAINER nandao

# Never do it !
# RUN apt-get update
# RUN apt-get install -y openjdk-8-jre

# You HAVE TO do it
RUN apt-get update \ 
	&& apt-get install -y openjdk-8-jre \ 
	&& apt-get install -y nodejs \ 
	&& apt-get clean