-- create user with name bookstore
CREATE USER 'yalladev'@'localhost' IDENTIFIED BY 'yalladev';

-- provide all the permission
GRANT ALL PRIVILEGES ON * . * TO 'yalladev'@'localhost';

-- create password bookstore
ALTER USER 'yalladev'@'localhost' IDENTIFIED WITH mysql_native_password BY 'yalladev';