CREATE DATABASE IF NOT EXISTS social_media;
USE social_media;


CREATE TABLE account (
    user_name VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    user_role ENUM('admin', 'user') NOT NULL
);


CREATE TABLE posts (

    user_name VARCHAR(50),
    post1 VARCHAR(200),
    post2 VARCHAR(200),
    post3 VARCHAR(200),
    post4 VARCHAR(200),
    post5 VARCHAR(200),
    PRIMARY KEY (user_name),
    FOREIGN KEY (user_name) REFERENCES account(user_name) ON DELETE CASCADE
);


CREATE TABLE follows (
    user_name VARCHAR(50),
    follow1 VARCHAR(50),
    follow2 VARCHAR(50),
    follow3 VARCHAR(50),
    PRIMARY KEY (user_name),
    FOREIGN KEY (user_name) REFERENCES account(user_name) ON DELETE CASCADE,
    FOREIGN KEY (follow1) REFERENCES account(user_name) ON DELETE SET NULL,
    FOREIGN KEY (follow2) REFERENCES account(user_name) ON DELETE SET NULL,
    FOREIGN KEY (follow3) REFERENCES account(user_name) ON DELETE SET NULL
);

CREATE TABLE admin_messages (
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    sender VARCHAR(50),
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender) REFERENCES account(user_name) ON DELETE CASCADE
);

-- Insert mock accounts
INSERT INTO account (user_name, password, user_role) VALUES
('alice', 'hashed_password1', 'user'),
('bob', 'hashed_password2', 'user'),
('charlie', 'hashed_password3', 'admin'),
('dave', 'hashed_password4', 'user');

-- Insert mock posts
INSERT INTO posts (user_name, post1, post2, post3, post4, post5) VALUES
('alice', 'Hello world!', 'Loving SQL!', 'Learning MySQL', NULL, NULL),
('bob', 'First post!', 'SQL is cool', NULL, NULL, NULL),
('charlie', 'Admin update!', 'Database changes coming soon.', NULL, NULL, NULL),
('dave', 'Just joined!', NULL, NULL, NULL, NULL);

-- Insert mock follows
INSERT INTO follows (user_name, follow1, follow2, follow3) VALUES
('alice', 'bob', 'charlie', NULL),
('bob', 'alice', NULL, NULL),
('charlie', 'alice', 'dave', NULL),
('dave', 'bob', NULL, NULL);

-- Insert mock admin messages
INSERT INTO admin_messages (sender, content) VALUES
('charlie', 'System maintenance scheduled for tomorrow.'),
('charlie', 'New features rolling out next week!');
