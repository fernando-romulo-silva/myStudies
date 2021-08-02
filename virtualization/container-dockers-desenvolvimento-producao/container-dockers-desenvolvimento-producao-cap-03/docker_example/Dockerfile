FROM ubuntu
MAINTAINER Daniel Romero <infoslack@gmail.com>
RUN apt-get update
RUN apt-get install -y nginx
ADD exemplo /etc/nginx/sites-enabled/default
RUN echo "daemon off;" >> /etc/nginx/nginx.conf
ADD ./ /rails
WORKDIR /rails
EXPOSE 8080
CMD service nginx start