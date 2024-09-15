# ecommerce-app

# Connecting to the postgres container and create and drop databases
docker exec -it ec_postgresql bash
su - postgres
psql -U karthi  
create database product;
create database payment;
create database "order";