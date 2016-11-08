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