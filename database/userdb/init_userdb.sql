-- user-data table
-- contains basic information about a user provided on first login
    -- user_id: UUID of the given user
    -- user_email: User's E-Mail address
    -- name: user's name
    -- gender: user's gender
    -- degree: user's TUM degree
    -- age: user's age
CREATE TABLE IF NOT EXISTS `user_data` (
    user_id VARBINARY(16) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(255) NOT NULL,
    degree VARCHAR(255) NOT NULL,
    age INT NOT NULL
); 

-- user-interests table
-- contains pairs of users and interests based on information provided on login
    -- user_id: UUID of the given user
    -- interest: String representing a given interest
CREATE TABLE IF NOT EXISTS `user_interests` (
    user_id VARBINARY(16) PRIMARY KEY,
    interest VARCHAR(255) UNIQUE NOT NULL
);

-- user_credentials table
    -- user_id: UUID of the given user
    -- password_hash: user's hashed password
CREATE TABLE IF NOT EXISTS `user_credentials` (
    user_id VARBINARY(16) PRIMARY KEY,
    password_hash VARCHAR(255) NOT NULL
);