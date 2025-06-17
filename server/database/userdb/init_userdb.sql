-- Create user-data table
CREATE TABLE IF NOT EXISTS user_data (
    user_id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender ENUM('Male', 'Female', 'Other')
);

-- Create user-credentials table
CREATE TABLE IF NOT EXISTS user_credentials (
    user_id INT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);