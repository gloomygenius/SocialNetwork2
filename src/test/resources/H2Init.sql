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
  recipient_id  BIGINT NOT NULL,
  relation_type INT    NOT NULL,
  FOREIGN KEY (sender_id) REFERENCES Users (id),
  FOREIGN KEY (recipient_id) REFERENCES Users (id)
);
CREATE TABLE Dialogues (
  id          BIGINT    NOT NULL PRIMARY KEY AUTO_INCREMENT,
  creator     BIGINT    NOT NULL,
  description VARCHAR(50),
  is_private  BOOLEAN                        DEFAULT TRUE,
  last_update TIMESTAMP NOT NULL,
  FOREIGN KEY (creator) REFERENCES Users (id)
);

CREATE TABLE Messages (
  id       BIGINT        NOT NULL PRIMARY KEY AUTO_INCREMENT,
  sender   BIGINT        NOT NULL,
  dialog   BIGINT        NOT NULL,
  message  VARCHAR(2000) NOT NULL,
  msg_time TIMESTAMP     NOT NULL,
  is_read  BOOLEAN                            DEFAULT FALSE,
  FOREIGN KEY (sender) REFERENCES Users (id),
  FOREIGN KEY (dialog) REFERENCES Dialogues (id)
);

CREATE TABLE Dialog_Participants (
  dialog_id BIGINT NOT NULL,
  user_id   BIGINT NOT NULL,
  FOREIGN KEY (dialog_id) REFERENCES Dialogues (id),
  FOREIGN KEY (user_id) REFERENCES Users (id)
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

INSERT INTO Relations (sender_id, recipient_id, relation_type) VALUES (1, 2, 3);
INSERT INTO Relations (sender_id, recipient_id, relation_type) VALUES (1, 4, 1);
INSERT INTO Relations (sender_id, recipient_id, relation_type) VALUES (5, 1, 1);
INSERT INTO Relations (sender_id, recipient_id, relation_type) VALUES (3, 1, 3);
INSERT INTO Relations (sender_id, recipient_id, relation_type) VALUES (4, 2, 3);

INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 1', '2016-11-15 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 2', '2016-11-13 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 3', '2016-11-10 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 4', '2016-11-11 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 5', '2016-11-14 10:28:42');
INSERT INTO Dialogues (creator, description, last_update) VALUES (1, 'test dialog 6', '2016-11-12 10:28:42');

INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (1, 2);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (2, 3);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (3, 4);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (4, 5);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (5, 6);
INSERT INTO Dialog_Participants (dialog_id, user_id) VALUES (6, 7);

INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Hello world 6', '2016-11-15 10:28:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Hello world 7', '2016-11-15 10:29:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Hello world 1', '2016-11-15 10:23:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Hello world 2', '2016-11-15 10:24:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Hello world 3', '2016-11-15 10:25:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Hello world 4', '2016-11-15 10:26:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Hello world 5', '2016-11-15 10:27:42');

INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Hello world 8', '2016-11-15 10:30:42');
INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (1, 1, 'Hello world 9', '2016-11-15 10:31:42');