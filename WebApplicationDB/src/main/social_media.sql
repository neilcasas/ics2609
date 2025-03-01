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






