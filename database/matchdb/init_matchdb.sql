-- matches table
-- contains information about what group a user has been matched with
    -- user_id UUID associated with a user
    -- group_id UUID of the group a user has been matched with
    -- rsvp Has the user RSVP'd the meeting
CREATE TABLE IF NOT EXISTS `matches` (
    match_id VARBINARY(16) PRIMARY KEY,
    user_id VARBINARY(16) NOT NULL,
    group_id VARBINARY(16) NOT NULL,
    rsvp BIT NOT NULL
);

-- groups table
-- contains information about groups
    -- group_id: UUID of each matched group
    -- meet_time: time a group is set to meet at
    -- meet_date: date a group is set to meet at
    -- mensa: mensa a group is set to meet at
CREATE TABLE IF NOT EXISTS `groups` (
    group_id VARBINARY(16) PRIMARY KEY,
    meet_date INT NOT NULL,
    meet_time INT NOT NULL,
    meet_place VARCHAR(255) NOT NULL
);

-- match requests table
-- contains information about individual requests for matches made by users
    -- user_id UUID associated with a user
    -- group_id UUID of the group a user was matched with. Null if unmatched
    -- time_slot time slots a user is available to be matched, encoded as an integer
    -- date_slot date a user is available to be matched
    -- degree_pref whether a user prefers others with the same degree
    -- age_pref whether a user prefers others of similar age
    -- gender_pref whether a user prefers others of the same gender
CREATE TABLE IF NOT EXISTS `match_requests` (
    request_id VARBINARY(16) PRIMARY KEY,
    user_id VARBINARY(16) NOT NULL,
    group_id VARBINARY(16) NOT NULL,
    time_slot INT NOT NULL,
    date_slot INT NOT NULL,
    degree_pref BIT,
    age_pref BIT,
    gender_pref BIT
);