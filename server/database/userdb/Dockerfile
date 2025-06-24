# Creates an image with a mysql database based on the template init_userdb.sql
# Expects a root password to be passed via the MYSQL_ROOT_PASSWORD environmental variable

# Use mysql as base image for the databases
FROM mysql:8.0

# ENV MYSQL_ROOT_PASSWORD=password
ENV MYSQL_DATABASE=userdb

# Initialize database
COPY init_userdb.sql /docker-entrypoint-initdb.d/

# Expose port for MySQL
EXPOSE 3306