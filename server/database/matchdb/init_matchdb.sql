-- matches table
-- which user has been matched with which group
    -- match_id: UUID of the match, primary key
    -- user_id: UUID of the matched user
    -- group_id: UUID of group the user has been matched to
    -- invite_status: Has the user RSVP'd?
CREATE TABLE IF NOT EXISTS `matches` (
    match_id VARBINARY(16) PRIMARY KEY,
    user_id VARBINARY(16) NOT NULL,
    group_id VARBINARY(16) NOT NULL,
    invite_status VARCHAR(255) NOT NULL
);

-- groups table
-- information about groups
    -- group_id: UUID of the group
    -- meet_date: date a group is set to meet
    -- meet_timeslot: time a group is set to meet, encoded as Integer
    -- meet_location: mensa a group is set to meet at
CREATE TABLE IF NOT EXISTS `groups` (
    group_id VARBINARY(16) PRIMARY KEY,
    meet_date DATE NOT NULL,
    meet_timeslot INT NOT NULL,
    meet_location VARCHAR(255) NOT NULL
);

-- prompts table
-- contains conversation starters
    -- prompt_id: UUID of the prompt
    -- group_id: UUID of the group this conversation starter prompt belongs to
    -- prompt: prompt itself
CREATE TABLE IF NOT EXISTS `prompts` (
    prompt_id VARBINARY(16) PRIMARY KEY,
    group_id VARBINARY(16) NOT NULL,
    prompt VARCHAR(1023) NOT NULL
);

-- match requests table
-- individual match requests
    -- request_id: UUID of the request, primary key
    -- user_id: UUID of user making the request
    -- group_id: UUID of the group the user was matched to. Null if Unmatched
    -- request_date: date the user would like to be matched
    -- degree_pref: does user prefer matches with same degree?
    -- age_pref: does user prefer matches with same age?
    -- gender_pref: does user prefer matches with same gender?
CREATE TABLE IF NOT EXISTS `match_requests` (
    request_id VARBINARY(16) PRIMARY KEY,
    user_id VARBINARY(16) NOT NULL,
    request_date DATE NOT NULL,
    request_location VARCHAR(255) NOT NULL,
    degree_pref BIT NOT NULL,
    age_pref BIT NOT NULL,
    gender_pref BIT NOT NULL
    request_status VARCHAR(255) NOT NULL
);

-- match timeslot table
-- What time-slots has a user selected in their requests
    -- timeslot_id: UUID primary key (Unique)
    -- request_id: UUID of the corresponding request
    -- time_slot time slot (encoded as integer)
CREATE TABLE IF NOT EXISTS `match_timeslots` (
    timeslot_id VARBINARY(16) PRIMARY KEY,
    request_id VARBINARY(16) NOT NULL,
    request_timeslot INT NOT NULL
);