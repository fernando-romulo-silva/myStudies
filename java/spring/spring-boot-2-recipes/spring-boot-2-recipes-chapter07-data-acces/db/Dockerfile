FROM postgres
MAINTAINER Marten Deinum <marten@deinum.biz>

ENV POSTGRES_DB customers
ENV POSTGRES_USER customers
ENV POSTGRES_PASSWORD customers

EXPOSE 5432
CMD ["postgres"]

# or just:
# docker run --name customers-db -e POSTGRES_DB=customers -e POSTGRES_USER=customers -e POSTGRES_PASSWORD=customers -d -p 5432:5432 postgres:latest
