-- @author Leo Kuhlmey
-- @author Julian Bücher


-- Creation of Tables for Schema Wiki
--maybe in the following code must be many changes, we have to check the excel format before
CREATE TABLE dbo.student(
    hs_id UUID PRIMARY KEY,
    matrikelnummer VARCHAR(5),
    prename VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    age int NOT NULL
);


-- Creating the additional Users
CREATE USER serviceuser WITH PASSWORD 'serviceuser2020';
GRANT CONNECT ON DATABASE example TO serviceuser;
GRANT USAGE ON SCHEMA dbo TO serviceuser;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA dbo TO serviceuser;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA dbo TO serviceuser;
