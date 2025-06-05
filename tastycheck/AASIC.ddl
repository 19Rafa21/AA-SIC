CREATE TABLE Restaurant (Id varchar(255) NOT NULL, Owner varchar(255), Name varchar(255), Location varchar(255), CuisineType varchar(255), Rating float8, Image varchar(255), PRIMARY KEY (Id));
CREATE TABLE Review (Id varchar(255) NOT NULL, Rating float8, Text varchar(255), Author varchar(255), Restaurant varchar(255), PRIMARY KEY (Id));
CREATE TABLE Reply (Id varchar(255) NOT NULL, Text varchar(255), Review varchar(255), Author varchar(255), PRIMARY KEY (Id));
CREATE TABLE "User" (Id varchar(255) NOT NULL, Username varchar(255), Password varchar(255), Email varchar(255), Discriminator varchar(255) NOT NULL, PRIMARY KEY (Id));
