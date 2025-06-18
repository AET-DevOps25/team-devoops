-- Create user-data table
-- chat_id: UUID of the chat
-- group_id: UUID of the group in the chat
CREATE TABLE IF NOT EXISTS `chats` (
    chat_id INT PRIMARY KEY,
    group_id INT
);

-- Create user-credentials table
-- message_id: UUID of the message
-- chat_id: UUID of the chat the message was sent in
-- user_id: UUID of the author of the message
-- timestamp: time the message was sent
-- content: content of the message
CREATE TABLE IF NOT EXISTS `messages` (
    message_id INT PRIMARY KEY,
    chat_id INT,
    user_id INT,
    timestamp INT,
    content VARCHAR(255) NOT NULL
);