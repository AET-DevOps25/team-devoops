-- Create user-data table
-- user_id UUID associated with a user
-- group_id UUID of the group a user has been matched withe
CREATE TABLE IF NOT EXISTS `matching` (
    user_id INT PRIMARY KEY,
    group_id INT
);

-- Create groups table
-- group_id: UUID of each matched group
-- meetup_time: Date and Time a group is set to meet
CREATE TABLE IF NOT EXISTS `groups` (
    group_id INT PRIMARY KEY,
    meetup_time INT
);