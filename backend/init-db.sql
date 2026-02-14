IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'health-tracking-db')
BEGIN
    CREATE DATABASE [health-tracking-db];
END
GO

-- Create login if not exists
USE master;
GO


IF NOT EXISTS (SELECT name FROM sys.server_principals WHERE name = 'burak')
BEGIN
    CREATE LOGIN burak WITH PASSWORD = 'Bur@kCan_2024';
END
GO

-- Switch to health-tracking-db and create user
USE [health-tracking-db];
GO

IF NOT EXISTS (SELECT name FROM sys.database_principals WHERE name = 'burak')
BEGIN
    CREATE USER burak FOR LOGIN burak;
    ALTER ROLE db_owner ADD MEMBER burak;
END
GO