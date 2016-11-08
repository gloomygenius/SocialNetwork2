CREATE TABLE Users (
  id         BIGINT PRIMARY KEY AUTO_INCREMENT,
  email      VARCHAR(255) NOT NULL UNIQUE,
  password   VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  gender     INT          NOT NULL,
  role       INT          NOT NULL,
  telephone  VARCHAR(20),
  birthday   DATE,
  country    VARCHAR(20),
  city       VARCHAR(25),
  university VARCHAR(50),
  team       INT,
  position   INT,
  about      VARCHAR(255),
);

CREATE TABLE Relations (
  sender_id     BIGINT NOT NULL,
  recipient_id       BIGINT NOT NULL,
  relation_type INT    NOT NULL,
  FOREIGN KEY (sender_id) REFERENCES Users (id),
  FOREIGN KEY (recipient_id) REFERENCES Users (id)
);

INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('admin@exam.com', '123456', 'Василий', 'Бобков', 0, 2);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('oldmail@exam.com', '123456', 'Петя', 'Петров', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('user3@exam.com', '123456', 'Антон', 'Чехов', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('pushkin@exam.com', '123456', 'Александр', 'Пушкин', 0, 1);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('philipp@exam.com', '123456', 'Филипп', 'Преображенский', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('makedonskiy@exam.com', '123456', 'Александр', 'Македонский', 0, 0);
INSERT INTO Users (email, password, first_name, last_name, gender, role)
VALUES ('elena@exam.com', '123456', 'Елена', 'Троянская', 1, 0);

INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,2,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (1,4,1);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (5,1,1);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (3,1,3);
INSERT INTO Relations(sender_id, recipient_id, relation_type) VALUES (4,2,3);