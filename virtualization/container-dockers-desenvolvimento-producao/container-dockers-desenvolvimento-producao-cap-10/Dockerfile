FROM alpine:3.1
MAINTAINER Daniel Romero <infoslack@gmail.com>

RUN apk add --update mysql mysql-client pwgen && rm -rf /var/cache/apk/*

ADD mysql.cnf /etc/mysql/my.cnf
RUN chmod 664 /etc/mysql/my.cnf

ADD run.sh /run.sh
RUN chmod +x /run.sh

EXPOSE 3306

CMD ["/run.sh"]