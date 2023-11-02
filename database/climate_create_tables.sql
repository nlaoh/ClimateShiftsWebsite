-- Create tables for Semester 1 2023 CTG example ER Model
PRAGMA foreign_keys = OFF;
drop table if exists Country;
drop table if exists Date;
PRAGMA foreign_keys = ON;

CREATE TABLE Country (
   CountryCode TEXT NOT NULL,
   CountryName Text,
   PRIMARY KEY (CountryCode)
);

CREATE TABLE Date (
   Year NOT NULL,
   PRIMARY KEY (Year)
);

-- ||||| DELETE ASIA COLUMNS IN COUNTRY (AS IT IS NOT A COUNTRY) |||||
--First check if Asia is connected to any other countries using the query below
SELECT CountryCode, CountryName, Population 
    FROM CountryTempObservation
    WHERE CountryCode = "SAS";
--Then delete the tables with SAS
DELETE FROM CountryTempObservation WHERE CountryCode = "SAS";


CREATE TABLE CountryTempObservation (
   Year INTEGER NOT NULL,
   CountryCode TEXT NOT NULL,
   CountryName TEXT,
   AvgTemp REAL,
   MinTemp REAL,
   MaxTemp REAL,
   Population INTEGER,
   PRIMARY KEY (Year, CountryCode),
   FOREIGN KEY (Year) REFERENCES Date (Year)
);

CREATE TABLE GlobalTempObservation (
    Year INTEGER NOT NULL,
    AvgTemp REAL,
    MinTemp REAL,
    MaxTemp REAL,
    LandOceanAvgTemp REAL,
    LandOceanMinTemp REAL, 
    LandOceanMaxTemp REAL
);

CREATE TABLE CityTempObservation (
    Year INTEGER NOT NULL,
    CountryName TEXT,
    CityName TEXT,
    Latitude TEXT,
    Longitude TEXT,
    AvgTemp REAL,
    MinTemp REAL,
    MaxTemp REAL,
    PRIMARY KEY (Year, CityName, Latitude, Longitude)
);

CREATE TABLE StateTempObservation (
    Year INTEGER NOT NULL,
    StateName TEXT,
    AvgTemp REAL,
    MinTemp REAL,
    MaxTemp REAL,
    PRIMARY KEY (Year)
);

CREATE TABLE Student (
    SID TEXT,
    FirstName TEXT,
    LastName TEXT,
    EmailAddress TEXT,
    PRIMARY KEY (SID)
);
CREATE TABLE StudentCreatedPersona (
    SID TEXT,
    PersonaName TEXT
);
CREATE TABLE StudentRole (
    RoleTitle TEXT,
    SID TEXT
);
CREATE TABLE Role (
    RoleTitle TEXT,
    PRIMARY KEY (RoleTitle)
);
    
CREATE TABLE Persona (
    Name TEXT,
    Image_FilePath TEXT,
    Description TEXT,
    PRIMARY KEY (NAME)
);

CREATE TABLE PersonaAttribute (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    PersonaName TEXT,
    AttributeType TEXT,
    Description TEXT
);

