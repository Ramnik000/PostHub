INSERT INTO users (username, password, enabled) VALUES
('user1', '$2a$10$58BCAGhR9GCJgiJMDYTUSuHLupac0NbzuRpCfMFpAOxiWNdf8m43W', true),
('user2', '$2a$10$58BCAGhR9GCJgiJMDYTUSuHLupac0NbzuRpCfMFpAOxiWNdf8m43W', true),
('user3', '$2a$10$58BCAGhR9GCJgiJMDYTUSuHLupac0NbzuRpCfMFpAOxiWNdf8m43W', false);


INSERT INTO authorities (username, authority) VALUES
('user1', 'ROLE_USER'),
('user2', 'ROLE_ADMIN'),
('user3', 'ROLE_USER');

-- Insert data into the thread table with specific date and time
INSERT INTO thread (username, title, date, time) VALUES
('user1', 'Thread 1', '2023-11-27', '12:30:00'),
('user2', 'Thread 2', '2023-11-27', '13:45:00'),
('user3', 'Thread 3', '2023-11-27', '15:00:00');

-- Insert data into the post table with specific date and time
INSERT INTO post (thread_id, username, content) VALUES
(1, 'user1', 'This is the first post in Thread 1'),
(1, 'user2', 'Reply to Thread 1'),
(2, 'user3', 'Post in Thread 2'),
(3, 'user1', 'Another post in Thread 3');



