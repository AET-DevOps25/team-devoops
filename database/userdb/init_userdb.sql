-- Create user-data table
-- user_id: UUID of the given user
-- name: user's name
-- gender: user's gender
-- TODO: Add more data
CREATE TABLE IF NOT EXISTS user_data (
    user_id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(255) NOT NULL
);

-- Create user-credentials table
-- user_id: UUID of the given user
-- email: user's email used on login
-- password_hash: user's hashed password
CREATE TABLE IF NOT EXISTS user_credentials (
    user_id INT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);