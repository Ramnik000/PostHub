DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS thread;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
    id LONG AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(120) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities (
    username VARCHAR(50),
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE IF NOT EXISTS thread (
    thread_id LONG AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    title VARCHAR(255),
    date DATE NOT NULL,
    time TIME NOT NULL
);


CREATE TABLE IF NOT EXISTS post (
    post_id LONG AUTO_INCREMENT PRIMARY KEY,
    thread_id LONG,
    username VARCHAR(50),
    content VARCHAR(255) NOT NULL
);

