CREATE DATABASE IF NOT EXISTS social_media;
USE social_media;

CREATE TABLE account (
    user_name VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    user_role ENUM('admin', 'user', 'super_admin') NOT NULL
);

CREATE TABLE posts (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50),
    content VARCHAR(200) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_name) REFERENCES account(user_name) ON DELETE CASCADE
);

CREATE TABLE follows (
    user_name VARCHAR(50),
    followed_user_name VARCHAR(50),
    PRIMARY KEY (user_name, followed_user_name),
    FOREIGN KEY (user_name) REFERENCES account(user_name) ON DELETE CASCADE,
    FOREIGN KEY (followed_user_name) REFERENCES account(user_name) ON DELETE CASCADE
);

CREATE TABLE admin_messages (
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    sender VARCHAR(50),
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender) REFERENCES account(user_name) ON DELETE CASCADE
);
