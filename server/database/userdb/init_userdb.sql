-- user-data table
-- contains basic information about a user provided on first login
    -- user_id: UUID of the given user
    -- user_email: User's E-Mail address
    -- name: user's name
    -- gender: user's gender
    -- degree: user's TUM degree
    -- birthday: user's Date of birth
CREATE TABLE IF NOT EXISTS `user_data` (
    user_id VARBINARY(16) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL,
    gender VARCHAR(255) NOT NULL,
    degree VARCHAR(255) NOT NULL,
    degree_start INT NOT NULL,
    bio VARCHAR(1023) NOT NULL
); 

-- user-interests table
-- contains pairs of users and interests based on information provided on login
    -- user_id: UUID of the given user
    -- interest: String representing a given interest
CREATE TABLE IF NOT EXISTS `user_interests` (
    list_id VARBINARY(16) PRIMARY KEY,
    user_id VARBINARY(16) NOT NULL,
    interest VARCHAR(255) NOT NULL
);

-- user-auth0 table
-- contains pairs of user_IDs and their auth0 sub IDs
    -- user_id: UUID of the given user
    -- auth_id: Users's Auth0 ID
CREATE TABLE IF NOT EXISTS `user_identity` (
    pair_id VARBINARY(16) PRIMARY KEY,
    user_id VARBINARY(16) UNIQUE NOT NULL,
    auth_id VARCHAR(255) UNIQUE NOT NULL
);