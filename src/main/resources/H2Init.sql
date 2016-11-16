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