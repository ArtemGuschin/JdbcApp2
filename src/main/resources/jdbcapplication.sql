
CREATE TABLE IF NOT EXISTS writers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    writer_status VARCHAR(100) NOT NULL

);


CREATE TABLE IF NOT EXISTS labels (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    label_status VARCHAR(100) NOT NULL

);


CREATE TABLE IF NOT EXISTS posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    writer_id BIGINT,
    post_status VARCHAR(100) NOT NULL,
     FOREIGN KEY (writer_id) REFERENCES writers(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS post_labels (
    post_id BIGINT,
    label_id BIGINT,
    PRIMARY KEY (post_id, label_id),
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (label_id) REFERENCES labels(id) ON DELETE CASCADE
);
create  database jdbcapplication;
use jdbcapplication
select *from  posts

SELECT *  FROM posts p
            LEFT JOIN post_labels pl ON p.id = p.id = pl.post_id
            LEFT JOIN labels l  ON  l.id = pl.label_id

SELECT p.id, p.content, p.created, p.updated, p.post_status,
            w.id as writer_id, w.firstname, w.lastname, l.id as label_id, l.name
            from posts p
            left join post_labels pl on p.id = pl.post_id left join labels l on pl.label_id = l.id
            left join writers w on w.id = p.writer_id


CREATE TABLE IF NOT EXISTS writer_posts (
    writer_id BIGINT,
    post_id BIGINT,
    PRIMARY KEY (writer_id, post_id),
    FOREIGN KEY (writer_id) REFERENCES writers(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);
select * from writer_posts

